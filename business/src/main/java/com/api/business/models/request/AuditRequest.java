package com.api.business.models.request;

import com.api.business.utils.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditRequest {

    @NotBlank(message = "details must be informed")
    private String details;

    @NotBlank(message = "spokesmanDocument must be informed")
    @Pattern(regexp = Regex.CPF, message = "contracteeDocumentNumber must be a valid CPF")
    private String spokesmanDocument;

    @JsonIgnore
    private LocalDate accessLastDate;

    @JsonIgnore
    private Integer qtdAccess;
}
