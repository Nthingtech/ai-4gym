package org.nthing.model.person;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.model.embeddable.Address;
import org.nthing.model.embeddable.Name;
import org.nthing.model.embeddable.Phone;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Person extends PanacheEntity {

    @Embedded
    @NotNull
    public Name name;

    @NotNull
    @Column(nullable = false)
    public LocalDate birthDate;

    @NotNull
    @CPF
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos")
    @Column(unique = true, nullable = false)
    public String cpf;

    @NotNull
    @Column(nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Embedded
    @NotNull
    public Address address;

    @Embedded
    @NotNull
    public Phone phone;

    @NotNull
    @Email(message = "O email deve ser válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(nullable = false, unique = true)
    public String email;

    @NotNull
    @Column(nullable = false)
    public String password;

    public Person() {
    }

    public Person(Name name, LocalDate birthDate, String cpf, Gender gender, Address address,
                  Phone phone, String email, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }



    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", birthDate=" + birthDate +
                ", cpf='" + cpf + '\'' +
                ", gender=" + gender +
                ", address=" + address +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
