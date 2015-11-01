package com.invest.pro.application.resources;

import com.invest.pro.application.model.transaction.TMutualFundDetail;
import com.invest.pro.application.test.main.resources.AuthenticatedBaseResourceTest;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 8/20/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PingResourceTest extends AuthenticatedBaseResourceTest {

    @Test
    public void testMutualFund() {
        TMutualFundDetail tMutualFundDetail = new TMutualFundDetail();
        ClientResponse response = createClient().resource(RULE.genTestURL(MutualFundResource.PATH+"/add"))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
               .entity(tMutualFundDetail)
                .post(ClientResponse.class);
        System.out.println("====================="+response.getClientResponseStatus());
        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        response.close();

    }

    //@Path("/ping")
    /*public static class PingResource {
      static String  ROOT_PATH = "/";
      public static final String PATH = ROOT_PATH + "ping";
        @GET
        public String ping() {
            return "pong";
        }
    }

    @Test
    public void shouldPing() throws IOException {
        ClientResponse crt = createClient().resource(RULE.genTestURL(PingResource.PATH))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                        // .header(AUTHORIZATION_HEADER, RULE.getBasicAuthenticationHeader())
                .get(ClientResponse.class);

       // final URL url = new URL("/ping");
       // final String response = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
        assertEquals("pong", crt);
    }*/

}
