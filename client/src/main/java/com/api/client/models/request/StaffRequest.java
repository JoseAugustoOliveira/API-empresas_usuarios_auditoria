package com.api.client.models.request;

import com.api.client.entites.Audit;
import com.api.client.enums.Department;
import com.api.client.utils.Regex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
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
public class StaffRequest {

    @NotBlank(message = "completeName must be informed")
    private String completeName;

    @NotBlank(message = "spokesmanDocument must be informed")
    @Pattern(regexp = Regex.CPF, message = "contracteeDocumentNumber must be a valid CPF")
    private String spokesmanDocument;

    @Email
    private String email;

    @JsonIgnore
    private LocalDate initialDate;

    @JsonIgnore
    private boolean isActive;

    private Department department;
}
