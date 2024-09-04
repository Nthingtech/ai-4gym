package org.nthing.model.person;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@MappedSuperclass
public class Person {

    @NotNull
    @Column(nullable = false, length = 100)
    private String name;
    private LocalDate birthDate;

    @NotNull
    @CPF
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números e ter 11 dígitos")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull
    @Column(unique = true, nullable = false)
    private String rg;


    @NotNull
    private String address;

    @NotNull
    @Length(min = 11, max = 11)
    @Column(nullable = false, unique = true)
    private String phone;

    @NotNull
    @Email(message = "O email deve ser válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, unique = true)
    private String password;

}
