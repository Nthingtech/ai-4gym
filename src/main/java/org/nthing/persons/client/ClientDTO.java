package org.nthing.persons.client;

import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.persons.client.enums.Gender;

import java.time.LocalDate;

public record ClientDTO(NameDTO name, LocalDate birthDate, String cpf, Gender gender, AddressDTO address,
                        String phone, String email, String password, Long enrollmentNumber, String instagram) {


}
