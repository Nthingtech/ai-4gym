package org.nthing.dto.embeddable;

import org.nthing.model.embeddable.Address;

public record AddressDTO(String residenceNumber, String street, String district,
                         String city, String state, String zipcode) {

    public AddressDTO(Address address) {
        this(address.residenceNumber, address.street, address.district,
                address.city, address.state, address.zipcode);
    }
}
