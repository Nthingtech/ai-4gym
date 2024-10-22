package org.nthing.persons.client.dto.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.persons.client.Client;
import org.nthing.persons.client.dto.ClientDTO;

@ApplicationScoped
public class ClientMapper {

    public ClientDTO toDto(Client client) {
        return new ClientDTO(client.id, new NameDTO(client.name.firstName, client.name.lastName), client.birthDate, client.age,
                client.cpf, client.status,
                new AddressDTO(client.address.residenceNumber, client.address.street, client.address.district, client.address.city, client.address.state, client.address.zipcode),
                client.phone, client.email, client.password, client.enrollmentNumber, client.instagram);
    }
}
