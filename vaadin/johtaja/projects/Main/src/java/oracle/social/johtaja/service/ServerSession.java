package oracle.social.johtaja.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.social.johtaja.service.bc.ServerBackChannel;

import waggle.common.modules.connect.XConnectModule;
import waggle.common.modules.connect.infos.XLoginCredentialsInfo;
import waggle.common.modules.connect.infos.XLoginInfo;
import waggle.common.modules.hive.XHiveModule;

import waggle.core.api.XAPI;
import waggle.core.api.XAPIManager;
import waggle.core.api.exceptions.XAPIException;
import waggle.core.exceptions.XRuntimeException;
import waggle.core.id.XClientID;


/**
 * Encapsulates the user session with the OSN server. 
 */
public class ServerSession {
    private static final String CLASSNAME = ServerSession.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    private XAPI xAPI;
    private XLoginInfo xLoginInfo = null;
    private ServiceFactory sFactory = null;
    private ServerBackChannel backChannel = null;
    
    
    public ServerSession() {
        xAPI = XAPIManager.newInstance();
        XClientID xClientId = XClientID.generate();
        xAPI.setClientID(xClientId);
        xAPI.setProtocolXML(false);  // use JSON
        sFactory = ServiceFactory.newInstance(xAPI);
        backChannel = new ServerBackChannel();
    }
    

    /**
     * Configure the server connection.
     * @param serverURL the server URL.
     * @param contextPath the context path.
     */
    public void configureConnection(String serverURL, String contextPath) {
        if (xAPI != null) {
            xAPI.setSession(serverURL, contextPath);
        }
    }
    
    
    /**
     * Get the service factory associated with this server session.
     * @return ServiceFactory
     */
    public ServiceFactory getServiceFactory() {
        return sFactory;
    }
    
    

    public void serviceLogin(String username, String password) throws Exception {
        XLoginCredentialsInfo xLoginCredentialsInfo = new XLoginCredentialsInfo();

        // Setup login credentials.
        xLoginCredentialsInfo.UserName = username;
        xLoginCredentialsInfo.UserPassword = password;

        try {
            // Perform login.
            xLoginInfo = xAPI.call(XConnectModule.Server.class).login(xLoginCredentialsInfo);            
            xAPI.setRandomID(xLoginInfo.APIRandomID);
            xAPI.call(XHiveModule.Server.class).enterHive();
            
            // Start backchannel thread.
            backChannel.setupBackChannelReceiverThread(xAPI);
            
        } 
        catch (Exception e) {
            logger.log(Level.WARNING, "Remote login failed ---" + e);
            if (e instanceof XAPIException) {
                XAPIException xapiException = (XAPIException)e;
                if (xapiException.getRemoteExceptionInfo() != null) {
                    logger.log(Level.WARNING, "Exception: " + xapiException.getRemoteExceptionInfo().ExceptionClass);
                    logger.log(Level.WARNING, "  Message: " + xapiException.getRemoteExceptionInfo().Message);
                    logger.log(Level.WARNING, " Resource: " + xapiException.getRemoteExceptionInfo().ResourceID);
                }
                throw new Exception("Invalid login.", e);
            }
            else if (e instanceof XRuntimeException) {
                XRuntimeException xruntimeEx = (XRuntimeException)e;
                logger.log(Level.WARNING, " Message: " + xruntimeEx.getMessage());
                logger.log(Level.WARNING, "Resource: " + xruntimeEx.getResourceID());
                if (xruntimeEx.getResourceID().contains("CouldNotExecuteRequest")) {
                    throw new Exception("Cannot connect.", e);
                }
            }            
            throw new Exception("Unexpected error.", e);
        }
    }
    
    
    public void serviceLogout() {
        try {
            // Stop backchannel thread.
            backChannel.shutdownBackChannelReceiverThread();
            
            // Perform logout.
            xAPI.call(XHiveModule.Server.class).exitHive();
            xAPI.call(XConnectModule.Server.class).logout();
        }
        catch (Exception e) {
            logger.warning("Logout encountered an exception -- " +
                           e.getMessage());
        }
    }
    
    
    public String getCurrentLoginUsername() {
        if (xLoginInfo != null && xLoginInfo.UserInfo != null) {
            return xLoginInfo.UserInfo.DisplayName;
        }
        
        return "";
    }
    
}
