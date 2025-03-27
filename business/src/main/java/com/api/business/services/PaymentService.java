package com.api.business.services;

import com.api.business.components.GetValidCompanyComponent;
import com.api.business.entites.CompanyData;
import com.api.business.entites.FinancingCompanyData;
import com.api.business.entites.PaymentData;
import com.api.business.enums.PaymentStatus;
import com.api.business.enums.PaymentStatusFinancing;
import com.api.business.exceptions.CompanyNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CompanyRepository companyRepository;

    private final AuditService auditService;
    private final GetValidCompanyComponent getValidCompanyComponent;

    public PaymentResponse authorizePayment(PaymentRequest request, String spokesmanDocument, String details) {
        var existingCompany = getValidCompanyComponent.getCompanyData(request.getContracteeDocumentNumber(), request.getContractNumber());

        PaymentData paymentData;
        if (existingCompany.getPayments() != null && !existingCompany.getPayments().isEmpty()) {
            paymentData = existingCompany.getPayments().get(existingCompany.getPayments().size() - 1); // Último pagamento
        } else {
            throw new CompanyNotFoundException("Company not found for this contract document number {}: ", request.getContracteeDocumentNumber());
        }

        FinancingCompanyData financingCompanyData = new FinancingCompanyData();
        LocalDate lastPaymentDate;

        Integer quantityInstallments = paymentData.getQuantityInstallments();
        if (quantityInstallments != null) {
            lastPaymentDate = LocalDate.now().plusMonths(quantityInstallments);
        } else {
            lastPaymentDate = LocalDate.now();
        }

        try {
            // Verifica se a lista de pagamentos está inicializada
            if (existingCompany.getPayments() == null) {
                existingCompany.setPayments(new ArrayList<>());
            }

            // Atualizando o status de pagamento da empresa
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);
            existingCompany.setLastUpdateDate(LocalDate.now());
            existingCompany.setLastPaymentDate(lastPaymentDate);

            // Atualizando dados de financiamento
            financingCompanyData.setAccount(request.getAccount());
            financingCompanyData.setAgency(request.getAgency());
            financingCompanyData.setCreatedDate(LocalDateTime.now());
            financingCompanyData.setFinancingName(request.getFinancingName());

            // Atualizando o status do pagamento no financing
            paymentData.setPaymentStatusFinancing(PaymentStatusFinancing.PAYMENT_IN_PROGRESS);
            paymentData.setMadeMonthPayment(true);
            paymentData.setMadeTotalPayment(false); // Ajustar conforme a lógica do pagamento

            // Relacionando o pagamento com a empresa
            existingCompany.getPayments().add(paymentData);
            paymentData.setCompanyData(existingCompany);

            log.info("Payment authorized successfully for company: {}", existingCompany.getContracteeDocumentNumber());

        } catch (Exception ex) {
            // Atualizando o status de pagamento para rejeitado em caso de erro
            existingCompany.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);
            existingCompany.setLastUpdateDate(LocalDate.now());

            // Reutilizando os dados de financiamento em caso de erro
            financingCompanyData.setAccount(request.getAccount());
            financingCompanyData.setAgency(request.getAgency());
            financingCompanyData.setCreatedDate(LocalDateTime.now());
            financingCompanyData.setFinancingName(request.getFinancingName());
            paymentData.setMadeMonthPayment(false);

            log.error("Error to update company, payment rejected!");
            throw new RuntimeException("Error to generate payment for company: " + existingCompany.getContracteeDocumentNumber(), ex);
        }

        // Registrando auditoria para o porta-voz
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

        // Registrando auditoria para o porta-voz
        auditService.registerAuditToStaff(spokesmanDocument, details);
        return processedCompanies;
    }

    private static ActivesCompaniesToValues getActivesCompaniesToValues(CompanyData company) {

        // Assumindo que estamos lidando com o primeiro pagamento
        PaymentData paymentData = company.getPayments().get(0);

        // Cálculo da taxa de juros e valores com base nos dados de pagamento
        BigDecimal interestRate = BigDecimal.valueOf(paymentData.getInterest()).divide(BigDecimal.valueOf(100));
        BigDecimal orderWithInterest = company.getOrderValue().add(company.getOrderValue().multiply(interestRate));
        BigDecimal orderMonthly = orderWithInterest.divide(BigDecimal.valueOf(paymentData.getQuantityInstallments()), RoundingMode.DOWN)
                .setScale(2, RoundingMode.DOWN);

        log.debug("Company name: {}", company.getCompanyName());

        return ActivesCompaniesToValues.builder()
                .companyName(company.getCompanyName())
                .paymentStatus(company.getPaymentStatus())
                .interest(paymentData.getInterest()) // Pegando juros do PaymentData
                .quantityInstallments(paymentData.getQuantityInstallments())
                .orderValue(company.getOrderValue())
                .orderWithInterest(orderWithInterest)
                .orderMonthly(orderMonthly)
                .build();
    }
}
