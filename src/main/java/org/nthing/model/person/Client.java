package org.nthing.model.person;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Client extends Person {


    public static List<Client> clients() {
        return Client.list("order by lower(name)");
    }

    public static List<Client> findByFullName(String fullName) {
        return find("lower(name.firstName) like lower(?1) or lower(name.lastName) like lower(?1) " +
                "ORDER BY name.firstName, name.lastName","%" + fullName + "%").list();
    }


}
