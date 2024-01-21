package com.api.business.models.response;

import com.api.business.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private BigDecimal value;
    private String companyName;
    private String bankName;
    private PaymentStatus paymentStatus;
}
