package oracle.social.johtaja.service.bc;

import java.util.List;

import waggle.client.modules.chat.XChatModuleClientEvents;
import waggle.common.modules.chat.infos.XChatInfo;
import waggle.core.id.XObjectID;


public class ChatModuleEventsListener  implements XChatModuleClientEvents {
    
    public ChatModuleEventsListener() {
        super();
    }

    public void notifyTyping(XObjectID xObjectID, XObjectID xObjectID1) {
    }

    public void notifyChatCreated(XChatInfo xChatInfo) {
    }

    public void notifyChatForked(XChatInfo xChatInfo) {
    }

    public void notifyChatUpdated(XChatInfo xChatInfo) {
    }

    public void notifyChatDeleted(XChatInfo xChatInfo) {
    }

    public void notifyChatsRead(XObjectID xObjectID, List<Integer> list) {
    }

    public void notifyChatsUnread(XObjectID xObjectID, List<Integer> list) {
    }

    public void notifyChatsAllRead(XObjectID xObjectID, int i) {
    }

    public void notifyChatsAllUnread(XObjectID xObjectID, int i) {
    }
}
