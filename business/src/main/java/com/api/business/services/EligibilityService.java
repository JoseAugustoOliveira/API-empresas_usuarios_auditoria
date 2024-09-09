package com.api.business.services;

import com.api.business.components.EligibilityComponent;
import com.api.business.models.request.InfoCompanyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EligibilityService {

    private final EligibilityComponent eligibilityComponent;

    public boolean verifyEligibility(InfoCompanyRequest infoCompanyRequest) {
        log.info("Start eligibility verification for contracteeDocumentNumber: {} and contractNumber: {}", infoCompanyRequest.getContracteeDocumentNumber(), infoCompanyRequest.getContractNumber());
        return eligibilityComponent.verifyEligibility(infoCompanyRequest.getContracteeDocumentNumber(), infoCompanyRequest.getContractNumber());
    }
}