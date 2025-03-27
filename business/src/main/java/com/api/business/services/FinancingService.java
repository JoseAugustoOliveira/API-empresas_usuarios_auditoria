package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.entites.FinancingCompanyData;
import com.api.business.entites.PaymentData;
import com.api.business.enums.PaymentStatus;
import com.api.business.enums.PaymentStatusFinancing;
import com.api.business.models.request.FinancingRequest;
import com.api.business.repositories.CompanyRepository;
import com.api.business.repositories.PaymentDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancingService {

    private final CompanyRepository companyRepository;
    private final PaymentDataRepository paymentDataRepository;

    private final AuditService auditService;
    private final GetValidCompanyComponent getValidCompanyComponent;

    private static final int VALUE_TO_DIVIDE = 100;

    public void createFinancing(FinancingRequest request, String spokesmanDocument, String details) {
        var existingCompany = getValidCompanyComponent.getCompanyData(
                request.getContracteeDocumentNumber(), request.getContractNumber());

        // TODO: adicionar lógica para que o CNPJ e o NU CONTRATO, esteja com o STEP 1 completo

        if (ObjectUtils.isEmpty(existingCompany.getCompanyData())) {
            log.warn("Initializing FinancingCompanyData for company: {}", existingCompany.getContracteeDocumentNumber());
            existingCompany.setCompanyData(new FinancingCompanyData());
        }

        PaymentData paymentData = getPaymentData(request, existingCompany);

        existingCompany.setPaymentStatus(PaymentStatus.PARTNER_SENT);
        existingCompany.setOrderValue(request.getOrderValue());
        existingCompany.setLastUpdateDate(LocalDate.now());
        existingCompany.setMadePayment(false);

        try {
            if (ObjectUtils.isEmpty(existingCompany.getPayments())) {
                existingCompany.setPayments(new ArrayList<>());
            }
            existingCompany.getPayments().add(paymentData);

            log.info("Company updated successfully with contract document number: {}", existingCompany.getContracteeDocumentNumber());

        } catch (Exception ex) {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);

            log.error("Error to update company, payment rejected!");
            throw new IllegalStateException("Error to save company data: " + existingCompany.getContracteeDocumentNumber(), ex);
        }

        auditService.registerAuditToStaff(spokesmanDocument, details);
        companyRepository.save(existingCompany);
    }

    private PaymentData getPaymentData(FinancingRequest request, CompanyData existingCompany) {
        PaymentData paymentData = new PaymentData();
        paymentData.setQuantityInstallments(request.getQuantityInstallments());
        var interest = getValidCompanyComponent.calculateInterest(request.getQuantityInstallments());
        paymentData.setInterest(interest);

        BigDecimal totalValueWithInterest = request.getOrderValue().multiply(BigDecimal.valueOf(interest / VALUE_TO_DIVIDE));
        paymentData.setTotalValueWithInterested(totalValueWithInterest);
        paymentData.setMonthValue(totalValueWithInterest.divide(BigDecimal.valueOf(request.getQuantityInstallments()), RoundingMode.HALF_UP));

        paymentData.setPaymentStatusFinancing(PaymentStatusFinancing.PAYMENT_IN_PROGRESS);
        paymentData.setCompanyData(existingCompany);

        paymentDataRepository.save(paymentData);
        log.info("Payments data updated successfully with name: {} ", paymentData.getCompanyData().getCompanyName());
        return paymentData;
    }
}
