package oracle.social.johtaja.service.bc;

import java.util.logging.Level;
import java.util.logging.Logger;

import waggle.client.message.XMessageReceiverThreadJSON;

import waggle.core.api.XAPI;
import waggle.core.events.XEventsRegistry;
import waggle.core.id.XClientID;


public final class BackChannelReceiverThread extends XMessageReceiverThreadJSON {
    private static final String CLASSNAME = BackChannelReceiverThread.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;

    private XClientID backChannelReceiverID;
    
    /**
     * Construct the JSON backchannel receiver thread.
     * @param xapi the associated XAPI instance to use.
     */
    public BackChannelReceiverThread(XAPI xapi) {
        super(xapi);
        this.backChannelReceiverID = xapi.getClientID();
    }
    
    
    /**
     * Construct the JSON backchannel receiver thread.
     * @param xapi the associated XAPI instance to use.
     * @param eventsRegistry the associated XEventsRegistry instance to use.
     */
    public BackChannelReceiverThread(XAPI xapi, XEventsRegistry eventsRegistry) {
        super(xapi, eventsRegistry);
        this.backChannelReceiverID = xapi.getClientID();
    }
    
    
    /**
     * Get the ID for this receiver thread.  It is set to the XClientID associated
     * with the XAPI instance specified in the constructor.
     * @return XClientID
     */
    public XClientID getID() {
        return backChannelReceiverID;
    }

        
    /**
     * Initialize the thread name before proceeding.
     */
    public void run() {
        if (backChannelReceiverID != null) {
            Thread.currentThread().setName("BackChannelReceiverThread." + backChannelReceiverID);
            if (logger.isLoggable(DEBUG)) {
                logger.log(DEBUG, "XClientID {0} has thread {1}", new Object[] { backChannelReceiverID, Thread.currentThread().getName() });
            }
        }
        super.run();
    }
}
