package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.nthing.exceptions.BusinessException;
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

    public List<ClientDTO> clientsListByName() {
        return Client.clientsListByName()
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthDate() {
        return Client.clientsByBirthDate()
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> allClientsList() {
        return Client.<Client>listAll()
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }


    public List<ClientDTO> clientListInactive() {
        return Client.clientListInactive()
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO findByIdClient(@NotNull Long id) {
        return Client.<Client>findByIdOptional(id)
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }


    public ClientDTO findByIdInactive(@NotNull Long id) {
        try {
            Client client = Client.findByIdInactive(id);
            return /*clientMapper.toDto(client)*/ClientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new RecordNotFoundException(id);
        }
    }

    public ClientDTO findByCpfInactive(@NotNull @NotBlank String cpf) {
        try {
            Client client = Client.findByCpfInactive(cpf);
            return /*clientMapper.toDto(client)*/ClientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new BusinessException("CPF inexistente.");
        }
    }

    public ClientDTO findByCpf(@NotNull @NotBlank String cpf) {
        return Client.<Client>find("cpf", cpf).singleResultOptional()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .orElseThrow(() -> new BusinessException("Cpf inexistente."));
    }

    public List<ClientDTO> findClientByName(@NotNull @NotBlank String fullName) {
        return Client.findClientByName(fullName)
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthMonth(@NotNull int month) {
        if (month < 1 || month > 12) {
            throw new BusinessException("Mês inexistente.");
        }
        return Client.clientsByBirthMonth(month)
                .stream()
                .map(/*clientMapper::toDto*/ClientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO createClient(@Valid ClientDTO newClientDTO) {
        Client newClient = /*clientMapper.toEntity(newClientDTO)*/ClientMapper.toEntityBuilder(newClientDTO);
        calcAge(newClient);
        newClient.persist();
        return /*clientMapper.toDto(newClient)*/ClientMapper.toDTOBuilder(newClient);
    }

    public ClientDTO updateClient(@NotNull Long id, @Valid Client client) {
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
        return /*clientMapper.toDto(existingClient)*/ClientMapper.toDTOBuilder(existingClient);

    }

    public void reactivateClient(Long id) {

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
    private void calcAge(Client client) {
       calcAndSetAge(client);
    }

    @PreUpdate
    private void updateAge(Client client) {
        calcAndSetAge(client);
    }

    @PrePersist
    public void calcAgeTest(Client client) {//TODO Remove after tests
        calcAndSetAge(client);
    }

    private void calcAndSetAge(Client client) {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(client.birthDate, today);
        client.age = calcAge.getYears();
    }
}