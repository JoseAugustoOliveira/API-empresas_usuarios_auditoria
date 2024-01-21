package com.api.business.controllers;

import com.api.business.services.GenerateFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate-file")
@Tag(name = "Generate File")
public class GenerateFileController {

    @Autowired
    private GenerateFileService generateFileService;

    @GetMapping("/generate-pdf/{contracteeDocumentNumber}")
    public ResponseEntity<ByteArrayResource> generatePdfByCnpj(@PathVariable String contracteeDocumentNumber) {
        return generateFileService.generatePdfByCnpj(contracteeDocumentNumber);
    }

}
