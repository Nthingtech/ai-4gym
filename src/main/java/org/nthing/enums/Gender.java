package org.nthing.enums;

public enum Gender {
    MULHER_TRANS("Mulher Trans"),
    HOMEM_TRANS("Homem Trans"),
    MULHER_CIS("Mulher Cis"),
    HOMEM_CIS("Homem Cis"),
    NAO_BINARIO("Não Binário"),
    OUTRO("Outro"),
    PREFIRO_NAO_INFORMAR("Prefiro não informar");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
