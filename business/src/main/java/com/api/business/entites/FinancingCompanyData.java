package com.api.business.entites;

import com.api.business.enums.FinancingCompany;
import com.api.business.enums.PaymentStatusFinancing;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FinancingCompanyData {

    @Column(name = "STATUS_FINANCIAMENTO")
    @Enumerated(EnumType.STRING)
    private FinancingCompany financingName;

    @Column(name = "DT_CADASTRO_FINANCIADORA")
    private LocalDateTime createdDate;

    @Column(name = "CONTA")
    private String account;

    @Column(name = "AGENCIA")
    private String agency;

    @Column(name = "STATUS_PAGAMENTO_FINANCIADORA")
    @Enumerated(EnumType.STRING)
    private PaymentStatusFinancing paymentStatusFinancing;

    @Column(name = "VR_TOTAL_COM_JUROS")
    private BigDecimal totalValueWithInterested;
}
