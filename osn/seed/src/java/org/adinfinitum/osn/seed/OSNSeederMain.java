package org.adinfinitum.osn.seed;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
 * Usage: java -jar osn-test.jar seed_properties.xml
 */
public class OSNSeederMain {
    private static final Logger logger = Logger.getLogger(OSNSeederMain.class.getName());

    public static void main(String... args) {
        InputStream seedConfigStream = null;

        if (args.length < 1) {
            logger.info("No properties file specified.");
            System.exit(1);
        }
        else {
            try {
                seedConfigStream = new FileInputStream(new File(args[0]));
                SeedConfiguration.getInstance().setConfig(seedConfigStream);
            }
            catch (Exception e) {
                try {
                    if (seedConfigStream != null) seedConfigStream.close();
                } catch(Exception e2) {
                    // no-op
                }
            }
        }

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
