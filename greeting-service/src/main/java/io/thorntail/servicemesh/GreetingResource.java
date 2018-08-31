package io.thorntail.servicemesh;

import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 * @author Ken Finnigan
 */
@Path("/")
public class GreetingResource {

    private static final String NAME_SERVICE_URL = "http://name-service:8080";

    @GET
    @Path("/greeting")
    @Produces("application/json")
    public Response greeting() throws Exception {
        NameService nameService =
                RestClientBuilder.newBuilder()
                        .baseUrl(new URL(NAME_SERVICE_URL))
                        .build(NameService.class);

        String name = nameService.getName();

        return Response.ok()
                .entity(new Greeting(String.format("Hello %s", name)))
                .build();
    }

    static class Greeting {
        private final String content;

        public Greeting(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

}