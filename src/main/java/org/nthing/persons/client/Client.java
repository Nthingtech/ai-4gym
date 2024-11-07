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
import org.nthing.enums.Status;
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
@NamedNativeQuery(
        name = "Client.findByCpfInactive",
        query = "SELECT * FROM Client WHERE cpf = :cpf AND status = 'Inativo'",
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

    @PrePersist
    public void setEnrollmentNumber() {
        this.enrollmentNumber = id;
    }

   /* public Client() {}*/

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

    public static Client findByCpfInactive(String cpf) {
        return (Client) getEntityManager()
                .createNamedQuery("Client.findByCpfInactive")
                .setParameter("cpf", cpf)
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

    public static ClientBuilder clientBuilder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        private Long id;
        private Name name;
        private LocalDate birthDate;
        private Integer age;
        private String cpf;
        private Gender gender;
        private Address address;
        private String phone;
        private String email;
        private String password;
        private Status status = Status.ACTIVE;
        private Long enrollmentNumber;
        private String instagram;

        public ClientBuilder id (Long id) {
            this.id = id;
            return this;
        }

        public ClientBuilder name(Name name) {
            this.name = name;
            return this;
        }

        public ClientBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public ClientBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public ClientBuilder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public ClientBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public ClientBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public ClientBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ClientBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public ClientBuilder enrollmentNumber(Long enrollmentNumber) {
            this.enrollmentNumber = enrollmentNumber;
            return this;
        }

        public ClientBuilder instagram(String instagram) {
            this.instagram = instagram;
            return this;
        }

        public Client build() {
            var client = new Client();
            client.id = this.id;
            client.name = this.name;
            client.birthDate = this.birthDate;
            client.age = this.age;
            client.cpf = this.cpf;
            client.gender = this.gender;
            client.address = this.address;
            client.phone = this.phone;
            client.email = this.email;
            client.password = this.password;
            client.status = this.status;
            client.enrollmentNumber = this.enrollmentNumber;
            client.instagram = this.instagram;
            return client;
        }
    }

}
