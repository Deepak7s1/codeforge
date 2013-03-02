package oracle.social.opss.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Client
 */
public class Client extends HttpServlet {
    private static final long serialVersionUID = 5762913230357111998L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {

        String userName = request.getParameter("u");

        PrintWriter pw = response.getWriter();

        try {
            pw.append("Client: user name is " + userName);
            String token = TrustServiceUtil.getSecurityToken( userName );
            pw.append( ClientS2SCall.callOtherServer( token ) );

            pw.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            pw.close();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
    }

}
