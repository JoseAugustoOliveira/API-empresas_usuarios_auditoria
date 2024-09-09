package com.api.business.models.request;

import com.api.business.utils.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CompanyRequest {

    @NotBlank(message = "contracteeDocumentNumber must be informed")
    @Pattern(regexp = Regex.CNPJ, message = "contracteeDocumentNumber must be a valid CNPJ")
    private String contracteeDocumentNumber;

    @NotBlank(message = "contractNumber must be informed")
    @Pattern(regexp = Regex.ONLY_NUMBER, message = "contractNumber must be a number")
    private String contractNumber;

    @NotBlank(message = "companyName must be informed")
    private String companyName;

    @Email
    private String email;

    @JsonIgnore
    private LocalDate initialDate;

    @JsonIgnore
    private LocalDate lastUpdateDate;

    @JsonIgnore
    private String paymentStatus;

    @JsonIgnore
    private BigDecimal orderValue;

    @JsonIgnore
    private boolean isActive;
}
