package com.api.business.services;

import com.api.business.builders.CompanyDataBuilder;
import com.api.business.builders.CompanyResponseBuilder;
import com.api.business.entites.CompanyData;
import com.api.business.exceptions.CompanyErrorException;
import com.api.business.exceptions.EmptyListCompaniesException;
import com.api.business.models.request.CompanyRequest;
import com.api.business.models.response.CompanyResponse;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AuditService auditService;

    public CompanyResponse saveCompany(CompanyRequest request, String spokesmanDocument, String details) {
        var existingCompanyAndContract = getByContracteeDocumentNumberAndContractNumber(request);

        try {
            if (existingCompanyAndContract.isPresent()) {
                throw new CompanyErrorException("There is a company with contract document number " + request.getContracteeDocumentNumber() +
                        " and contract " + request.getContractNumber());
            }

            var companyData = CompanyDataBuilder.builder(request);

            auditService.registerAuditToStaff(spokesmanDocument, details);
            companyRepository.save(companyData);
            log.info("Company with contract document number: {} registered successfully", request.getContracteeDocumentNumber());

            return CompanyResponseBuilder.build(companyData);

        } catch (CompanyErrorException ex) {
            log.error("Error to register company name: {}", request.getCompanyName());
            throw new CompanyErrorException("Error to register company", ex);
        }
    }

    public List<CompanyResponse> listCompanies(String spokesmanDocument, String details) {
        var listCompanies = companyRepository.findAllCompanies();
        if (listCompanies.isEmpty()) {
            throw new EmptyListCompaniesException("Error to list company is empty");
        }

        auditService.registerAuditToStaff(spokesmanDocument, details);
        return listCompanies;
    }

    private Optional<CompanyData> getByContracteeDocumentNumberAndContractNumber(CompanyRequest request) {
        return companyRepository.findByContracteeDocumentNumberAndContractNumber(
                request.getContracteeDocumentNumber(),
                request.getContractNumber()
        );
    }
}