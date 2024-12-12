package org.nthing.persons;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.embeddable.Address;
import org.nthing.embeddable.Name;
import org.nthing.enums.Gender;
import org.nthing.enums.Status;
import org.nthing.enums.converters.GenderConverter;
import org.nthing.enums.converters.StatusConverter;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Person extends PanacheEntity {

    @Valid
    @Embedded
    @NotNull
    public Name name;

    @NotNull
    @Past(message = "A data de nascimento deve ser uma data passada.")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    public LocalDate birthDate;

    @NotNull
    @Max(99)
    @Column(length = 2)
    public Integer age = 0;

    @NotNull
    @NotBlank
    @CPF
    @Size(max = 11)
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos")
    @Column(unique = true, nullable = false, length = 15)
    public String cpf;

    @NotNull
    @Column(nullable = false, length = 22)
    @Convert(converter = GenderConverter.class)
    public Gender gender;

    @Valid
    @Embedded
    @NotNull
    public Address address;

    @NotNull
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d+$", message = "O campo deve conter apenas dígitos numéricos")
    @Column(nullable = false, length = 11, unique = true)
    public String phone;

    @NotNull
    @Email(message = "O email deve ser válido", regexp = "^[\\w-.]+@[\\w-]+\\.[\\w-]{2,4}$")
    @Size(min = 6, max = 35)
    @Column(nullable = false, unique = true, length = 35)
    public String email;

    @NotNull
    @NotBlank
    @Size(min = 12, max = 30, message = "A senha deve ter no mínimo 12 caracteres.")
    @Column(nullable = false, length = 30)
    public String password;

    @NotNull
    @Column(length = 9, nullable = false)
    @Convert(converter = StatusConverter.class)
    public Status status = Status.ACTIVE;

}
