package org.adinfinitum.osn.seed;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.config.Collection;
import org.adinfinitum.osn.seed.config.SeedConfiguration;
import org.adinfinitum.osn.seed.config.User;
import org.adinfinitum.osn.seed.module.ConversationModule;
import org.adinfinitum.osn.seed.module.TrackableModule;
import org.adinfinitum.osn.seed.module.UserModule;
import org.adinfinitum.osn.seed.util.RandomTextGenerator;

import waggle.common.modules.conversation.infos.XConversationInfo;
import waggle.common.modules.user.infos.XUserInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;

/**
 * This is the main class that seeds OSN with collections,
 * conversations, chat messages, etc.
 */
public class SeedEngine {
    private static final Logger logger = Logger.getLogger(SeedEngine.class.getName());

    private User userConf = SeedConfiguration.getInstance().getUserConfig();
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
        this.initPrivileges(xapi);
        this.seedUsers(xapi);
        this.seedCollections(xapi);
        this.seedMessages(xapi);
    }

    /**
     * Initialize the current user with the necessary privileges
     * to perform the following actions.
     * @param xapi XAPI
     */
    private void initPrivileges(XAPI xapi) {
        logger.info("Init privileges...");
        UserModule userModule = UserModule.getInstance();
        XObjectID myId = userModule.getMe(xapi).ID;
        userModule.grantDeveloperRole(xapi, myId);
    }


    /**
     * Seed OSN with a bunch of users.
     * @param xapi XAPI
     */
    private void seedUsers(XAPI xapi) {
        UserModule userModule = UserModule.getInstance();
        final int numUsers = userConf.getNumberOfUsers();

        // Create a set of test users.
        logger.info("Creating " + numUsers + " test users...");
        userModule.createTestUsers(xapi, numUsers);
        List<XUserInfo> seededUsers = userModule.getAllTestUsers(xapi, numUsers);

        // Create user profile pics.
        logger.info("Adding user profile pics...");
        for (XUserInfo user : seededUsers) {
            userModule.createProfilePic(xapi, user.ID);
            sleep(100);
        }
    }


    /**
     * Seed OSN with a bunch of collections and related conversations
     * in the each collection.
     * @param xapi XAPI
     */
    private void seedCollections(XAPI xapi) {
        UserModule userModule = UserModule.getInstance();
        ConversationModule convModule = ConversationModule.getInstance();
        TrackableModule trackModule = TrackableModule.getInstance();

        //
        // Determine how many collections are already in the system.
        // If less than number of collections to be seeded, create the remaining.
        //
        List<XObjectID> collectionIDs = convModule.getCollections(xapi,
                collConf.getNumberOfCollections());


        logger.info("Creating collections...");
        int numCollectionsToCreate = collConf.getNumberOfCollections() - collectionIDs.size();
        if (numCollectionsToCreate > 0) {
            // Create collections.
            for (int i=0; i < numCollectionsToCreate; i++) {
                collectionIDs.add(convModule.
                        createCollection(xapi, RandomTextGenerator.genConversationTitle("Coll-" + i + ":")));
            }
            logger.info(numCollectionsToCreate + " collections created.");
            sleep(100);
        }

        //
        // Determine number of related conversations in each collection.
        // If less than number required to be seeded, create the remaining.
        //
        logger.info("Creating related conversations...");
        for (XObjectID collId : collectionIDs) {
            // Get conversation IDs in a collection.
            List<XObjectID> conversationIDs = convModule.getConversationsInCollection(xapi,
                    collId, collConf.getNumberOfConversationsPerCollection());


            int numConversationsToCreate = collConf.getNumberOfConversationsPerCollection() - conversationIDs.size();
            if (numConversationsToCreate > 0) {
                // Create related Conversations.
                logger.info("Creating related Conversations for collection " + collId + "...");
                for (int j=0; j < numConversationsToCreate; j++) {
                    conversationIDs.add(
                        convModule.createConversation(xapi, collId,
                                RandomTextGenerator.genConversationTitle("Conv-" + j + ":")));
                }
                logger.info(numConversationsToCreate +
                            " related Conversations created for collection " + collId + ".");
                sleep(100);
            }
        }


        // Get the list of user IDs.
        final int numUsers = 10;  // TODO: make configurable.
        List<XUserInfo> seededUsers = userModule.getAllTestUsers(xapi, numUsers);
        List<XObjectID> userIdList = new ArrayList<XObjectID>(seededUsers.size());
        for (XUserInfo userInfo : seededUsers) {
            userIdList.add(userInfo.ID);
        }

        // Add users to the collections and conversations.
        logger.info("Adding members to conversations...");
        for (XObjectID collId : collectionIDs) {
            int limitMaxNumber = collConf.getNumberOfConversationsPerCollection();
            List<XConversationInfo> convList = trackModule.getRelatedConversations(xapi, collId, limitMaxNumber);
            for (XConversationInfo relatedConv : convList) {
                convModule.addMembers(xapi, relatedConv.ID, userIdList);
            }
            convModule.addMembers(xapi, collId, userIdList);
        }
    }


    /**
     * Seed conversations with a bunch of messages and replies to messages.
     * @param xapi XAPI
     */
    private void seedMessages(XAPI xapi) {
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
