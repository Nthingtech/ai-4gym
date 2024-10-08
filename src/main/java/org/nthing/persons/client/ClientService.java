package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
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


    private final EntityManager entityManager;

    public ClientService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> clientsByNameBirthDate() {
        return Client.clientsByNameBirthDate();
    }

    public List<Client> allClientsList() {
        return Client.listAll();
    }

    public List<Client> clientListInactive() {
        String sql = "SELECT * FROM Client WHERE status = 'Inativo'";
        return entityManager.createNativeQuery(sql, Client.class).getResultList();
    }

    public Client findByIdClient(@Positive @NotNull Long id) {
        return Client.findById(id);
    }

    public List<Client> findClientByFullName(String fullName) {
        return Client.findClientByFullName(fullName);
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

    public void reactivateClient(Long id) {//TODO wait converter enum
        String sql = "UPDATE Client SET status = 'Ativo' WHERE id = :id";
        entityManager.createNativeQuery(sql)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void delete (Long id) {
        Client client = Client.findById(id);
        client.delete();
    }

    public void hardDeleteById(Long id) {
        String sql = "DELETE FROM Client WHERE id = ?1";
        entityManager.createNativeQuery(sql)
                .setParameter(1, id)
                .executeUpdate();
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
}