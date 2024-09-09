package com.api.business.controllers;

import com.api.business.models.request.InfoCompanyRequest;
import com.api.business.models.response.EligibilityResponse;
import com.api.business.services.EligibilityService;
import com.api.business.services.ValidSpokesmanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "Eligibility")
@RequestMapping(path = "/eligibility")
public class EligibilityController {

    @Autowired
    private EligibilityService eligibilityService;

    @Autowired
    private ValidSpokesmanService validSpokesmanService;

    @GetMapping
    @Operation(summary = "Verify eligibility to CNPJ.")
    public EligibilityResponse verifyEligibility(@Valid InfoCompanyRequest request, @RequestParam String spokesmanDocument) {
        if (!validSpokesmanService.validStaffToRegister(spokesmanDocument)) {
            return EligibilityResponse.builder().eligibility(false).build();
        }

        boolean isEligible = eligibilityService.verifyEligibility(request);
        return EligibilityResponse.builder().eligibility(isEligible).build();
    }
}
