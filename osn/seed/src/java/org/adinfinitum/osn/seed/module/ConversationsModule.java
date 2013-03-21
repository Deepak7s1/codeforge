package org.adinfinitum.osn.seed.module;

import java.util.logging.Logger;

import waggle.common.modules.conversation.XConversationModule;
import waggle.common.modules.conversation.infos.XConversationCreateInfo;
import waggle.common.modules.conversation.infos.XConversationInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class ConversationsModule {
    private final static Logger logger = Logger.getLogger(ConversationsModule.class.getName());

    private static final ConversationsModule _instance = new ConversationsModule();
    private ConversationsModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static ConversationsModule getInstance() {
        return _instance;
    }

    /**
     * Create a Conversation for the logged in user.
     * @param xapi XAPI
     * @param relatedToConversationId XObjectID of the related-to parent Conversation, null if no related-to Conversation
     * @param conversationName the Conversation name
     * @return the newly created Conversation ID
     */
    public XObjectID createConversation(XAPI xapi,
                                        XObjectID relatedToConversationId,
                                        String conversationName) {
        XConversationInfo conversationInfo = xapi.call(XConversationModule.Server.class).createConversation(relatedToConversationId, conversationName);
        return conversationInfo.ID;
    }

    /**
     * Create a Conversation for the logged in user.
     * @param xapi XAPI
     * @param collectionName the Collection Name
     * @return the newly created Collection ID
     */
    public XObjectID createCollection(XAPI xapi,
                                      String collectionName) {
        XConversationCreateInfo createInfo = new XConversationCreateInfo();
        createInfo.Name = collectionName;
        createInfo.RelatedToConversationID = null;
        createInfo.PrimaryExternalID = null;
        createInfo.TypeGadgetID = XObjectID.valueOf(10125);  // TODO: hardcoded gadgetID
        XConversationInfo collectionInfo = xapi.call(XConversationModule.Server.class).createConversationFromInfo(createInfo);
        return collectionInfo.ID;
    }
}
