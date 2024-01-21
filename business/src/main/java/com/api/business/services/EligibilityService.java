package com.api.business.services;

import com.api.business.components.EligibilityComponent;
import com.api.business.models.request.EligibilityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EligibilityService {

    private final EligibilityComponent eligibilityComponent;

    public boolean verifyEligibility(EligibilityRequest eligibilityRequest) {
        log.info("Start eligibility verification for contracteeDocumentNumber: {} and contractNumber: {}", eligibilityRequest.getContracteeDocumentNumber(), eligibilityRequest.getContractNumber());
        return eligibilityComponent.verifyEligibility(eligibilityRequest.getContracteeDocumentNumber(), eligibilityRequest.getContractNumber());
    }
}