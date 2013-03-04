/* Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved. */
package oracle.social.opss.client;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import waggle.common.modules.admin.XAdminModule;
import waggle.common.modules.admin.infos.XDiagnosticInfo;
import waggle.core.api.XAPI;


/**
 * Example Servlet URL:
 *
 * http(s)://host:port/opss/appservlet?_u=james&_s=bond&_prot=http&_host=127.0.0.1&_port=8080&_path=osn
 * http(s)://host:port/opss/appservlet?_prot=http&_host=127.0.0.1&_port=8080&_path=osn
 *
 * Valid GET parameters:
 * 1. _u : the username (not needed for OAM/SSO authenticated request)
 * 2. _s : the password (not needed for OAM/SSO authenticated request)
 * 4. _prot : 'http' or 'https'
 * 5. _host : OSN server hostname
 * 6. _port : OSN server portnum
 * 7. _path : OSN server context path (usually 'osn', no leading forward slash)
 */
public class AppServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
        
    private final String username = "_u";
    private final String password = "_s";
    private final String osnHost = "_host";
    private final String osnPort = "_port";
    private final String osnProtocol = "_prot";
    private final String osnContextPath = "_path";
    private final String osnURL = "_url";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>AppServlet</title></head>");
        out.println("<body>");
        
        XAPI xapi = connect(request, out);
        
        if (xapi != null) {
            // Make OSN call to get some diagnostic data.
            List<XDiagnosticInfo> diag = xapi.call(XAdminModule.Server.class).getDiagnosticStatusList();
            StringBuffer sb = new StringBuffer("<p>");
            for (XDiagnosticInfo d : diag) {
              sb.append(d.Description).append(":").append(d.Status).append("<br/>");
            }
            out.println(sb.toString());
        }
        
        disconnect(out);
        
        out.println("</body></html>");
        out.close();
    }
    
    
    /**
     * Connect to the OSN service.
     * @param request the servlet request
     * @param out PrintWriter
     */
    private XAPI connect(HttpServletRequest request, PrintWriter out) {
        // Prepare connection parameters.
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put(username, request.getParameter(username) != null ? request.getParameter(username) : "");
        paramMap.put(password, request.getParameter(password) != null ? request.getParameter(password) : "");
        paramMap.put(osnHost, request.getParameter(osnHost) != null ? request.getParameter(osnHost) : "localhost");
        paramMap.put(osnPort, request.getParameter(osnPort) != null ? request.getParameter(osnPort) : "7001");
        paramMap.put(osnProtocol, request.getParameter(osnProtocol) != null ? request.getParameter(osnProtocol) : "http");
        paramMap.put(osnContextPath, request.getParameter(osnContextPath) != null ? "/" + request.getParameter(osnContextPath) : "/osn");
        paramMap.put(osnURL, paramMap.get(osnProtocol) + "://" + paramMap.get(osnHost) + ":" + paramMap.get(osnPort));
        printParamMap(paramMap, out);

        
        XAPISession xapiSess = new XAPISession();
        try {
            xapiSess.configureConnection(paramMap.get(osnURL), 
                                         paramMap.get(osnContextPath));
            
            // If the request is already OAM/SSO authenticated, the user principal 
            // should be available in the request.  Use this to access OSN service.
            if (request.getUserPrincipal() != null) {
                xapiSess.serviceLogin(request.getUserPrincipal().getName());
                out.println("Successfully logged in to OSN using TrustService as " + xapiSess.getCurrentLoginUsername());
            }
            else {
                xapiSess.serviceLogin(paramMap.get(username), paramMap.get(password));
                out.println("Successfully logged in to OSN as " + xapiSess.getCurrentLoginUsername());
            }
        }
        catch (Exception e) {
            out.println("<textarea rows='25' cols='130'>");
            e.printStackTrace(out);
            out.println("</textarea>");
            return null;
        }        
        return xapiSess.getXAPI();
    }
    
    
    /**
     * Print out the relevant GET parameters and their values.
     * @param paramMap the parameter map
     * @param out PrintWriter
     */
    private void printParamMap(Map<String, String> paramMap, PrintWriter out) {
        StringBuffer sb = new StringBuffer("<p>");
        sb.append(username + " = " + paramMap.get(username)).append("<br/>");
        sb.append(password + " = " + paramMap.get(password)).append("<br/>");
        sb.append(osnURL + " = " + paramMap.get(osnURL)).append("<br/>");
        sb.append(osnContextPath + " = " + paramMap.get(osnContextPath)).append("<br/></p>");
        out.println(sb.toString());
    }
    
    
    /**
     * Disconnect from the OSN service.
     * @param out PrintWriter
     */
    public void disconnect(PrintWriter out) {
        XAPISession xapiSess = new XAPISession();
        xapiSess.serviceLogout();
        out.println("<p>Successfully logged out of OSN</p>");
    }
}
