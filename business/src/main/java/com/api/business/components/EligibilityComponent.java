package com.api.business.components;

import com.api.business.entites.CompanyData;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EligibilityComponent {

    private final CompanyRepository companyRepository;

    public boolean verifyEligibility(String contracteeDocumentNumber, String contractNumber) {
        log.debug("Start eligibility verification for contracteeDocumentNumber: {} and contractNumber: {}",
                contracteeDocumentNumber, contractNumber);
        return isValidCnpjAndContract(contracteeDocumentNumber, contractNumber);
    }

    private boolean isValidCnpjAndContract(String contracteeDocumentNumber, String contractNumber) {
        log.debug("Verifying contractee document number {} and contract number {} in the database.",
                contracteeDocumentNumber, contractNumber);

        Optional<CompanyData> company = companyRepository.findByContracteeDocumentNumberAndContractNumber(
                contracteeDocumentNumber, contractNumber);

        if (company.isPresent()) {
            log.debug("Contractee document number {} and contract number {} are registered in the database.",
                    contracteeDocumentNumber, contractNumber);

            return true;
        } else {
            log.error("Contractee document number {} and contract number {} are not registered in the database.",
                    contracteeDocumentNumber, contractNumber);
            return false;
        }
    }

}
