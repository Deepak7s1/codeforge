package oracle.social.johtaja.service.bc;

import java.util.List;

import waggle.client.modules.followup.XFollowupModuleClientEvents;
import waggle.common.modules.chat.infos.XChatInfo;
import waggle.common.modules.followup.infos.XFollowupInfo;
import waggle.common.modules.user.infos.XUserInfo;
import waggle.core.id.XObjectID;


public class FollowupModuleEventsListener implements XFollowupModuleClientEvents {

    public FollowupModuleEventsListener() {
        super();
    }

    public void notifyFollowupsOpened(XObjectID xObjectID, XObjectID xObjectID1, List<XObjectID> list,
                                      XObjectID xObjectID2) {
    }

    public void notifyFollowupsUpdated(XObjectID xObjectID, XObjectID xObjectID1, List<XObjectID> list,
                                       XObjectID xObjectID2) {
    }

    public void notifyFollowupsClosed(XObjectID xObjectID, XObjectID xObjectID1, List<XObjectID> list,
                                      XObjectID xObjectID2) {
    }

    public void notifyFollowupDeleted(XObjectID xObjectID, XObjectID xObjectID1) {
    }

    public void notifyFollowupMarkedRead(XChatInfo xChatInfo, XUserInfo xUserInfo, XFollowupInfo xFollowupInfo) {
    }

    public void notifyFollowupMarkedUnread(XChatInfo xChatInfo, XUserInfo xUserInfo, XFollowupInfo xFollowupInfo) {
    }
}
