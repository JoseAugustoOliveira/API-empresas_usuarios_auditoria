package com.api.business.entites;

import com.api.business.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPRESA")
public class CompanyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private Long id;

    @Column(name = "CNPJ")
    private String contracteeDocumentNumber;

    @Column(name = "NUMERO_CONTRATO")
    private String contractNumber;

    @Column(name = "NOME_EMPRESA")
    private String companyName;

    @Column(name = "STATUS_PAGAMENTO")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "VALOR_TOTAL")
    private BigDecimal totalValue;

    @Column(name = "DATA_CADASTRO")
    private LocalDate initialDate;

    @Column(name = "ATIVA")
    private Boolean isActive;

    @Column(name = "PAGAMENTO_EFETUADO")
    private Boolean madePayment;
}
