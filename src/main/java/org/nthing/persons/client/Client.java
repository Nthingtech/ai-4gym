package org.nthing.persons.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.nthing.embeddable.Address;
import org.nthing.embeddable.Name;
import org.nthing.enums.Gender;
import org.nthing.persons.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(indexes = {@Index(name = "idx_enrollmentNumber", columnList = "enrollmentNumber")})
public class Client extends Person {

    @Column(unique = true)
    public Long enrollmentNumber = id;

    @NotNull
    @Size(max = 30)
    @Column(unique = true, length = 35)
    public String instagram;

    public Client() {
    }

    public Client(Name name, LocalDate birthDate, Integer age, String cpf, Gender gender, Address address, String phone, String email, String password, String status, Long enrollmentNumber, String instagram) {
        super(name, birthDate, age, cpf, gender, address, phone, email, password, status);
        this.enrollmentNumber = enrollmentNumber;
        this.instagram = instagram;
    }

    @PrePersist
    public void setEnrollmentNumber() {
        this.enrollmentNumber = id;
    }

    public static List<Client> clients() {
        return list("order by lower(name)");
    }

    public static List<Client> findByFullName(String fullName) {
        return find("lower(name.firstName) like lower(?1) or lower(name.lastName) like lower(?1) " +
                "ORDER BY name.firstName, name.lastName","%" + fullName + "%").list();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(enrollmentNumber, client.enrollmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enrollmentNumber);
    }
}
