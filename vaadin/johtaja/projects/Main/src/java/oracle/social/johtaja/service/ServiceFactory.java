package oracle.social.johtaja.service;

import waggle.core.api.XAPI;


/**
 * Service Factory provides access to OSN service modules
 * that can be invoked to accomplish various CRUD tasks.
 */
public class ServiceFactory {
    
    private XAPI xAPI;
    private UserModuleService userModule = null;
    
    
    private ServiceFactory(XAPI xapi) {
        this.xAPI = xapi;
    }
    
    /**
     * Create a new instance of service factory associated with
     * the given XAPI (i.e. XHTTPSession).
     * @param xapi
     * @return a new ServiceFactory instance.
     */
    public static ServiceFactory newInstance(XAPI xapi) {
        return new ServiceFactory(xapi);
    }
    
    
    public UserModuleService getUserModuleService() {
        if (userModule == null) {
            userModule = new UserModuleService(xAPI);
        }
        return userModule;
    }
}
