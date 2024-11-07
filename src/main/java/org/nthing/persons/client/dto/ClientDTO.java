package org.nthing.persons.client.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.embeddable.dto.AddressDTO;
import org.nthing.embeddable.dto.NameDTO;
import org.nthing.enums.Gender;
import org.nthing.enums.validation.ValueOfEnum;

import java.time.LocalDate;

public record ClientDTO(

        @NotNull
        NameDTO name,

        @NotNull
        LocalDate birthDate,

        Integer age,

        @NotNull @NotBlank @CPF @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos")
        String cpf,

        @NotNull @ValueOfEnum(enumClass = Gender.class)
        String gender,

        @NotNull @Valid
        AddressDTO address,

        @NotNull @Size(min = 10, max = 11) @Pattern(regexp = "^\\d+$", message = "O campo deve conter apenas dígitos numéricos")
        String phone,

        @NotNull @Email(message = "O email deve ser válido", regexp = "^[\\w-.]+@[\\w-]+\\.[\\w-]{2,4}$") @Size(min = 6, max = 35)
        String email,

        @NotNull @NotBlank @Size(min = 12, max = 30, message = "A senha deve ter no mínimo 12 caracteres.")
        String password,

        Long enrollmentNumber,

        @NotNull
        @Size(max = 30)
        String instagram) {


}
