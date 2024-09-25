package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {


   /* private final ClientService clientService;

    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }*/


    @GET
    @Path("/list")
    public List<Client> clientList(){
        return Client.listAll();
    }

    @POST
    @Transactional
    public Response createClient(@Valid Client client) {
        if (client.id !=null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(client.birthDate, today);
        client.age = calcAge.getYears();
        client.persist();
        return Response.status(Response.Status.CREATED).entity(client).build() ;
    }

}
