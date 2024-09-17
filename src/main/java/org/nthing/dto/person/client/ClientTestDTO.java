package org.nthing.dto.person.client;

import org.nthing.model.embeddable.Address;
import org.nthing.model.embeddable.Name;
import org.nthing.model.embeddable.Phone;
import org.nthing.model.person.Client;
import org.nthing.model.enums.Gender;

import java.time.LocalDate;

public record ClientTestDTO(Name name, LocalDate birthDate, String cpf, Gender gender, Address address,
                            Phone phone, String email, String password, Long enrollmentNumber, String instagram) {

    public Client toClient() {
        var clientEntity = new Client(
                new Name(this.name.firstName, this.name.lastName),
                this.birthDate,
                this.cpf,
                this.gender,
                new Address(
                        this.address.residenceNumber,
                        this.address.street,
                        this.address.district,
                        this.address.city,
                        this.address.state,
                        this.address.zipcode
                ),
                new Phone(this.phone.number, this.phone.type),
                this.email,
                this.password,
                this.enrollmentNumber,
                this.instagram
        );
        return clientEntity;
    }

}
