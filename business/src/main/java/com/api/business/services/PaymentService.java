package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.enums.PaymentStatus;
import com.api.business.enums.PaymentStatusFinancing;
import com.api.business.exceptions.EmptyListCompaniesException;
import com.api.business.models.request.PaymentRequest;
import com.api.business.models.response.ActivesCompaniesToValues;
import com.api.business.models.response.PaymentResponse;
import com.api.business.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        LocalDate lastPaymentDate = LocalDate.now().plusMonths(existingCompany.getQuantityInstallments());

        // TODO: Validar no banco a data do ultimo pagamento

        try {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);
            existingCompany.setLastUpdateDate(LocalDate.now());
            existingCompany.setLastPaymentDate(lastPaymentDate);
            existingCompany.getCompanyData().setAccount(request.getAccount());
            existingCompany.getCompanyData().setAgency(request.getAgency());
            existingCompany.getCompanyData().setPaymentStatusFinancing(PaymentStatusFinancing.PAYMENT_IN_PROGRESS);
            existingCompany.getCompanyData().setCreatedDate(LocalDateTime.now());
            existingCompany.getCompanyData().setFinancingName(request.getFinancingName());
            existingCompany.setMadePayment(true);

            log.info("Payment authorized successfully");

        } catch (Exception ex) {
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);
            existingCompany.setLastUpdateDate(LocalDate.now());
            existingCompany.getCompanyData().setAccount(request.getAccount());
            existingCompany.getCompanyData().setAgency(request.getAgency());
            existingCompany.getCompanyData().setCreatedDate(LocalDateTime.now());
            existingCompany.getCompanyData().setFinancingName(request.getFinancingName());
            existingCompany.setMadePayment(false);

            log.error("Error to update company, payment rejected!");
            throw new RuntimeException("Error to generate payment");
        }
        auditService.registerAuditToStaff(spokesmanDocument, details);
        companyRepository.save(existingCompany);

        return PaymentResponse.builder()
                .paymentStatus(existingCompany.getPaymentStatus())
                .financingCompany(request.getFinancingName())
                .lastUpdateDate(LocalDate.now())
                .companyName(existingCompany.getCompanyName())
                .value(existingCompany.getOrderValue())
                .build();
    }

    public List<ActivesCompaniesToValues> listActivesCompaniesAndValues(String contracteeDocumentNumber,
                                                                        String contractNumber,
                                                                        String spokesmanDocument,
                                                                        String details) {
        var listActivesCompanies = companyRepository.listCompaniesAndValues(
                contracteeDocumentNumber, contractNumber);

        if (listActivesCompanies.isEmpty()) {
            throw new EmptyListCompaniesException("Error: The company list is empty");
        }

        List<ActivesCompaniesToValues> processedCompanies = listActivesCompanies
            .stream().map(PaymentService::getActivesCompaniesToValues)
            .collect(Collectors.toList());

        auditService.registerAuditToStaff(spokesmanDocument, details);
        return processedCompanies;
    }

    private static ActivesCompaniesToValues getActivesCompaniesToValues(CompanyData company) {

        BigDecimal interestRate = BigDecimal.valueOf(company.getInterest()).divide(BigDecimal.valueOf(100));
        BigDecimal orderWithInterest = company.getOrderValue().add(company.getOrderValue().multiply(interestRate));
        BigDecimal orderMonthly = orderWithInterest.divide(BigDecimal.valueOf(company.getQuantityInstallments()), RoundingMode.DOWN)
                .setScale(2, RoundingMode.DOWN);

        log.debug("Company name: {}", company.getCompanyName());

        return ActivesCompaniesToValues.builder()
                .companyName(company.getCompanyName())
                .paymentStatus(company.getPaymentStatus())
                .interest(company.getInterest())
                .quantityInstallments(company.getQuantityInstallments())
                .orderValue(company.getOrderValue())
                .orderWithInterest(orderWithInterest)
                .orderMonthly(orderMonthly)
                .build();
    }
}
