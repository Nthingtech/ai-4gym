package org.nthing.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Name {

    @NotNull
    @Column(nullable = false, length = 15)
    @Size(min = 3, max = 15)
    public String firstName;

    @NotNull
    @Column(nullable = false, length = 40)
    @Size(min = 3, max = 40)
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
