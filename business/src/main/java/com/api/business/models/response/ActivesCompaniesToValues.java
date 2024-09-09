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
public class ActivesCompaniesToValues {

    private String companyName;
    private PaymentStatus paymentStatus;
    private Double interest;
    private Integer quantityInstallments;
    private BigDecimal orderValue;
    private BigDecimal orderWithInterest;
    private BigDecimal orderMonthly;
}
