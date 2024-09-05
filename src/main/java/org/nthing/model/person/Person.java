package org.nthing.model.person;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.model.embeddable.Address;
import org.nthing.model.embeddable.Name;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Person extends PanacheEntity {

    @NotNull
    @Column(nullable = false, length = 100)
    public Name name;

    @NotNull
    @Column(nullable = false)
    public LocalDate birthDate;

    @NotNull
    @CPF
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos")
    @Column(/*unique = true,*/ nullable = false)
    public String cpf;

    @NotNull
    @Column(/*unique = true, */nullable = false)
    public String rg;

    @NotNull
    @Column(nullable = false, length = 12)
    public Gender gender;

    @Embedded
    @NotNull
    @Column(nullable = false)
    public Address address;

    @NotNull
    @Length(min = 11, max = 11)
    @Column(nullable = false/*, unique = true*/)
    public String phone;

    @NotNull
    @Email(message = "O email deve ser válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(nullable = false/*, unique = true*/)
    public String email;

    @NotNull
    @Column(nullable = false/*, unique = true*/)
    public String password;



}
