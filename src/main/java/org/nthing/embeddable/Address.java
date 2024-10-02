package org.nthing.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address {

    @NotNull
    @NotBlank(message = "O campo não pode esta em branco.")
    @Column(name = "residence_number", nullable = false)
    public String residenceNumber;

    @NotNull
    @NotBlank(message = "O campo não pode esta em branco.")
    @Size(min = 6, max = 100, message = "O campo deve conter entre 6 à 100 caracteres.")
    @Column(nullable = false, length = 100)
    public String street;

    @NotNull
    @NotBlank(message = "O campo não pode esta em branco.")
    @Size(max = 100, message = "O campo não pode ter mais que 100 caracteres.")
    @Column(nullable = false, length = 50)
    public String district;

    @NotNull
    @NotBlank(message = "O campo não pode esta em branco.")
    @Size(max = 30, message = "O campo não pode ter mais que 30 caracteres.")
    @Column(nullable = false, length = 30)
    public String city;

    @NotNull
    @NotBlank(message = "O campo não pode esta em branco.")
    @Size(max = 30, message = "O campo não pode ter mais que 30 caracteres.")
    @Column(nullable = false, length = 30)
    public String state;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}", message = "O campo deve conter apenas oito dígitos numéricos.")
    @Column(nullable = false, length = 8)
    public String zipcode;

    @Size(max = 35, message = "O campo não pode ter mais que 35 caracteres.")
    @Column(length = 35)
    public String complement;

    public Address() {
    }

    public Address(String residenceNumber, String street, String district, String city, String state, String zipcode, String complement) {
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.residenceNumber = residenceNumber;
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "Address{" +
                "residenceNumber='" + residenceNumber + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", complement='" + complement + '\'' +
                '}';
    }
}

