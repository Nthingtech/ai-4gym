package org.nthing.model.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

    public String firstName;
    public String lastName;

    public Name() {
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
