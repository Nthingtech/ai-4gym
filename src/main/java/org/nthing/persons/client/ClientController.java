package org.nthing.persons.client;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.Consumes;
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
    @Path("list")
    public List<Client> clientList(){
        return Client.listAll();
    }

    @GET
    @Path("{id}")
    public Client findByIdClient(@Positive @NotNull Long id) {
        Client client = Client.findById(id);
        if (client == null || client.id <= 0) {
            throw new WebApplicationException("Client with id of " + id + " does not exist", 404);
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
    public Client updateClient(Long id, Client client){
        Client existingClient = Client.findById(id);

        if (existingClient ==null) {
            throw new WebApplicationException("Client whit id of " + id + " does not exist", 404);
        }

        existingClient.name.firstName = client.name.firstName;
        existingClient.name.lastName = client.name.lastName;
        existingClient.birthDate = client.birthDate;
        existingClient.cpf = client.cpf;
        existingClient.gender = client.gender;
        existingClient.address.residenceNumber = client.address.residenceNumber;
        existingClient.address.street = client.address.street;
        existingClient.address.district = client.address.street;
        existingClient.address.city = client.address.city;
        existingClient.address.state = client.address.state;
        existingClient.address.zipcode = client.address.zipcode;
        existingClient.address.complement = client.address.complement;
        existingClient.phone = client.phone;
        existingClient.email = client.email;
        existingClient.password = client.password;
        existingClient.instagram = client.instagram;

        return existingClient;
    }





}
