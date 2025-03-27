package com.api.business.components;

import com.api.business.components.adapter.InterestCalculator;
import com.api.business.components.adapter.InterestCalculatorAdapter;
import com.api.business.entites.CompanyData;
import com.api.business.exceptions.CompanyNotFoundException;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetValidCompanyComponent {

    private final CompanyRepository companyRepository;
    private InterestCalculatorAdapter interestCalculatorAdapter;

    public CompanyData getCompanyData(String contracteeDocumentNumber, String contractNumber) {
        var findCompany = companyRepository.findByContracteeDocumentNumberAndContractNumber(
                contracteeDocumentNumber, contractNumber);

        return findCompany.orElseThrow(() ->
                new CompanyNotFoundException("Company not found with contracteeDocumentNumber: "
                        + contracteeDocumentNumber));
    }

    public Double calculateInterest(int quantityInstallments) {
        InterestCalculator calculator = interestCalculatorAdapter.getCalculator();
        return calculator.calculate(quantityInstallments);
    }
}
