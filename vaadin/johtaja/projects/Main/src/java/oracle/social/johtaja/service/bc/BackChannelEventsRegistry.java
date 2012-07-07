package oracle.social.johtaja.service.bc;

import java.util.logging.Level;
import java.util.logging.Logger;

import waggle.core.events.XEventsManager;


public class BackChannelEventsRegistry {
    private static final String CLASSNAME = BackChannelEventsRegistry.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;

    /**
     * The module-specific XEvent listeners.
     */
    private ChatModuleEventsListener chatListener;
    private ConversationModuleEventsListener conversationListener;    
    private FollowupModuleEventsListener followupListener;
    private UserModuleEventsListener userListener;
    private StarModuleEventsListener starListener;


    /**
     * Reference to singleton.
     */
    private static final BackChannelEventsRegistry _instance = new BackChannelEventsRegistry();
    private BackChannelEventsRegistry() {
        this.registerXEventListeners();
    }

    /**
     * Get the singleton instance of BackChannelEventsRegistry.
     * @return reference to BackChannelEventsRegistry
     */
    public static BackChannelEventsRegistry getInstance() {
        return _instance;
    }


    /**
     * Register XEvent listeners.
     */
    private void registerXEventListeners() {
        logger.log(DEBUG, "Register back channel event listeners");

        conversationListener = new ConversationModuleEventsListener();        
        chatListener = new ChatModuleEventsListener();
        followupListener = new FollowupModuleEventsListener();
        userListener = new UserModuleEventsListener();
        starListener = new StarModuleEventsListener();
        
        XEventsManager.register(conversationListener);
        XEventsManager.register(chatListener);
        XEventsManager.register(followupListener);
        XEventsManager.register(userListener);
        XEventsManager.register(starListener);
    }


    /**
     * De-register XEvent listeners.
     */
    private void deregisterXEventListeners() {
        logger.log(DEBUG, "De-register back channel event listeners");

        XEventsManager.unregister(conversationListener);
        XEventsManager.unregister(chatListener);
        XEventsManager.unregister(followupListener);
        XEventsManager.unregister(userListener);
        XEventsManager.unregister(starListener);
    }

}
