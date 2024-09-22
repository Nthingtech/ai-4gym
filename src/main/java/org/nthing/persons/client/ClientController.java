package org.nthing.persons.client;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {

    @GET
    @Path("/list")
    public List<Client> clientList(){
        return Client.listAll();
    }

    @POST
    @Transactional
    public Response createClient(Client client) {
        client.persist();
        return Response.created(URI.create("/clients/" + client.id)).build() ;
    }
}
