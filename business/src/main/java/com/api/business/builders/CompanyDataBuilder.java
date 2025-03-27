package com.api.business.builders;

import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.models.request.CompanyRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDataBuilder {

    public static CompanyData builder(CompanyRequest request) {
        return CompanyData.builder()
                .companyName(request.getCompanyName())
                .orderValue(BigDecimal.ZERO)
                .initialDate(LocalDate.now())
                .lastUpdateDate(LocalDate.now())
                .contracteeDocumentNumber(request.getContracteeDocumentNumber())
                .isActive(true)
                .email(request.getEmail())
                .madePayment(false)
                .contractNumber(request.getContractNumber())
                .paymentStatus(PaymentStatus.WAITING_PROCESSING)
                .build();
    }
}
