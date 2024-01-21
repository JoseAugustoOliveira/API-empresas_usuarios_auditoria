package com.api.business.services;

import com.api.business.components.GenerateFileComponent;
import com.api.business.entites.CompanyData;
import com.api.business.exceptions.CompanyNotFoundException;
import com.api.business.repositories.CompanyRepository;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerateFileService {

    private final GenerateFileComponent generateFileComponent;
    private final CompanyRepository companyRepository;

    public ResponseEntity<ByteArrayResource> generatePdfByCnpj(String contracteeDocumentNumber) {
        List<CompanyData> companies = companyRepository.findByContracteeDocumentNumber(contracteeDocumentNumber);

        if (companies.isEmpty()) {
            throw new CompanyNotFoundException("No companies found with ContracteeDocumentNumber: " + contracteeDocumentNumber);
        }

        try {
            return generateFileComponent.generatePdfByCompanies(companies);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


}
