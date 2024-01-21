package com.api.business.models.request;

import com.api.business.utils.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancingRequest {

    @NotBlank(message = "contracteeDocumentNumber must be informed")
    @Pattern(regexp = Regex.CNPJ, message = "contracteeDocumentNumber must be a valid CNPJ")
    private String contracteeDocumentNumber;

    @NotBlank(message = "contractNumber must be informed")
    @Pattern(regexp = Regex.ONLY_NUMBER, message = "contractNumber must be a number")
    private String contractNumber;

    @JsonIgnore
    private String paymentStatus;

    private BigDecimal value;
}
