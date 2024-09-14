package org.nthing.model.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.nthing.model.embeddable.Address;
import org.nthing.model.embeddable.Name;
import org.nthing.model.embeddable.Phone;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //TODO REMOVE
@Table(indexes = {@Index(name = "idx_enrollmentNumber", columnList = "enrollmentNumber")})
public class Client extends Person {

    @NotNull
    @Column(nullable = false, unique = true)
    public Long enrollmentNumber = id;

    @Column
    public String instagram;

    public Client() {
    }

    public Client(Name name, LocalDate birthDate, String cpf, Gender gender, Address address, Phone phone, String email, String password, Long enrollmentNumber, String instagram) {
        super(name, birthDate, cpf, gender, address, phone, email, password);
        this.enrollmentNumber = enrollmentNumber;
        this.instagram = instagram;
    }

    public static List<Client> clients() {
        return Client.list("order by lower(name)");
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
