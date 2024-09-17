package org.nthing.dto.embeddable;

import org.nthing.model.embeddable.Phone;
import org.nthing.model.enums.PhoneType;

public record PhoneDTO(String phone, PhoneType type) {

    public PhoneDTO(Phone phone) {
        this(phone.number, phone.type);
    }
}
