package oracle.social.opss.client;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ClientS2SCall {
    public static final String AUTH_TYPE_NAME = "OIT";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SPACE = " ";

    public static String callOtherServer(String token) throws Exception {
        HttpResponse response = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();

            HttpRequestBase request = new HttpGet("http://adc6170696.us.oracle.com:7004/opssServer/Server");

            request.setHeader(AUTHORIZATION, AUTH_TYPE_NAME + SPACE + token);

            response = client.execute(request);

            return EntityUtils.toString(response.getEntity());

        }
        finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }

    }
}
