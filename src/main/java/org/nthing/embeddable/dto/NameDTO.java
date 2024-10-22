package org.nthing.embeddable.dto;

import io.quarkus.hibernate.orm.panache.common.NestedProjectedClass;

@NestedProjectedClass
public record NameDTO(String firstName, String lastName) {

}
