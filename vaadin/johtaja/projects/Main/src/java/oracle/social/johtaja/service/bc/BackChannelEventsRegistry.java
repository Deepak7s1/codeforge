package oracle.social.johtaja.service.bc;

import java.lang.ref.SoftReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.social.johtaja.model.BCEventDataObject;

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
     * List of BC event consumers. When a backchannel event is received, this
     * list of consumers will be informed.
     */
    private Map<UUID, SoftReference<BackChannelEventConsumer>> eventConsumers;
    

    /**
     * Reference to singleton.
     */
    private static final BackChannelEventsRegistry _instance = new BackChannelEventsRegistry();
    private BackChannelEventsRegistry() {
        this.eventConsumers = new ConcurrentHashMap<UUID, SoftReference<BackChannelEventConsumer>>(1);
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
     * Add a new event consumer.
     * @param consumerId the consumer ID as a UUID object.
     * @param consumer the BackChannelEventConsumer.
     */
    public void addEventConsumer(UUID consumerId, BackChannelEventConsumer consumer) {
        eventConsumers.put(consumerId, new SoftReference<BackChannelEventConsumer>(consumer));
        logger.log(DEBUG, "Added event consumer " + consumerId + 
                          ". Total: " + eventConsumers.size());
    }
    
    /**
     * Remove an event consumer.
     * @param consumerId the consumer ID as a UUID object.
     */
    public void removeEventConsumer(UUID consumerId) {
        SoftReference<BackChannelEventConsumer> softRef = eventConsumers.remove(consumerId);
        if (softRef != null) {
            logger.log(DEBUG, "Removed event consumer " + softRef.get().getConsumerId() +
                              ". Total: " + eventConsumers.size());
        }
    }
    
    /**
     * Notify the list of event consumers when a backchannel event occurs.
     * @param eventObj the backchannel event data object.
     */
    public void notifyEventConsumers(BCEventDataObject eventObj) {
        if (eventConsumers.isEmpty()) {
            return;
        }
        
        synchronized(eventConsumers) {
            for (Map.Entry<UUID, SoftReference<BackChannelEventConsumer>> entry : eventConsumers.entrySet()) {
                if (entry.getValue() != null) {
                    entry.getValue().get().handleEvent(eventObj);
                }
            }
        }
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
