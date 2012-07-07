package oracle.social.johtaja.service.bc;

import java.util.logging.Level;
import java.util.logging.Logger;

import waggle.client.message.XMessageReceiverThread;
import waggle.core.api.XAPI;


/**
 * Encapsulates the user backchannel with the OSN server.
 */
public class ServerBackChannel {
    private static final String CLASSNAME = ServerBackChannel.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;
        
    
    /**
     * The back channel message listener thread.
     */
    private XMessageReceiverThread fThread;


    public ServerBackChannel() {
        logger.warning("ServerBackChannel() constructor");
        
        // Make sure event listeners have been registered with XEventsManager.
        BackChannelEventsRegistry.getInstance();
    }


    /**
     * Setup and start the event receiver thread.
     * @param fAPI
     */
    public void setupBackChannelReceiverThread(XAPI fAPI) {
        logger.log(DEBUG, "Start back channel event receiver thread");
        fThread = new BackChannelReceiverThread(fAPI);
        fThread.start();
    }
    

    /**
     * Stop the event receiver thread.
     */
    public void shutdownBackChannelReceiverThread() {
        logger.log(DEBUG, "Stopping back channel event receiver thread");

        try {
            fThread.stopThread();
            fThread.join();
        }
        catch (InterruptedException ine) {
            logger.warning("Unable to join thread " + fThread.getName());
        }
    }

}
