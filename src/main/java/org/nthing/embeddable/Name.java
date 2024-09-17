package org.nthing.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Name {

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 15)
    @Size(min = 3, max = 15, message = "Deve ter entre 3 à 15 caracteres.")
    public String firstName;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 40)
    @Size(min = 3, max = 40, message = "Deve ter entre 3 à 40 caracteres.")
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
