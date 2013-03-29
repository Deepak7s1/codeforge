package org.adinfinitum.osn.seed.module;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import waggle.common.modules.conversation.XConversationModule;
import waggle.common.modules.conversation.enums.XConversationDetailSortField;
import waggle.common.modules.conversation.infos.XConversationCreateInfo;
import waggle.common.modules.conversation.infos.XConversationDetailConversationInfo;
import waggle.common.modules.conversation.infos.XConversationDetailFilterInfo;
import waggle.common.modules.conversation.infos.XConversationDetailInfo;
import waggle.common.modules.conversation.infos.XConversationInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class ConversationsModule {
    private static final Logger logger = Logger.getLogger(ConversationsModule.class.getName());

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
     * Get all collections up to a maximum limit. Collections that are closed
     * and muted are excluded. Walls are also excluded.
     * @param xapi XAPI
     * @param maxNumber maximum number to retrieve
     * @return the list of XObjectIDs of the collections
     */
    public List<XObjectID> getCollections(XAPI xapi, int maxNumber) {
        XObjectID collectionGadgetId = XOSGadgetModule.getInstance().getCollectionGadgetId(xapi);
        logger.info("collectionGadgetId = " + collectionGadgetId);

        // Create the filter.
        XConversationDetailFilterInfo filter = new XConversationDetailFilterInfo();
        filter.FirstResult = 0;
        filter.NumResults = maxNumber;
        filter.SortField = XConversationDetailSortField.CONVERSATION_LAST_POST;
        filter.SortOrderDescending = true;
        filter.IncludeClosed = false;
        filter.IncludeOpen = true;
        filter.ExcludeWalls = true;
        filter.ExcludeMuted = true;
        filter.ExcludeGadgetedConversations = false;
        filter.LimitToGadgetID = collectionGadgetId;

        XConversationDetailInfo xcdi = xapi.call(XConversationModule.Server.class).getConversationsDetail(filter);

        List<XObjectID> conversationIDs = new ArrayList<XObjectID>();
        if (xcdi.ConversationInfos != null) {
            for (XConversationDetailConversationInfo ci : xcdi.ConversationInfos) {
                conversationIDs.add(ci.Conversation.ConversationInfo.ID);
            }
        }
        return conversationIDs;
    }


    /**
     * Get all Conversations in a collection up to a maximum limit. Conversations
     * that are closed and muted are excluded. Walls are also excluded.
     * @param xapi XAPI
     * @param collectionId the XObjectID of the collection
     * @param maxNumber maximum number to retrieve
     * @return the list of XObjectIDs of the Conversations
     */
    public List<XObjectID> getConversationsInCollection(XAPI xapi, XObjectID collectionId, int maxNumber) {
        // Create the filter.
        XConversationDetailFilterInfo filter = new XConversationDetailFilterInfo();
        filter.FirstResult = 0;
        filter.NumResults = maxNumber;
        filter.SortField = XConversationDetailSortField.CONVERSATION_LAST_POST;
        filter.SortOrderDescending = true;
        filter.IncludeClosed = false;
        filter.IncludeOpen = true;
        filter.ExcludeWalls = true;
        filter.ExcludeMuted = true;
        filter.LimitToContainedConversations = collectionId;

        XConversationDetailInfo xcdi = xapi.call(XConversationModule.Server.class).getConversationsDetail(filter);

        List<XObjectID> conversationIDs = new ArrayList<XObjectID>();
        if (xcdi.ConversationInfos != null) {
            for (XConversationDetailConversationInfo ci : xcdi.ConversationInfos) {
                conversationIDs.add(ci.Conversation.ConversationInfo.ID);
            }
        }
        return conversationIDs;
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
        XObjectID collectionGadgetId = XOSGadgetModule.getInstance().getCollectionGadgetId(xapi);

        XConversationCreateInfo createInfo = new XConversationCreateInfo();
        createInfo.Name = collectionName;
        createInfo.RelatedToConversationID = null;
        createInfo.PrimaryExternalID = null;
        createInfo.TypeGadgetID = collectionGadgetId;
        XConversationInfo collectionInfo = xapi.call(XConversationModule.Server.class).createConversationFromInfo(createInfo);
        return collectionInfo.ID;
    }
}
