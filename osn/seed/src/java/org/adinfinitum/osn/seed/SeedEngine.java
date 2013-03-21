package org.adinfinitum.osn.seed;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.module.ConversationsModule;
import org.adinfinitum.osn.seed.util.RandomTextGenerator;

import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class SeedEngine {
    private final static Logger logger = Logger.getLogger(SeedEngine.class.getName());

    private final static int NUM_OF_COLLECTIONS = 10;
    private final static int NUM_OF_CONVERSATIONS_PER_COLLECTION = 20;

    private static final SeedEngine _instance = new SeedEngine();
    private SeedEngine() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static SeedEngine getInstance() {
        return _instance;
    }

    /**
     * Begins the seeding process.
     * @param xapi XAPI
     */
    public void start(XAPI xapi) {
        // TODO: make seeding re-entrant.
        // TODO: for example, check if collections are already created before creating more, etc.

        List<XObjectID> collectionIDs = new ArrayList<XObjectID>();
        List<XObjectID> conversationIDs = new ArrayList<XObjectID>();

        // Create collections.
        //
        logger.info("Creating collections...");
        for (int i=0; i < NUM_OF_COLLECTIONS; i++) {
            collectionIDs.add(ConversationsModule.getInstance().
                    createCollection(xapi, RandomTextGenerator.genConversationTitle("Coll-" + i + ":")));
        }
        logger.info("Collections created.");

        sleep(200);

        // Create related conversations in collections.
        //
        logger.info("Creating Related Conversations...");
        for (XObjectID collId : collectionIDs) {
            for (int j=0; j < NUM_OF_CONVERSATIONS_PER_COLLECTION; j++) {
                conversationIDs.add(ConversationsModule.getInstance().
                    createConversation(xapi, collId, RandomTextGenerator.genConversationTitle("Related Conv-" + j + ":")));
            }
            sleep(200);
        }
        logger.info("Related Conversations created.");

        // TODO: add messages to conversations.
    }


    /**
     * Sleep for a bit.
     * @param sleepTime in milliseconds
     */
    private void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch(InterruptedException ine) {
            // do nothing
        }
    }
}
