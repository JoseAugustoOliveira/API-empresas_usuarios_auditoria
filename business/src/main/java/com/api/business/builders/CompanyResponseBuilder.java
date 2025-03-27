package com.api.business.builders;

import com.api.business.entites.CompanyData;
import com.api.business.models.response.CompanyResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyResponseBuilder {

    public static CompanyResponse build(CompanyData company) {
        return CompanyResponse.builder()
                .contractNumber(company.getContractNumber())
                .companyName(company.getCompanyName())
                .email(company.getEmail())
                .contracteeDocumentNumber(company.getContracteeDocumentNumber())
                .paymentStatus(company.getPaymentStatus())
                .build();
    }
}