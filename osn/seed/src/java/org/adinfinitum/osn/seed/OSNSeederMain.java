package org.adinfinitum.osn.seed;

import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.config.Connector;
import org.adinfinitum.osn.seed.config.SeedConfiguration;

import waggle.client.main.XClientMain;
import waggle.common.modules.connect.XConnectModule;
import waggle.common.modules.connect.infos.XLoginCredentialsInfo;
import waggle.common.modules.connect.infos.XLoginInfo;
import waggle.common.modules.hive.XHiveModule;
import waggle.core.api.XAPI;
import waggle.core.api.XAPIManager;


/**
 * The main program that seeds an Oracle Social Network system.
 * Usage: java -classpath {waggle-sdk libraries} OSNSeederMain
 *        http://localhost:8080 user1 waggle
 */
public class OSNSeederMain {
    private static final Logger logger = Logger.getLogger(OSNSeederMain.class.getName());

    public static void main(String... args) {
        Connector connector = SeedConfiguration.getInstance().getConnectorConfig();
        logger.info("Connecting to " + connector.getServerUrl() + connector.getWebContextPath());

        XLoginCredentialsInfo loginCredentialsInfo = new XLoginCredentialsInfo();
        loginCredentialsInfo.UserName = connector.getLoginUser();
        loginCredentialsInfo.UserPassword = connector.getLoginPassword();

        XAPI xapi = null;
        try {
            XClientMain.initApp();
            xapi = XAPIManager.newInstance();
            xapi.setSession(connector.getServerUrl(), connector.getWebContextPath());

            XLoginInfo loginInfo = xapi.call(XConnectModule.Server.class).login(loginCredentialsInfo);

            if (loginInfo != null) {
                xapi.setRandomID(loginInfo.APIRandomID);
                xapi.call(XHiveModule.Server.class).enterHive();
                printLoginInfo(loginInfo);

                SeedEngine.getInstance().start(xapi);

            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
            if (xapi != null) {
                xapi.call(XHiveModule.Server.class).exitHive();
                xapi.call(XConnectModule.Server.class).logout();
            }
            XClientMain.shutdown();
        }
    }

    /**
     * Print details of the XLoginInfo after OSN login completes.
     * @param loginInfo XLoginInfo
     */
    private static void printLoginInfo(XLoginInfo loginInfo) {
        logger.info("UserName: " + loginInfo.UserInfo.DisplayName);
        logger.info("APIVersion: " + loginInfo.APIVersion);
        logger.info("APIRandomID: " + loginInfo.APIRandomID);
        logger.info("FrontChannelURL: " + loginInfo.FrontChannelURL);
        logger.info("BackChannelURL: " + loginInfo.BackChannelURL);
    }

}
