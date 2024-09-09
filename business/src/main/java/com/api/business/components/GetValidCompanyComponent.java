package com.api.business.components;

import com.api.business.entites.CompanyData;
import com.api.business.exceptions.CompanyNotFoundException;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetValidCompanyComponent {

    private final CompanyRepository companyRepository;

    @Value("${first.value}")
    private Double firstValue;

    @Value("${second.value}")
    private Double secondValue;

    @Value("${third.value}")
    private Double thirdValue;

    @Value("${fourth.value}")
    private Double fourthValue;

    @Value("${fifth.value}")
    private Double fifthValue;

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

    public double calculateInterest(int quantityInstallments) {
        if (quantityInstallments >= 1 && quantityInstallments <= 12) {
            return firstValue;
        } else if (quantityInstallments >= 13 && quantityInstallments <= 24) {
            return secondValue;
        } else if (quantityInstallments >= 25 && quantityInstallments <= 36) {
            return thirdValue;
        } else if (quantityInstallments >= 37 && quantityInstallments <= 48) {
            return fourthValue;
        } else if (quantityInstallments >= 49 && quantityInstallments <= 60) {
            return fifthValue;
        } else {
            throw new IllegalArgumentException("Invalid quantity of installments: " + quantityInstallments);
        }
    }

}
