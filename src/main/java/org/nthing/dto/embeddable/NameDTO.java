package org.nthing.dto.embeddable;

import org.nthing.model.embeddable.Name;

public record NameDTO(String firstName, String lastName) {
    public NameDTO(Name name) {
        this(name.firstName, name.lastName);
    }
}
