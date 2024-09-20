package org.nthing.persons.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.enums.Gender;
import org.nthing.enums.validation.ValueOfEnum;

import java.time.LocalDate;

public record ClientDTO(
        Long id,
        @NotNull @NotBlank NameDTO name,
        @NotNull LocalDate birthDate,
        @NotNull @Positive Integer age,
        @NotNull @NotBlank @CPF @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos") String cpf,
        @NotNull @NotBlank @ValueOfEnum(enumClass = Gender.class) String gender,
        AddressDTO address,
        String phone,
        String email,
        String password,
        Long enrollmentNumber,
        String instagram) {


}
