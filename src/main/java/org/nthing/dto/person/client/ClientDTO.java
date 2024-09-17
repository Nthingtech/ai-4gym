package org.nthing.dto.person.client;

import org.nthing.dto.embeddable.AddressDTO;
import org.nthing.dto.embeddable.NameDTO;
import org.nthing.dto.embeddable.PhoneDTO;
import org.nthing.model.embeddable.Address;
import org.nthing.model.embeddable.Name;
import org.nthing.model.embeddable.Phone;
import org.nthing.model.person.Client;
import org.nthing.model.enums.Gender;

import java.time.LocalDate;

public record ClientDTO(NameDTO name, LocalDate birthDate, String cpf, Gender gender, AddressDTO address,
                        PhoneDTO phone, String email, String password, Long enrollmentNumber, String instagram) {

    public Client toClient() {
        var clientEntity = new Client(
            new Name(this.name.firstName(), this.name.lastName()),
            this.birthDate,
            this.cpf,
            this.gender,
            new Address(
            this.address.residenceNumber(),
            this.address.street(),
            this.address.district(),
            this.address.city(),
            this.address.state(),
            this.address.zipcode()
            ),
            new Phone(this.phone().phone(), this.phone.type()),
            this.email,
            this.password,
            this.enrollmentNumber,
            this.instagram
        );
        return clientEntity;
    }

}
