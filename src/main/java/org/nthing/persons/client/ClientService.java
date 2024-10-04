package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@ApplicationScoped
public class ClientService {


    public List<Client> clientsByNameBirthDate() {
        return Client.clientsByNameBirthDate();
    }

    public List<Client> clientsList() {
        return Client.listAll();
    }



    public Client createClient(@Valid Client newClient) {
        calcAge(newClient);
        newClient.persist();
        return newClient;
    }

    public Client updateClient(@Positive @NotNull Long id, @Valid Client client) {
        Client existingClient = Client.findById(id);
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
        updateAge(existingClient);
        return existingClient;
    }

    @PrePersist
    public void calcAge(Client client) {
       calcAndSetAge(client);
    }

    @PreUpdate
    public void updateAge(Client client) {
        calcAndSetAge(client);
    }

    private void calcAndSetAge(Client client) {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(client.birthDate, today);
        client.age = calcAge.getYears();
    }

    public Client findBYId(Long id) {
        return Client.findById(id);
    }
}
