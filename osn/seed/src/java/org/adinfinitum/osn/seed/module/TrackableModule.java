package org.adinfinitum.osn.seed.module;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import waggle.common.modules.conversation.infos.XConversationInfo;
import waggle.common.modules.conversation.infos.XConversationTrackableInfo;
import waggle.common.modules.track.XTrackModule;
import waggle.common.modules.track.enums.XTrackTrackablesSortField;
import waggle.common.modules.track.infos.XTrackTrackablesFilterInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class TrackableModule {
    private static final Logger logger = Logger.getLogger(TrackableModule.class.getName());

    private static final TrackableModule _instance = new TrackableModule();
    private TrackableModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static TrackableModule getInstance() {
        return _instance;
    }

    /**
     * Get all related conversations of a given conversation.
     * @param xapi XAPI
     * @param convId the conversation ID
     * @param limitMaxNumber maximum number of related conversations
     * @return the list of XConversationInfo
     */
    public List<XConversationInfo> getRelatedConversations(XAPI xapi,
                                                           XObjectID convId,
                                                           int limitMaxNumber) {
        XTrackTrackablesFilterInfo filter = new XTrackTrackablesFilterInfo();
        filter.NumResults = limitMaxNumber;
        filter.SortField = XTrackTrackablesSortField.TRACKABLE_NAME;

        List<XConversationInfo> resultList = new ArrayList<XConversationInfo>();

        List<XConversationTrackableInfo> trackableInfoList =
                xapi.call(XTrackModule.Server.class).getTrackables(convId, filter);
        for (XConversationTrackableInfo trackableInfo : trackableInfoList) {
            resultList.add(trackableInfo.TrackableInfo);
        }
        return resultList;
    }
}
