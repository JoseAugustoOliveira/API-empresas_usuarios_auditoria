package com.api.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatusFinancing {

    LATE_PAYMENT("Pagamento Atrasado"),
    COMPLETE_PAYMENT("Pagamento Completo"),
    PAYMENT_IN_PROGRESS("Pagamento em Andamento");

    private final String label;
}
