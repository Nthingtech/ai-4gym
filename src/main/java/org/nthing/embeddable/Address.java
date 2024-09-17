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
    @NotBlank
    @Column(name = "residence_number", nullable = false)
    public String residenceNumber;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 100)
    public String street;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    public String district;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 30)
    public String city;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 30)
    public String state;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}", message = "O campo deve conter apenas oito dígitos numéricos.")
    @Column(nullable = false, length = 8)
    public Integer zipcode;

    @Size(max = 35)
    @Column(length = 35)
    public String complement;

    public Address() {
    }

    public Address(String residenceNumber, String street, String district, String city, String state, Integer zipcode, String complement) {
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

