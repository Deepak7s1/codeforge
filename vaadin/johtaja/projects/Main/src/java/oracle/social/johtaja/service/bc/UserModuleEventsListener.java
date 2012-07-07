package oracle.social.johtaja.service.bc;

import waggle.client.modules.user.XUserModuleClientEvents;
import waggle.common.modules.user.infos.XUserInfo;


public class UserModuleEventsListener implements XUserModuleClientEvents {
    
    public UserModuleEventsListener() {
        super();
    }

    public void notifyUserUpdated(XUserInfo xUserInfo) {
    }

    public void notifyUserDeleted(XUserInfo xUserInfo) {
    }
}
