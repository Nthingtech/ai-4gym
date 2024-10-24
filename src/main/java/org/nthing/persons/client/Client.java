package org.nthing.persons.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.nthing.embeddable.Address;
import org.nthing.embeddable.Name;
import org.nthing.enums.Gender;
import org.nthing.persons.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NamedNativeQuery(
        name = "Client.findInactive",
        query = "SELECT * FROM Client WHERE status = 'Inativo'",
        resultClass = Client.class
)
@NamedNativeQuery(
                name = "Client.findByIdAndInactive",
                query = "SELECT * FROM Client WHERE id = :id AND status = 'Inativo'",
                resultClass = Client.class
)

@Entity
@Table(indexes = {@Index(name = "idx_fullname", columnList = "firstName, lastName")})
@SQLDelete(sql = "UPDATE Client SET status = 'Inativo' WHERE id= ?")
@SQLRestriction("status <> 'Inativo'")
public class Client extends Person {


    @Column(unique = true)
    public Long enrollmentNumber;

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

    public static List<Client> clientsByBirthDate() {
        return find("ORDER BY birthDate").list();
    }

    public static List<Client> clientsListByName() {
        return list("order by lower(name.firstName), lower(name.lastName)");
    }


    public static List<Client> findClientByName(String fullName) {
        String trimmedFullName = fullName.trim().replaceAll(" +", " ").toLowerCase();
        return find("LOWER(concat(name.firstName, ' ', name.lastName)) LIKE ?1", "%" + trimmedFullName + "%").list();
    }


    public static List<Client> clientsByBirthMonth (int month) {
        return find("FROM Client WHERE MONTH(birthDate) = ?1 ORDER BY DAY(birthDate)", month).list();
    }

    public static void reactivateClient(Long id) {
        String sql = "UPDATE Client SET status = 'Ativo' WHERE id = :id";
        getEntityManager().createNativeQuery(sql)
                .setParameter("id", id)
                .executeUpdate();
    }

    public static List<Client> clientListInactive() {
        return getEntityManager().createNamedQuery("Client.findInactive", Client.class).getResultList();
    }

    public static Client findByIdInactive(Long id) {
        return (Client) getEntityManager()
                .createNamedQuery("Client.findByIdAndInactive")
                .setParameter("id", id)
                .getSingleResult();
    }

    public static void hardDeleteById(Long id) {
        String sql = "DELETE FROM Client WHERE id = ?1";
        getEntityManager().createNativeQuery(sql)
                .setParameter(1, id)
                .executeUpdate();
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
