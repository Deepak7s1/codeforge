package org.adinfinitum.osn.seed.module;

import waggle.common.modules.chat.XChatModule;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;

import java.util.logging.Logger;


public class ChatModule {
    private static final Logger logger = Logger.getLogger(ChatModule.class.getName());

    private static final ChatModule _instance = new ChatModule();
    private ChatModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static ChatModule getInstance() {
        return _instance;
    }

    /**
     * Post a new message to a Conversation.
     * @param toConversationId the Conversation where message is posted
     * @param messageText the message text
     * @return the message ChatID
     */
    public XObjectID postNewMessage(XAPI xapi, XObjectID toConversationId, String messageText) {
        return xapi.call(XChatModule.Server.class).createChat(toConversationId, messageText, null, null);
    }

}
