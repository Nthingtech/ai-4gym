package org.nthing.persons.client;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.nthing.persons.client.dto.ClientDTO;

import java.util.List;


@Path("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GET
    @Path("list-by-name")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> listByName() {
        return clientService.clientsListByName();
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
    public List<ClientDTO> allClientList(){
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
    @Path("find-by-name/{fullName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> findClientByFullName(@NotNull @NotBlank String fullName) {
         return clientService.findClientByFullName(fullName);
    }

    @GET
    @Path("client-inactive-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client findByInactive(@Positive @NotNull Long id) {
        return clientService.findByIdInactive(id);
    }

    @GET
    @Path("/birthMonth/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> clientsByBirthMonth(@Positive @NotNull @Max(12) int month) {
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
    public Client createClient(@Valid Client newClient) {
        return clientService.createClient(newClient);
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
    public void reactivateClient(@Positive @NotNull Long id){ //TODO wait converter enum
        clientService.reactivateClient(id);
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
    public void hardDelete(@Positive @NotNull Long id) {
        clientService.hardDeleteById(id);
    }
}
