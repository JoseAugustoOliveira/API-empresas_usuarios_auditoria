package com.api.business.services;

import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.exceptions.CompanyErrorException;
import com.api.business.exceptions.EmptyListCompaniesException;
import com.api.business.models.request.CompanyRequest;
import com.api.business.models.response.CompanyResponse;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AuditService auditService;

    public CompanyResponse saveCompany(CompanyRequest request, String spokesmanDocument, String details) {
        try {
            CompanyData companyData = CompanyData.builder()
                    .companyName(request.getCompanyName())
                    .totalValue(BigDecimal.ZERO)
                    .initialDate(LocalDate.now())
                    .contracteeDocumentNumber(request.getContracteeDocumentNumber())
                    .isActive(true)
                    .madePayment(false)
                    .contractNumber(request.getContractNumber())
                    .paymentStatus(PaymentStatus.WAITING_PROCESSING)
                    .build();

            auditService.registerAuditToStaff(spokesmanDocument, details);
            companyRepository.save(companyData);

            return CompanyResponse.builder()
                    .contractNumber(companyData.getContractNumber())
                    .companyName(companyData.getCompanyName())
                    .paymentStatus(companyData.getPaymentStatus())
                    .build();

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
}
