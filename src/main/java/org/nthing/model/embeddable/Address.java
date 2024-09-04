package org.nthing.model.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    public String street;
    public String district;
    public String city;
    public String state;
    public String zipcode;

    public Address() {
    }

    public Address(String street, String district, String city, String state, String zipcode) {
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
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

