package com.api.business.controllers;

import com.api.business.models.request.CompanyRequest;
import com.api.business.models.request.FinancingRequest;
import com.api.business.models.request.PaymentRequest;
import com.api.business.models.response.CompanyResponse;
import com.api.business.models.response.PaymentResponse;
import com.api.business.services.CompanyService;
import com.api.business.services.FinancingService;
import com.api.business.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business")
@Tag(name = "Companies")
public class BusinessController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private FinancingService financingService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/saveCompany")
    @Operation(summary = "Register new company. Step 1")
    public CompanyResponse saveCompany(@Valid @RequestBody CompanyRequest request, @RequestParam String spokesmanDocument, String details) {
        return companyService.saveCompany(request, spokesmanDocument, details);
    }

    @PostMapping("/financing")
    @Operation(summary = "Register financing to company. Step 2")
    public void createFinancing(@Valid @RequestBody FinancingRequest request, @RequestParam String spokesmanDocument, String details) {
        financingService.createFinancing(request, spokesmanDocument, details);
    }

    @PostMapping("/payment")
    @Operation(summary = "Register payment to company. Step 3")
    public PaymentResponse authorizePayment(@Valid @RequestBody PaymentRequest request, @RequestParam String spokesmanDocument, String details) {
        return paymentService.authorizePayment(request, spokesmanDocument, details);
    }

    @GetMapping("/listCompanies")
    @Operation(summary = "List companies")
    public List<CompanyResponse> listCompanies(@RequestParam String spokesmanDocument, String details) {
        return companyService.listCompanies(spokesmanDocument, details);
    }

}
