package oracle.social.johtaja.app.servlet;

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


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            initializeBackend(servletContext);
            logger.log(DEBUG, "[ Johtaja AppContext initialized ]");
            
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
        finally {
            logger.log(DEBUG, "[ Johtaja AppContext destroyed ]");
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
        XClientMain.initWebApp(servletContext, properties);
    }


    /**
     * Shutdown client to backend service.
     */
    public static void shutdownBackend() {
        XClientMain.shutdown();
    }

}
