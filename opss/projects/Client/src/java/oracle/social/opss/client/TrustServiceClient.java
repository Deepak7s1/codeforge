/* Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved. */
package oracle.social.opss.client;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Map;

import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.service.trust.TrustService;
import oracle.security.jps.service.trust.token.TokenConfiguration;
import oracle.security.jps.service.trust.token.TokenConstants;
import oracle.security.jps.service.trust.token.TokenContext;
import oracle.security.jps.service.trust.token.TokenManager;
import oracle.security.jps.service.trust.token.TokenUtil;

import oracle.security.xml.GenericToken;
import oracle.security.xml.Token;
import oracle.security.xmlsec.saml2.util.SAML2URI;
import oracle.security.xmlsec.wss.username.UsernameToken;
import oracle.security.xmlsec.wss.util.WSSTokenUtils;


public class TrustServiceClient {

    public static final String AUTH_TYPE_NAME = "OIT";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SPACE = " ";



    public static TokenManager getTrustServiceTokenManager() throws Exception {
        JpsContextFactory ctxFactory = JpsContextFactory.getContextFactory();

        JpsContext jpsCtx = ctxFactory.getContext();

        final TrustService trustService = jpsCtx.getServiceInstance(TrustService.class);

        if (trustService == null) {
            throw new Exception("TrustService instance is null");
        }
        final TokenManager tokenMgr = trustService.getTokenManager();

        if (tokenMgr == null) {
            throw new Exception("TokenManager instance is null");
        }
        return tokenMgr;
    }



    public static Token issueSecurityToken(String userName) throws Exception {
        final TokenManager tokenMgr = getTrustServiceTokenManager();

        /*
         * Not used in poc but should be used in real client
         */
        TokenContext ctx = tokenMgr.createTokenContext(TokenConfiguration.PROTOCOL_EMBEDDED);

        UsernameToken usernameToken = WSSTokenUtils.createUsernameToken("wsuid", userName);

        GenericToken gtok = new GenericToken(usernameToken);

        /*
         *  Set input token
         */
        ctx.setSecurityToken(gtok);

        /*
         *  Set requested token type
         */
        ctx.setTokenType(SAML2URI.ns_saml);

        /*
         *  Set confirmation method
         */
        Map<String, Object> ctxProperties = ctx.getOtherProperties();

        ctxProperties.put(TokenConstants.CONFIRMATION_METHOD, SAML2URI.confirmation_method_bearer);

        /*
         *  Issue token
         */
        final TokenContext ctxFinal = ctx;

        AccessController.doPrivileged(new PrivilegedAction<String>() {

                public String run() {
                    try {
                        tokenMgr.issueToken(ctxFinal);
                    }
                    catch (Exception e1) {
                        // throw new Exception("Exception in issue token");
                        e1.printStackTrace();
                    }
                    return null;
                }
            });

        Token token = ctxFinal.getSecurityToken();

        if (token == null) {
            throw new Exception("Token is invalid");
        }
        return token;
    }



    public static String encodeSecurityToken(Token token) throws Exception {
        return TokenUtil.encodeToken(token);
    }



    public static String getSecurityToken(String userName) throws Exception {
        try {
            Token token = TrustServiceClient.issueSecurityToken(userName);

            String encodedToken = TrustServiceClient.encodeSecurityToken(token);

            return encodedToken;
        }
        catch (Exception e) {
            throw new Exception("error in issue token");
        }
    }


}
