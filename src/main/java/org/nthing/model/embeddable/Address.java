package org.nthing.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class Address {

    @NotNull
    @Column(nullable = false)
    public String residenceNumber;

    @NotNull
    @Column(nullable = false, length = 100)
    public String street;

    @NotNull
    @Column(nullable = false, length = 50)
    public String district;

    @NotNull
    @Column(nullable = false, length = 30)
    public String city;

    @NotNull
    @Column(nullable = false, length = 30)
    public String state;

    @NotNull
    @Column(nullable = false, length = 8)
    @Pattern(regexp = "\\d{8}", message = "O campo deve conter apenas oito dígitos numéricos.")
    public String zipcode;

    public Address() {
    }

    public Address(String residenceNumber, String street, String district, String city, String state, String zipcode) {
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.residenceNumber = residenceNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}

