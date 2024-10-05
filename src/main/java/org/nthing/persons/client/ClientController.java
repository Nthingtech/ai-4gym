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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GET
    @Path("listName")
    public List<Client> clientsByNameBirthDate(){
        return clientService.clientsByNameBirthDate();
    }

    @GET
    @Path("list")
    public List<Client> clientList(){
        return clientService.clientsList();
    }

    @GET
    @Path("list-inactive")
    public List<Client> clientListInactive(){
        return clientService.getAllRecords();
    }

    @GET
    @Path("{id}")
    public Client findByIdClient(@Positive @NotNull Long id) {
        Client client = clientService.findByIdClient(id);
        if (client == null || client.id <= 0) {
            throw new WebApplicationException("Cliente com o id " + id + " não existe", 404);
        }
        return client;
    }

    @POST
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
    @Transactional
    @Path("{id}")
    public Client updateClient(@Positive @NotNull Long id, @Valid Client client){
        Client exsitingClient = Client.findById(id);
        if (exsitingClient == null) {
            throw new WebApplicationException("Client whit id of " + id + " does not exist", 404);
        }
        return clientService.updateClient(id, client);
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response delete(@Positive @NotNull Long id) {
        Client client = Client.findById(id);
        if (client == null) {
            throw new WebApplicationException("Client whit id of " + id + " does not exist", 404);
        }
        clientService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Transactional
    @Path("/undo-delete-client/{id}")
    public Client undoDeleteClient(@Positive @NotNull Long id, Client client){ //TODO wait converter enum
        Client exsitingClient = Client.findById(id);
        if (exsitingClient == null) {
            throw new WebApplicationException("Client whit id of " + id + " does not exist", 404);
        }
        return clientService.undoDelete(id, client);
    }

}
