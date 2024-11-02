package org.nthing.persons.client.dto.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.nthing.embeddable.Address;
import org.nthing.embeddable.Name;
import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.enums.Gender;
import org.nthing.enums.Status;
import org.nthing.persons.client.Client;
import org.nthing.persons.client.dto.ClientDTO;



@ApplicationScoped
public class ClientMapper {


    public ClientDTO toDTOBuilder(Client client) {

        return new ClientDTO(
                new NameDTO(client.name.firstName, client.name.lastName),
                client.birthDate,
                client.age,
                client.cpf,
                client.gender.getValue(),
                new AddressDTO(client.address.residenceNumber, client.address.street, client.address.district, client.address.city,
                        client.address.state, client.address.zipcode, client.address.complement),
                client.phone,
                client.email,
                client.password,
                client.enrollmentNumber,
                client.instagram
        );
    }

    public Client toEntityBuilder(ClientDTO clientDTO) {

        return Client.clientBuilder()
                .name(new Name(clientDTO.name().firstName(), clientDTO.name().lastName()))
                .birthDate(clientDTO.birthDate())
                .age(clientDTO.age())
                .cpf(clientDTO.cpf())
                .gender(convertGenderValue(clientDTO.gender()))
                .address(new Address(clientDTO.address().residenceNumber(), clientDTO.address().street(),
                        clientDTO.address().district(), clientDTO.address().city(), clientDTO.address().state(),
                        clientDTO.address().zipcode(), clientDTO.address().complement()))
                .phone(clientDTO.phone())
                .email(clientDTO.email())
                .password(clientDTO.password())
                .enrollmentNumber(clientDTO.enrollmentNumber())
                .instagram(clientDTO.instagram())
                .build();
    }

    public Gender convertGenderValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Mulher Trans"         -> Gender.MULHER_TRANS;
            case "Homem Trans"          -> Gender.HOMEM_TRANS;
            case "Mulher Cis"           -> Gender.MULHER_CIS;
            case "Homem Cis"            -> Gender.HOMEM_CIS;
            case "Não Binário"          -> Gender.NAO_BINARIO;
            case "Outro"                -> Gender.OUTRO;
            case "Prefiro não informar" -> Gender.PREFIRO_NAO_INFORMAR;
            default                     -> throw new IllegalArgumentException("Gênero inválido.");
        };
    }

    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Ativo"         -> Status.ACTIVE;
            case "Homem Trans"   -> Status.INACTIVE;
            default              -> throw new IllegalArgumentException("Status inválido.");
        };
    }

}
