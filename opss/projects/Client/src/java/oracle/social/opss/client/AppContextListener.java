/* Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved. */
package oracle.social.opss.client;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import waggle.client.main.XClientMain;
import waggle.core.http.XHTTPSession;


public class AppContextListener implements ServletContextListener {

    private static final String CLASSNAME = AppContextListener.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;
    private static final String WEBAPP_INSTANCE_NAME="opss-appservlet";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            initializeBackend(servletContext);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "App context initialization error: ", e);
        }
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            shutdownBackend();
        }
        catch (Throwable e) {
            logger.log(Level.SEVERE, "App context destruction error: ", e);
        }
    }


    /**
     * Initializes application as client to backend service.
     * @param servletContext the ServletContext
     * @throws Exception if initialization failed
     */
    public static void initializeBackend(ServletContext servletContext) throws Exception {
        Properties properties = new Properties();
        properties.setProperty(XHTTPSession.ENABLE_SELF_SIGNED_CERTIFICATES, "true");
        XClientMain.initWebApp(servletContext, WEBAPP_INSTANCE_NAME, properties);
    }


    /**
     * Shutdown client to backend service.
     */
    public static void shutdownBackend() {
        XClientMain.shutdown();
    }

}
