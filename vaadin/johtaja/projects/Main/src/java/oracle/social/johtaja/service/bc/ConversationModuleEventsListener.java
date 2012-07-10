package oracle.social.johtaja.service.bc;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.social.johtaja.model.BCEventDataObject;

import waggle.client.modules.conversation.XConversationModuleClientEvents;

import waggle.common.modules.conversation.enums.XConversationRole;
import waggle.common.modules.conversation.infos.XConversationEnterExitInfo;
import waggle.common.modules.conversation.infos.XConversationInfo;
import waggle.common.modules.conversation.infos.XConversationTrackInfo;
import waggle.common.modules.member.infos.XMemberInfo;
import waggle.common.modules.user.infos.XUserInfo;


public class ConversationModuleEventsListener implements XConversationModuleClientEvents {
    private static final String CLASSNAME = ConversationModuleEventsListener.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;

    public ConversationModuleEventsListener() {
        super();
    }

    public void notifyConversationEntering(XConversationEnterExitInfo xConversationEnterExitInfo) {
    }

    public void notifyConversationEntered(XConversationEnterExitInfo xConversationEnterExitInfo) {
        logger.log(DEBUG, "Thread {0} >>> {1} CONVERSATION_ENTERED '{2}'", 
                   new Object[] { Thread.currentThread().getName(), 
                                  xConversationEnterExitInfo.UserName,
                                  xConversationEnterExitInfo.ConversationName });
        BCEventDataObject eventObj = new BCEventDataObject();
        eventObj.setEventName("ConversationEntered");
        eventObj.setEventType("CONVERSATION_EVENT_TYPE");        
        eventObj.setEventDescription(xConversationEnterExitInfo.UserName + " entered conversation.");
        BackChannelEventsRegistry.getInstance().notifyEventConsumers(eventObj);
    }


    public void notifyConversationExiting(XConversationEnterExitInfo xConversationEnterExitInfo) {
    }

    public void notifyConversationExited(XConversationEnterExitInfo xConversationEnterExitInfo) {
        logger.log(DEBUG, "Thread {0} >>> {1} CONVERSATION_EXITED '{2}'", 
                   new Object[] { Thread.currentThread().getName(), 
                                  xConversationEnterExitInfo.UserName,
                                  xConversationEnterExitInfo.ConversationName });
        BCEventDataObject eventObj = new BCEventDataObject();
        eventObj.setEventName("ConversationExited");
        eventObj.setEventType("CONVERSATION_EVENT_TYPE");
        eventObj.setEventDescription(xConversationEnterExitInfo.UserName + " exited conversation.");
        BackChannelEventsRegistry.getInstance().notifyEventConsumers(eventObj);
    }

    public void notifyConversationCreated(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationUpdated(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationNameChanged(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationStateChanged(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationDiscoverableChanged(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationExternalIDsChanged(XConversationInfo xConversationInfo, List<String> list,
                                                     List<String> list1) {
    }

    public void notifyConversationMutedChanged(XConversationInfo xConversationInfo, boolean b) {
    }

    public void notifyConversationMembersChanged(XConversationInfo xConversationInfo, List<XMemberInfo> list,
                                                 List<XMemberInfo> list1) {
    }

    public void notifyConversationAccessible(XConversationInfo xConversationInfo, XConversationRole xConversationRole,
                                             List<XConversationTrackInfo> list) {
    }

    public void notifyConversationInaccessible(XConversationInfo xConversationInfo) {
    }

    public void notifyConversationMembershipChanged(XConversationInfo xConversationInfo, List<XUserInfo> list,
                                                    List<XUserInfo> list1) {
    }

    public void notifyConversationShowMembershipMessagesChanged(XConversationInfo xConversationInfo,
                                                                Boolean boolean1) {
    }
}
