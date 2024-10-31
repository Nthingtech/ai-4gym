package org.nthing.persons.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.nthing.embeddable.Name;
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
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthDate() {
        return Client.clientsByBirthDate()
                .stream()
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> allClientsList() {
        return Client.<Client>listAll()
                .stream()
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }


    public List<ClientDTO> clientListInactive() {
        return Client.clientListInactive()
                .stream()
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO findByIdClient(@NotNull Long id) {
        return Client.<Client>findByIdOptional(id)
                .map(ClientMapper::toDTOBuilder)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }


    public ClientDTO findByIdInactive(@NotNull Long id) {
        try {
            Client client = Client.findByIdInactive(id);
            return ClientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new RecordNotFoundException(id);
        }
    }

    public ClientDTO findByCpfInactive(@NotNull @NotBlank String cpf) {
        try {
            Client client = Client.findByCpfInactive(cpf);
            return ClientMapper.toDTOBuilder(client) ;
        } catch (NoResultException e) {
            throw new BusinessException("CPF inexistente.");
        }
    }

    public ClientDTO findByCpf(@NotNull @NotBlank String cpf) {
        return Client.<Client>find("cpf", cpf).singleResultOptional()
                .map(ClientMapper::toDTOBuilder)
                .orElseThrow(() -> new BusinessException("Cpf inexistente."));
    }

    public List<ClientDTO> findClientByName(@NotNull @NotBlank String fullName) {
        return Client.findClientByName(fullName)
                .stream()
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }

    public List<ClientDTO> clientsByBirthMonth(@NotNull int month) {
        if (month < 1 || month > 12) {
            throw new BusinessException("Mês inexistente.");
        }
        return Client.clientsByBirthMonth(month)
                .stream()
                .map(ClientMapper::toDTOBuilder)
                .toList();
    }

    public ClientDTO createClient(@Valid ClientDTO newClientDTO) {
        Client newClient = ClientMapper.toEntityBuilder(newClientDTO);
        calcAge(newClient);
        newClient.persist();
        return ClientMapper.toDTOBuilder(newClient);
    }

   /* public ClientDTO updateClient(@NotNull Long id, @Valid Client client) {//todo video enumtype parte 2 10:58
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
        return ClientMapper.toDTOBuilder(existingClient);
    }*/

    public ClientDTO updateClient(@NotNull Long id, @Valid ClientDTO clientDTO) {//todo video enumtype parte 2 10:58
        return Client.findByIdOptional(id)
                .map( existClient -> {
                    Client updateClient = Client.clientBuilder()
                            .name(new Name(clientDTO.name().firstName(), clientDTO.name().lastName()))
                            .birthDate(clientDTO.birthDate())
                            .age(clientDTO.age())
                            .cpf()//TODO
        })
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