package org.nthing.persons.client.dto;

import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.persons.enums.Gender;

import java.time.LocalDate;

public record ClientDTO(NameDTO name,
                        LocalDate birthDate,
                        Integer age,
                        String cpf,
                        Gender gender,
                        AddressDTO address,
                        String phone,
                        String email,
                        String password,
                        Long enrollmentNumber,
                        String instagram) {


}
