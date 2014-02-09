package org.adinfinitum.osn.seed;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.adinfinitum.osn.seed.config.Collection;
import org.adinfinitum.osn.seed.config.Connector;
import org.adinfinitum.osn.seed.config.SeedConfiguration;
import org.adinfinitum.osn.seed.config.User;
import org.adinfinitum.osn.seed.module.ContactModule;
import org.adinfinitum.osn.seed.module.ConversationModule;
import org.adinfinitum.osn.seed.module.TrackableModule;
import org.adinfinitum.osn.seed.module.UserModule;
import org.adinfinitum.osn.seed.util.RandomTextGenerator;

import waggle.common.modules.connect.XConnectModule;
import waggle.common.modules.connect.infos.XLoginCredentialsInfo;
import waggle.common.modules.connect.infos.XLoginInfo;
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

    private XLoginInfo loginInfo;
    private Connector connector = SeedConfiguration.getInstance().getConnectorConfig();
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
        this.seedContacts(xapi);
        this.seedCollections(xapi);
        this.seedMessages(xapi);
    }


    /**
     * Login as the admin user.  The username and password of the admin
     * user are obtained from the properties xml.
     * @param xapi XAPI
     */
    private void loginAsAdmin(XAPI xapi) {
        XLoginCredentialsInfo loginCredentialsInfo = new XLoginCredentialsInfo();
        loginCredentialsInfo.UserName = connector.getLoginUser();
        loginCredentialsInfo.UserPassword = connector.getLoginPassword();

        loginInfo = xapi.call(XConnectModule.Server.class).login(loginCredentialsInfo);
        if (loginInfo != null) {
            xapi.setRandomID(loginInfo.APIRandomID);
            logger.info(">>>> Login as: " + loginInfo.UserInfo.DisplayName);
        }
    }


    /**
     * Login as a given user.
     * @param xapi XAPI
     * @param user XUserInfo of the user to login as.
     */
    private void loginAsUser(XAPI xapi, XUserInfo user) {
        XLoginCredentialsInfo loginCredentialsInfo = new XLoginCredentialsInfo();
        loginCredentialsInfo.UserName = user.Name;
        loginCredentialsInfo.UserPassword = "waggle";

        loginInfo = xapi.call(XConnectModule.Server.class).login(loginCredentialsInfo);
        if (loginInfo != null) {
            xapi.setRandomID(loginInfo.APIRandomID);
            logger.info(">>>> Login as: " + loginInfo.UserInfo.DisplayName);
        }
    }


    /**
     * Logout as the current user.
     * @param xapi XAPI
     */
    private void logout(XAPI xapi) {
        logger.info("<<<< Logout as: " + loginInfo.UserInfo.DisplayName);
        xapi.call(XConnectModule.Server.class).logout();
    }


    /**
     * Grant developer role. Requires admin privilege.
     * @param xapi XAPI
     */
    private void initPrivileges(XAPI xapi) {
        logger.info("Init privileges...");
        loginAsAdmin(xapi);
        UserModule userModule = UserModule.getInstance();
        XObjectID myId = userModule.getMe(xapi).ID;
        userModule.grantDeveloperRole(xapi, myId);
        logout(xapi);
    }


    /**
     * Seed OSN with a bunch of users. Requires admin privilege.
     * @param xapi XAPI
     */
    private void seedUsers(XAPI xapi) {
        loginAsAdmin(xapi);
        UserModule userModule = UserModule.getInstance();
        final int numUsers = userConf.getNumberOfUsers();
        final int numOutsiders = userConf.getNumberOfOutsiders();

        // Create a set of test users.
        final int chunkSize = 50;
        for (int i=0 ; i < numUsers ; i++) {
            if (i % chunkSize == 0) {
                int upperLimit = Math.min(numUsers, i+chunkSize);
                logger.info("Creating users " + (i+1) + " to " + upperLimit + "...");
                userModule.createTestUsers(xapi, i+1, chunkSize);
                sleep(1000);
            }
        }

        // Create a set of outside users.
        for (int i=0 ; i < numOutsiders ; i++) {
            if (i % chunkSize == 0) {
                int upperLimit = Math.min(numOutsiders, i+chunkSize);
                logger.info("Creating outsider users " + (i+1) + " to " + upperLimit + "...");
                userModule.createOutsideUsers(xapi, i+1, chunkSize);
                sleep(1000);
            }
        }

        // Get only test users, not outside users.
        List<XUserInfo> seededUsers = userModule.getAllTestUsers(xapi, numUsers);

        // Create user profile pics.
        logger.info("Adding user profile pics...");
        for (XUserInfo user : seededUsers) {
            userModule.createProfilePic(xapi, user.ID);
        }
        sleep(2000);
        logout(xapi);
    }


    /**
     * Create contacts between users.
     * Should be invoked only after users have been seeded.
     * @param xapi XAPI
     */
    private void seedContacts(XAPI xapi) {
        // First login as admin to get all users.
        loginAsAdmin(xapi);
        UserModule userModule = UserModule.getInstance();
        ContactModule contactModule = ContactModule.getInstance();

        final int numUsers = userConf.getNumberOfUsers();
        List<XUserInfo> seededUsers = userModule.getAllTestUsers(xapi, numUsers);
        logout(xapi);

        // Next, login as user1 to add contacts.
        if (seededUsers.size() > 0) {
            loginAsUser(xapi, seededUsers.get(0));

            // Add contacts.
            logger.info("Adding contacts to " + seededUsers.get(0).DisplayName + "...");
            for (XUserInfo user : seededUsers) {
                if (user.ID != seededUsers.get(0).ID) {
                    contactModule.createContact(xapi, user.ID);
                }
            }
            sleep(2000);
            logout(xapi);
        }
    }


    /**
     * Seed OSN with a bunch of collections and related conversations
     * in the each collection.
     * @param xapi XAPI
     */
    private void seedCollections(XAPI xapi) {
        loginAsAdmin(xapi);
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

        logout(xapi);
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
