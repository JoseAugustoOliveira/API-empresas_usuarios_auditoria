package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.models.request.PaymentRequest;
import com.api.business.models.response.PaymentResponse;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CompanyRepository companyRepository;
    private final GetValidCompanyComponent getValidCompanyComponent;
    private final AuditService auditService;

    public PaymentResponse authorizePayment(PaymentRequest request, String spokesmanDocument, String details) {
        var findCompany = getValidCompanyComponent.getCompanyData(
                request.getContracteeDocumentNumber(), request.getContractNumber());
        CompanyData existingCompany = findCompany.get();

        try {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);
            existingCompany.setMadePayment(true);

            log.info("Payment authorized successfully");

        } catch (Exception ex) {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);
            existingCompany.setMadePayment(false);

            log.error("Error to update company, payment rejected!");
            throw new RuntimeException("Error to generate payment");
        }
        auditService.registerAuditToStaff(spokesmanDocument, details);
        companyRepository.save(existingCompany);

        return PaymentResponse.builder()
                .paymentStatus(existingCompany.getPaymentStatus())
                .bankName(request.getBankName())
                .companyName(existingCompany.getCompanyName())
                .value(existingCompany.getTotalValue())
                .build();
    }
}
