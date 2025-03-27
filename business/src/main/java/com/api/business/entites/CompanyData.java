package com.api.business.entites;

import com.api.business.enums.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS_PAGAMENTO")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "DATA_CADASTRO")
    private LocalDate initialDate;

    @Column(name = "DATA_ULTIMA_ATUALIZACAO")
    private LocalDate lastUpdateDate;

    @Column(name = "DATA_ULTIMO_PAGAMENTO")
    private LocalDate lastPaymentDate;

    @Column(name = "ATIVA")
    private Boolean isActive;

    @Column(name = "PAGAMENTO_EFETUADO")
    private Boolean madePayment;

    @Column(name = "VALOR_DO_PEDIDO")
    private BigDecimal orderValue;

    @Embedded
    private FinancingCompanyData companyData;

    @OneToMany(mappedBy = "companyData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentData> payments;
}