package com.api.business.entites;

import com.api.business.enums.PaymentStatusFinancing;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAGAMENTOS")
public class PaymentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGAMENTOS")
    private Long id;

    @Column(name = "PG_MES_EFETUADO")
    private Boolean madeMonthPayment;

    @Column(name = "QTD_PARCELAS")
    private Integer quantityInstallments;

    @Column(name = "JUROS")
    private Double interest;

    @Column(name = "VR_TOTAL_COM_JUROS")
    private BigDecimal totalValueWithInterested;

    @Column(name = "VR_MENSAL")
    private BigDecimal monthValue;

    @Column(name = "PG_TOTAL_EFETUADO")
    private Boolean madeTotalPayment;

    @Column(name = "STATUS_PAGAMENTO_FINANCIADORA")
    @Enumerated(EnumType.STRING)
    private PaymentStatusFinancing paymentStatusFinancing;

    @ManyToOne
    @JoinColumn(name = "EMPRESA_ID")
    private CompanyData companyData;
}
