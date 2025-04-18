package org.nthing.persons.clients;

import jakarta.inject.Singleton;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.nthing.exceptions.BusinessException;
import org.nthing.exceptions.RecordNotFoundException;
import org.nthing.persons.clients.dtos.ClientDTO;
import org.nthing.persons.clients.mapper.ClientMapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Singleton
public class ClientService {

    private final ClientMapper clientMapper;
    public ClientService(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;

    }

    public List<ClientDTO> clientsListByName() {
        return Client.clientsListByName()
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthDate() {
        return Client.clientsByBirthDate()
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> allClientsList() {
        return Client.<Client>listAll()
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }


    public List<ClientDTO> clientListInactive() {
        return Client.clientListInactive()
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO findByIdClient(@NotNull Long id) {
        return Client.<Client>findByIdOptional(id)
                .map(clientMapper::toDTOBuilder)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }


    public ClientDTO findByIdInactive(@NotNull Long id) {
        try {
            Client client = Client.findByIdInactive(id);
            return clientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new RecordNotFoundException(id);
        }
    }

    public ClientDTO findByCpfInactive(@NotNull @NotBlank String cpf) {
        try {
            Client client = Client.findByCpfInactive(cpf);
            return clientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new BusinessException("CPF inexistente.");
        }
    }

    public ClientDTO findByCpf(@NotNull @NotBlank String cpf) {
        return Client.<Client>find("cpf", cpf).singleResultOptional()
                .map(clientMapper::toDTOBuilder)
                .orElseThrow(() -> new BusinessException("Cpf inexistente."));
    }

    public List<ClientDTO> findClientByName(@NotNull @NotBlank String fullName) {
        return Client.findClientByName(fullName)
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthMonth(@NotNull int month) {
        if (month < 1 || month > 12) {
            throw new BusinessException("Mês inexistente.");
        }
        return Client.clientsByBirthMonth(month)
                .stream()
                .map(clientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO createClient(@Valid ClientDTO newClientDTO) {
        Client newClient = clientMapper.toEntityBuilder(newClientDTO);
        calcAge(newClient);
        newClient.persist();
        return clientMapper.toDTOBuilder(newClient);
    }

     public ClientDTO updateClient(@NotNull Long id, @Valid ClientDTO clientDTO) {
        Client existingClient = (Client)Client.findByIdOptional(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        existingClient.name.firstName = clientDTO.name().firstName();
        existingClient.name.lastName = clientDTO.name().lastName();
        existingClient.birthDate = clientDTO.birthDate();
        existingClient.cpf = clientDTO.cpf();
        existingClient.gender = clientMapper.convertGenderValue(clientDTO.gender());
        existingClient.address.residenceNumber = clientDTO.address().residenceNumber();
        existingClient.address.street = clientDTO.address().street();
        existingClient.address.district = clientDTO.address().street();
        existingClient.address.city = clientDTO.address().city();
        existingClient.address.state = clientDTO.address().state();
        existingClient.address.zipcode = clientDTO.address().zipcode();
        existingClient.address.complement = clientDTO.address().complement();
        existingClient.phone = clientDTO.phone();
        existingClient.email = clientDTO.email();
        existingClient.password = clientDTO.password();
        existingClient.instagram = clientDTO.instagram();
        updateAge(existingClient);
        return clientMapper.toDTOBuilder(existingClient);
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
           Client.findByIdActiveInactive(id);
       } catch (NoResultException e) {
           throw new RecordNotFoundException(id);
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
    }//TODO REMOVE AFTER TESTS

    private void calcAndSetAge(Client client) {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(client.birthDate, today);
        client.age = calcAge.getYears();
    }

}