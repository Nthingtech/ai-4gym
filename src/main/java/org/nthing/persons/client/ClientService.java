package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.nthing.exceptions.RecordNotFoundException;
import org.nthing.persons.client.dto.ClientDTO;
import org.nthing.persons.client.dto.mapper.ClientMapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@ApplicationScoped
public class ClientService {

    private final ClientMapper clientMapper;

    public ClientService(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public List<Client> clientsListByName() {
        return Client.clientsListByName();
    }

    public List<Client> clientsByBirthDate() {
        return Client.clientsByBirthDate();
    }

    public List<ClientDTO> allClientsList() {
        return Client.<Client>listAll()
                .stream()
                .map(clientMapper::toDto)
                /*.collect(Collectors.toList());TODO IMUTABLE OR NOT*/
                .toList();
    }


    public List<Client> clientListInactive() {
        return Client.clientListInactive();
    }

    public Client findByIdClient(@Positive @NotNull Long id) {
        return (Client) Client.findByIdOptional(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Client findByIdInactive(@Positive @NotNull Long id) {
        try {
            return Client.findByIdInactive(id);
        } catch (NoResultException e) {
            throw new RecordNotFoundException(id);
        }
    }

    public List<Client> findClientByFullName(@NotNull @NotBlank String fullName) {
        return Client.findClientByFullName(fullName);
    }

    public List<Client> clientsByBirthMonth(@Positive @NotNull @Max(12) int month) {
       /* if (month < 1 || month > 12) { //TODO MAKE NEW CONSTRAINT VIOLATION
            throw new BusinessException("Número do mês inexistente.");
        }*/
        return Client.clientsByBirthMonth(month);
    }

    public Client createClient(@Valid Client newClient) {
        calcAge(newClient);
        newClient.persist();
        return newClient;
    }

    public Client updateClient(@Positive @NotNull Long id, @Valid Client client) {
        Client existingClient = (Client)Client.findByIdOptional(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
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

    public void reactivateClient(Long id) {//TODO wait converter enum AND USE PANACHEQUERY

      try {
          Client.findByIdInactive(id);
      } catch (NoResultException e) {
          throw new RecordNotFoundException(id);
      }

      Client.reactivateClient(id);

    }

    public void delete (Long id) {
        Client.findByIdOptional(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        Client.deleteById(id);
    }

    public void hardDeleteById(Long id) {
       try {
           Client.findByIdInactive(id);
       } catch (NoResultException e) {
           throw new  RecordNotFoundException(id);
       }
        Client.hardDeleteById(id);
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