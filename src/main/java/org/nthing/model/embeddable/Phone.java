package org.nthing.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Phone {

    @NotNull
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d+$", message = "O campo deve conter apenas dígitos numéricos")
    @Column(nullable = false, length = 11, unique = true)
    public String number;

    @Enumerated(EnumType.STRING)
    public PhoneType type;


}
