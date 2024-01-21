package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.models.request.FinancingRequest;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancingService {

    private final CompanyRepository companyRepository;
    private final GetValidCompanyComponent getValidCompanyComponent;
    private final AuditService auditService;

    public void createFinancing(FinancingRequest request, String spokesmanDocument, String details) {
        var findCompany = getValidCompanyComponent.getCompanyData(
                request.getContracteeDocumentNumber(), request.getContractNumber());
        CompanyData existingCompany = findCompany.get();

        try {
            existingCompany.setPaymentStatus(PaymentStatus.PARTNER_SENT);
            existingCompany.setTotalValue(request.getValue());
            existingCompany.setMadePayment(false);

            log.info("Company updated successfully");

        } catch (Exception ex) {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);
            existingCompany.setTotalValue(request.getValue());
            existingCompany.setMadePayment(false);

            log.error("Error to update company, payment rejected!");
            throw new RuntimeException("Error to generate financing");
        }
        auditService.registerAuditToStaff(spokesmanDocument, details);
        companyRepository.save(existingCompany);
    }
}
