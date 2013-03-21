package org.adinfinitum.osn.seed;

import java.lang.String;
import java.lang.System;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final static Logger logger = Logger.getLogger(OSNSeederMain.class.getName());

    public static void main(String... args) {
        checkArgs(args);

        XLoginCredentialsInfo loginCredentialsInfo = new XLoginCredentialsInfo();
        loginCredentialsInfo.UserName = args[1];
        loginCredentialsInfo.UserPassword = args[2];

        XAPI xapi = null;
        try {
            XClientMain.initApp();
            xapi = XAPIManager.newInstance();
            xapi.setSession(args[0], "/osn");

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


    private static void checkArgs(String... args) {
        if(args.length < 3) {
            logger.info("Required Parameters: {base URL} {username} {password}");
            logger.info("Usage: java -classpath {waggle-sdk libraries} org.adinfinitum.osn.seed.OSNSeederMain http://localhost:8080 user1 waggle");
            System.exit(1);
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
