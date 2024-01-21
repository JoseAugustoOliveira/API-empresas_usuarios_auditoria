package com.api.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Department {

    IT("Tecnologia da Informação"),
    HR("Recursos Humanos"),
    SALES("Vendas"),
    MARKETING("Marketing"),
    FINANCE("Finanças"),
    OPERATIONS("Operações"),
    CUSTOMER_SERVICE("Atendimento ao Cliente");

    private final String label;
}
