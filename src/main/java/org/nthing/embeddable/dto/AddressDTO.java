package org.nthing.embeddable.dto;

public record AddressDTO(String residenceNumber, String street, String district,
                         String city, String state, String zipcode, String complement) {

}
