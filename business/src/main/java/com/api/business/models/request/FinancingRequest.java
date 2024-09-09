package com.api.business.models.request;

import com.api.business.utils.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @NotNull(message = "quantityInstallments must be informed")
    @Min(value = 0, message = "quantityInstallments must be at least 0")
    @Max(value = 60, message = "quantityInstallments must be no more than 60")
    private Integer quantityInstallments;

    @JsonIgnore
    private String paymentStatus;

    @JsonIgnore
    private LocalDate lastUpdateDate;

    @NotNull(message = "orderValue must be informed")
    private BigDecimal orderValue;
}
