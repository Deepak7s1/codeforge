/* Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved. */
package oracle.social.opss.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import waggle.common.modules.connect.XConnectModule;
import waggle.common.modules.connect.infos.XLoginCredentialsInfo;
import waggle.common.modules.connect.infos.XLoginInfo;
import waggle.common.modules.hive.XHiveModule;

import waggle.common.modules.hive.infos.XHiveInfo;

import waggle.core.api.XAPI;
import waggle.core.api.XAPIManager;
import waggle.core.api.exceptions.XAPIException;
import waggle.core.exceptions.XRuntimeException;
import waggle.core.exceptions.infos.XExceptionInfo;
import waggle.core.id.XClientID;


/**
 * Encapsulates the user session with the OSN server. 
 */
public class XAPISession {
    private static final String CLASSNAME = XAPISession.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    private static final String AUTH_TYPE_NAME = "OIT";
    private static final String AUTHORIZATION = "Authorization";
    private static final String SPACE = " ";

    private XAPI xAPI;
    private XLoginInfo xLoginInfo = null;
    
    
    public XAPISession() {
        xAPI = XAPIManager.newInstance();
        XClientID xClientId = XClientID.generate();
        xAPI.setClientID(xClientId);
        xAPI.setProtocolXML(false);  // use JSON
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
     * OSN service login. Use this method if we know username and password.
     * @param username the login user name
     * @param password the login user password
     * @throws Exception when login fails
     */
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
            
        } 
        catch (Exception e) {
            logger.log(Level.WARNING, "Remote login failed ---" + e);
            if (e instanceof XAPIException) {
                XAPIException xapiException = (XAPIException)e;
                XExceptionInfo remoteExceptionInfo = xapiException.getRemoteExceptionInfo();
                if (remoteExceptionInfo != null) {
                    logger.log(Level.WARNING, "Exception: " + remoteExceptionInfo.ExceptionClass);
                    logger.log(Level.WARNING, "  Message: " + remoteExceptionInfo.Message);
                    logger.log(Level.WARNING, " Resource: " + remoteExceptionInfo.ResourceID);

                    if (remoteExceptionInfo.ExceptionClass.contains("XAPIVersionException")) {
                        throw new Exception("API Version Mismatch", e);
                    }
                    else if (remoteExceptionInfo.ResourceID.contains("InvalidLogin")) {
                        throw new Exception("Invalid Login", e);
                    }
                }
            }
            else if (e instanceof XRuntimeException) {
                XRuntimeException xruntimeEx = (XRuntimeException)e;
                logger.log(Level.WARNING, " Message: " + xruntimeEx.getMessage());
                logger.log(Level.WARNING, "Resource: " + xruntimeEx.getResourceID());
                if (xruntimeEx.getResourceID().contains("CouldNotExecuteRequest")) {
                    throw new Exception("Could Not Execute Request", e);
                }
            }
            throw new Exception("Unexpected error.", e);
        }
    }
    
    
    /**
     * OSN service login. Use this method for OAM/SSO.
     * OPSS Trust Service is used to generate a security token from the user name.
     * @param username the login user name
     * @throws Exception when login fails
     */
    public void serviceLogin(String username) throws Exception {

        XLoginInfo xLoginInfo = null;
        try {
            // Perform login.
            String token = TrustServiceClient.getSecurityToken(username);
            xAPI.setRequestHeader(AUTHORIZATION, AUTH_TYPE_NAME + SPACE + token);

            xLoginInfo = xAPI.call(XConnectModule.Server.class).getLoginInfo();  
            XHiveInfo xHiveInfo = xAPI.call(XHiveModule.Server.class).getHive();
            
        } 
        catch (Exception e) {
            logger.log(Level.WARNING, "Remote login failed ---" + e);
            if (e instanceof XAPIException) {
                XAPIException xapiException = (XAPIException)e;
                XExceptionInfo remoteExceptionInfo = xapiException.getRemoteExceptionInfo();
                if (remoteExceptionInfo != null) {
                    logger.log(Level.WARNING, "Exception: " + remoteExceptionInfo.ExceptionClass);
                    logger.log(Level.WARNING, "  Message: " + remoteExceptionInfo.Message);
                    logger.log(Level.WARNING, " Resource: " + remoteExceptionInfo.ResourceID);

                    if (remoteExceptionInfo.ExceptionClass.contains("XAPIVersionException")) {
                        throw new Exception("API Version Mismatch", e);
                    }
                    else if (remoteExceptionInfo.ResourceID.contains("InvalidLogin")) {
                        throw new Exception("Invalid Login", e);
                    }
                }
            }
            else if (e instanceof XRuntimeException) {
                XRuntimeException xruntimeEx = (XRuntimeException)e;
                logger.log(Level.WARNING, " Message: " + xruntimeEx.getMessage());
                logger.log(Level.WARNING, "Resource: " + xruntimeEx.getResourceID());
                if (xruntimeEx.getResourceID().contains("CouldNotExecuteRequest")) {
                    throw new Exception("Could Not Execute Request", e);
                }
            }
            throw new Exception("Unexpected error.", e);
        }
    }
    
    
    public void serviceLogout() {
        try {
            // Perform logout.
            xAPI.call(XHiveModule.Server.class).exitHive();
            xAPI.call(XConnectModule.Server.class).logout();
        }
        catch (Exception e) {
            logger.warning("Logout encountered an exception -- " +
                           e.getMessage());
        }
    }
    
    
    public XAPI getXAPI() {
        return xAPI;
    }
    
    
    public String getCurrentLoginUsername() {
        if (xLoginInfo != null && xLoginInfo.UserInfo != null) {
            return xLoginInfo.UserInfo.DisplayName;
        }
        return "";
    }
}
