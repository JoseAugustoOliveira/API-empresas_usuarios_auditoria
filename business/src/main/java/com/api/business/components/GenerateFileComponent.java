package com.api.business.components;

import com.api.business.entites.CompanyData;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.api.business.utils.FormatUtils.formatActiveStatus;
import static com.api.business.utils.FormatUtils.formatCnpj;
import static com.api.business.utils.FormatUtils.formatCurrency;
import static com.api.business.utils.FormatUtils.formatPaymentDoneStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateFileComponent {

    private static final String NAME_FILE = "Dados_Empresariais.pdf";

    public ResponseEntity<ByteArrayResource> generatePdfByCompanies(List<CompanyData> companies) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = getByteArrayOutputStream(companies);

        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", NAME_FILE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(List<CompanyData> companies) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        document.add(new Paragraph("Informações das Empresas a Contratar o Financiamento"));
        document.add(Chunk.NEWLINE);
        int i = 0;

        for (CompanyData company : companies) {
            i++;
            document.add(new Paragraph("Contrato #" + i));
            document.add(new Paragraph("CNPJ: " + formatCnpj(company.getContracteeDocumentNumber())));
            document.add(new Paragraph("Numero do Contrato: " + company.getContractNumber()));
            document.add(new Paragraph("Nome da Empresa: " + company.getCompanyName()));
            document.add(new Paragraph("Status do Pagamento: " + company.getPaymentStatus().getLabel()));
            document.add(new Paragraph("Valor total: " + formatCurrency(company.getOrderValue())));
            document.add(new Paragraph("Data do Registro: " + company.getInitialDate()));
            document.add(new Paragraph("Empresa ativa: " + formatActiveStatus(company.getIsActive())));
            document.add(new Paragraph("Pagamento feito: " + formatPaymentDoneStatus(company.getIsActive())));
            document.add(Chunk.NEWLINE);
        }

        log.info("Total contracts listing: {}, with contractDocumentNumber: {}", i, companies.get(0).getContracteeDocumentNumber());

        document.close();
        return byteArrayOutputStream;
    }
}
