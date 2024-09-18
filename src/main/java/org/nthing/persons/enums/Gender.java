package org.nthing.persons.enums;

public enum Gender {
    MULHER_TRANS("Mulher Trans"),
    HOMEM_TRANS("Homem Trans"),
    MULHER_CIS("Mulher Cis"),
    HOMEM_CIS("Homem Cis"),
    NAO_BINARIO("Não Binário"),
    OUTRO("Outro"),
    PREFIRO_NAO_INFORMAR("Prefiro não informar");

    private String value;

    private Gender(String value) {
        this.value = value;
    }
}
