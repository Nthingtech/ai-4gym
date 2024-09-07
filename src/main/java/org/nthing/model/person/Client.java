package org.nthing.model.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {@Index(name = "idx_enrollmentNumber", columnList = "enrollmentNumber")})
public class Client extends Person {

    @NotNull
    @Column(nullable = false, unique = true)
    public Long enrollmentNumber = id;

    @Column
    public String instagram;

    public static List<Client> clients() {
        return Client.list("order by lower(name)");
    }

    public static List<Client> findByFullName(String fullName) {
        return find("lower(name.firstName) like lower(?1) or lower(name.lastName) like lower(?1) " +
                "ORDER BY name.firstName, name.lastName","%" + fullName + "%").list();
    }


}
