package org.nthing.persons.client;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-birthdate")
    public List<Client> clientsByBirthDate(){
        return clientService.clientsByBirthDate();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public List<Client> allClientList(){
        return clientService.allClientsList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list-inactive")
    public List<Client> clientListInactive(){
        return clientService.clientListInactive();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Client findByIdClient(@Positive @NotNull Long id) {
        return clientService.findByIdClient(id);
    }

    @GET
    @Path("list-name/{fullName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findClientByFullName(String fullName) {
        List<Client> clients = clientService.findClientByFullName(fullName);
        if (!clients.isEmpty()) {
            return Response.status(Response.Status.OK).entity(clients).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("client-name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName() {
        List<Client> clients = Client.clients();
        if (!clients.isEmpty()) {
            return Response.status(Response.Status.OK).entity(clients).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/birthMonth/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> clientsByBirthMonth(int month) {
        return clientService.clientsByBirthMonth(month);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("many-clients")
    public Response saveClients(@Valid List<Client> clients) {//TODO for mock postman --delete after test
        for (Client client : clients) {
            clientService.calcAge(client);
            client.persist();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("create")
    public Response createClient(@Valid Client newClient) {
        if (newClient.id !=null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        Client createdClient = clientService.createClient(newClient);
        return Response.status(Response.Status.CREATED).entity(createdClient).build() ;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("{id}")
    public Client updateClient(@Positive @NotNull Long id, @Valid Client client){
        return clientService.updateClient(id, client);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/reactivate-client/{id}")
    public Response reactivateClient(@Positive @NotNull Long id){ //TODO wait converter enum
        clientService.reactivateClient(id);
        return Response.noContent().entity(id).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("{id}")
    public void delete(@Positive @NotNull Long id) {
        clientService.delete(id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/hard-delete/{id}")
    public Response hardDelete(@Positive @NotNull Long id) {
        clientService.hardDeleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
