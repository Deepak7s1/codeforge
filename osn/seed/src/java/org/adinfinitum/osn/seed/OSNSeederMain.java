package org.adinfinitum.osn.seed;

import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.config.Connector;
import org.adinfinitum.osn.seed.config.SeedConfiguration;

import waggle.client.main.XClientMain;
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

        XAPI xapi;
        try {
            XClientMain.initApp();
            xapi = XAPIManager.newInstance();
            xapi.setSession(connector.getServerUrl(), connector.getWebContextPath());
            SeedEngine.getInstance().start(xapi);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
            XClientMain.shutdown();
        }
    }
}
