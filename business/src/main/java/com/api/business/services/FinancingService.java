package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.models.request.FinancingRequest;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

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
            existingCompany.setOrderValue(request.getOrderValue());
            existingCompany.setLastUpdateDate(LocalDate.now());
            existingCompany.setQuantityInstallments(request.getQuantityInstallments());
            existingCompany.setInterest(getValidCompanyComponent.calculateInterest(request.getQuantityInstallments()));
            existingCompany.getCompanyData().setTotalValueWithInterested(request.getOrderValue().multiply(
                    BigDecimal.valueOf(getValidCompanyComponent.calculateInterest(request.getQuantityInstallments()) / 10)));
            existingCompany.setMadePayment(false);

            log.info("Company updated successfully with contract document number: {}", request.getContracteeDocumentNumber());

        } catch (Exception ex) {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);
            existingCompany.setOrderValue(request.getOrderValue());
            existingCompany.setLastUpdateDate(LocalDate.now());
            existingCompany.setQuantityInstallments(0);
            existingCompany.setInterest(0d);
            existingCompany.setMadePayment(false);

            log.error("Error to update company, payment rejected!");
            throw new RuntimeException("Error to generate financing");
        }
        auditService.registerAuditToStaff(spokesmanDocument, details);
        companyRepository.save(existingCompany);
    }
}
