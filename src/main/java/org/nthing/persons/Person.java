package org.nthing.persons;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.nthing.embeddable.Address;
import org.nthing.embeddable.Name;
import org.nthing.enums.Gender;
import org.nthing.enums.converters.GenderConverter;

import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
public abstract class Person extends PanacheEntity {

    @Embedded
    @NotNull
    public Name name;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    public LocalDate birthDate;



    @Positive
    @Column(length = 2)
    public Integer age;

    @NotNull
    @NotBlank
    @CPF
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas dígitos numéricos")
    @Column(unique = true, nullable = false)
    public String cpf;

    @NotNull
    @Column(nullable = false, length = 12)
    @Convert(converter = GenderConverter.class)
    public Gender gender;

    @Embedded
    @NotNull
    public Address address;

    @NotNull
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d+$", message = "O campo deve conter apenas dígitos numéricos")
    @Column(nullable = false, length = 11, unique = true)
    public String phone;

    @NotNull
    @Email(message = "O email deve ser válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(nullable = false, unique = true)
    public String email;

    @NotNull
    @NotBlank
    @Size(min = 12, message = "A senha deve ter no mínimo 12 caracteres.")
    @Column(nullable = false)
    public String password;


   /* @PrePersist
    public void calcAge() {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(this.birthDate, today);
        this.age = calcAge.getYears();
    }*/

    @PreUpdate
    public void updateAge() {
        LocalDate today = LocalDate.now();
        Period calcAge = Period.between(this.birthDate, today);
        this.age = calcAge.getYears();
    }

    public Person() {
    }

    public Person(Name name, LocalDate birthDate, Integer age, String cpf, Gender gender, Address address,
                  String phone, String email, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
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
