package org.nthing.embeddable.dto;

import io.quarkus.hibernate.orm.panache.common.NestedProjectedClass;

@NestedProjectedClass
public record AddressDTO(String residenceNumber, String street, String district,
                         String city, String state, String zipcode) {

}
