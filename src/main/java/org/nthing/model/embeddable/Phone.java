package org.nthing.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.nthing.model.enums.PhoneType;

@Embeddable
public class Phone {

    @NotNull
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d+$", message = "O campo deve conter apenas dígitos numéricos")
    @Column(nullable = false, length = 11, unique = true)
    public String number;

    @Enumerated(EnumType.STRING)
    public PhoneType type;

    public Phone() {
    }

    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }
}
