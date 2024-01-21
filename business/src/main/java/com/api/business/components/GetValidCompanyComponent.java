package com.api.business.components;

import com.api.business.entites.CompanyData;
import com.api.business.exceptions.CompanyNotFoundException;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetValidCompanyComponent {

    private final CompanyRepository companyRepository;

    public Optional<CompanyData> getCompanyData(String contracteeDocumentNumber, String contractNumber) {
        var findCompany = companyRepository.findByContracteeDocumentNumberAndContractNumber(
                contracteeDocumentNumber, contractNumber);

        if (findCompany.isEmpty()) {
            log.error("Error to find company contractDocumentNumber: {} and contract number: {}",
                    contracteeDocumentNumber, contractNumber);
            throw new CompanyNotFoundException("Company not found with contracteeDocumentNumber: "
                    + contracteeDocumentNumber);
        }
        return findCompany;
    }
}
