package com.api.business.models.response;

import com.api.business.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private String contractNumber;
    private String companyName;
    private String email;
    private String contracteeDocumentNumber;
    private PaymentStatus paymentStatus;
}
