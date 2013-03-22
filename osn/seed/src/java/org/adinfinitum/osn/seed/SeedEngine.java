package org.adinfinitum.osn.seed;

import java.util.List;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.config.Collection;
import org.adinfinitum.osn.seed.config.SeedConfiguration;
import org.adinfinitum.osn.seed.module.ConversationsModule;
import org.adinfinitum.osn.seed.util.RandomTextGenerator;

import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class SeedEngine {
    private static final Logger logger = Logger.getLogger(SeedEngine.class.getName());

    private Collection collConf = SeedConfiguration.getInstance().getCollectionConfig();

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
        //
        // Determine how many collections are already in the system.
        // If less than NUM_OF_COLLECTIONS, create the remaining.
        //
        List<XObjectID> collectionIDs = ConversationsModule.getInstance().getCollections(xapi,
                                        collConf.getNumberOfCollections());


        int numCollectionsToCreate = collConf.getNumberOfCollections() - collectionIDs.size();
        if (numCollectionsToCreate > 0) {
            // Create collections.
            logger.info("Creating collections...");
            for (int i=0; i < numCollectionsToCreate; i++) {
                collectionIDs.add(ConversationsModule.getInstance().
                        createCollection(xapi, RandomTextGenerator.genConversationTitle("Coll-" + i + ":")));
            }
            logger.info(numCollectionsToCreate + " collections created.");
            sleep(200);
        }

        //
        // Determine number of related conversations in ecah collection.
        // If less than NUM_OF_CONVERSATIONS_PER_COLLECTION, create the remaining.
        //
        for (XObjectID collId : collectionIDs) {
            List<XObjectID> conversationIDs = ConversationsModule.getInstance().getConversationsInCollection(xapi,
                                              collId, collConf.getNumberOfConversationsPerCollection());


            int numConversationsToCreate = collConf.getNumberOfConversationsPerCollection() - conversationIDs.size();
            if (numConversationsToCreate > 0) {
                // Create related Conversations.
                logger.info("Creating related Conversations for collection " + collId + "...");
                for (int j=0; j < numConversationsToCreate; j++) {
                    conversationIDs.add(ConversationsModule.getInstance().
                        createConversation(xapi, collId, RandomTextGenerator.genConversationTitle("Related Conv-" + j + ":")));
                }
                logger.info(numConversationsToCreate + " related Conversations created for collection " + collId + ".");
            }
            sleep(200);
        }


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
