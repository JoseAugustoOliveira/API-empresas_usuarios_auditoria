package com.api.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    ERROR("Erro no processamento"),
    PARTNER_SENT("Enviado para parceiro"), // Step 2
    PAYMENT_REJECTED("Pagamento rejeitado"),
    PAYMENT_AUTHORIZED("Pagamento autorizado"),
    WAITING_PROCESSING("Aguardando processamento");  // Step 1

    private final String label;
}
