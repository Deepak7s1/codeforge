package oracle.social.johtaja.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import oracle.social.johtaja.model.UserDataObject;

import waggle.common.modules.user.XUserModule;
import waggle.common.modules.user.infos.XUserInfo;
import waggle.common.modules.user.infos.XUserSearchInfo;
import waggle.core.api.XAPI;


public class UserModuleService {
    private static final String CLASSNAME = UserModuleService.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    private XAPI xAPI;

    UserModuleService(XAPI xapi) {
        this.xAPI = xapi;
    }
    
    
    private XAPI getXAPI() {
        return this.xAPI;
    }


    /**
     * Find a list of users matching the user name prefix search string.  This method
     * looks for enabled/disabled and deleted users.  Does not return unregistered users.
     * 
     * @param searchNamePrefix a user name prefix string. A wildcard will be added to the end.
     * @return the list of UserDataObjects
     */
    public List<UserDataObject> findAllUsers(String searchNamePrefix) {
        List<XUserInfo> xuserList = 
            getXAPI().call(XUserModule.Server.class).findAllUsers(searchNamePrefix);
        
        if (xuserList == null || xuserList.isEmpty()) {
            // See generic notation here: 
            // http://gochev.blogspot.com/2010/07/using-of-collectionsemptylist-right-way.html
            return Collections.<UserDataObject>emptyList();
        }
        
        List<UserDataObject> wrapperList = new ArrayList<UserDataObject>(xuserList.size());
        for (XUserInfo xuser : xuserList) {
            UserDataObject wrapper = new UserDataObject(xuser);
            wrapperList.add(wrapper);
        }
        
        return wrapperList;
    }


    /**
     * Find a list of users matching the user name prefix search string.
     * 
     * @param searchNamePrefix a user name prefix string. A wildcard will be added to the end.
     * @param maxSize the maximum number of users to return.
     * @param includeUnregistered true to search realms. False to just search database. 
     *        If false only registered, enabled users are returned for all realms. 
     *        If true any matching external realm (LDAP) users are also returned. 
     * @return the list of UserDataObjects
     */
    public List<UserDataObject> findUsersWithLimit(String searchNamePrefix,
                                                   int maxSize,
                                                   boolean includeUnregistered) {
        List<XUserSearchInfo> xuserList = 
            getXAPI().call(XUserModule.Server.class).findUsersWithLimit(searchNamePrefix,
                                                                        maxSize,
                                                                        includeUnregistered);
        if (xuserList == null || xuserList.isEmpty()) {
            // See generic notation here: 
            // http://gochev.blogspot.com/2010/07/using-of-collectionsemptylist-right-way.html
            return Collections.<UserDataObject>emptyList();
        }
        
        List<UserDataObject> wrapperList = new ArrayList<UserDataObject>(xuserList.size());
        for (XUserSearchInfo xuser : xuserList) {
            UserDataObject wrapper = new UserDataObject(xuser);
            wrapperList.add(wrapper);
        }
        
        return wrapperList;
    }
}
