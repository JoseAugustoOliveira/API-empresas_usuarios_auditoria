package com.springBatch.service;

import com.opencsv.CSVWriter;
import com.springBatch.entites.CompanyData;
import com.springBatch.enums.PaymentStatus;
import com.springBatch.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateCSVFileCompanyService {

    private final CompanyRepository companyRepository;

    // TODO: Gerar o CSV com os dados da empresa para cada um dos status do enum PaymentStatus

    public void generateCsvForPaymentStatus(PaymentStatus paymentStatus) {
        List<CompanyData> companies = companyRepository.findByPaymentStatus(paymentStatus);

        if (!companies.isEmpty()) {
            String fileName = "CSV_" + paymentStatus.name() + ".csv";

            try (Writer writer = new FileWriter(fileName);
                 CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                         CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

                String[] header = getHeader();
                generateFiles(csvWriter, header, companies);

                log.info("CSV File generated successfully with status: " + paymentStatus);

            } catch (IOException e) {
                log.error("Error to generate file CSV with status: " + paymentStatus);
                e.printStackTrace();
            }
        } else {
            log.info("Nothing data finded with status: " + paymentStatus);
        }
    }

    private static void generateFiles(CSVWriter csvWriter, String[] header, List<CompanyData> companies) {
        csvWriter.writeNext(header);

        for (CompanyData company : companies) {
            String[] data = {
                    String.valueOf(company.getId()),
                    company.getContracteeDocumentNumber(),
                    company.getContractNumber(),
                    company.getCompanyName(),
                    company.getEmail(),
                    company.getPaymentStatus().name(),
                    String.valueOf(company.getTotalValue()),
                    String.valueOf(company.getInitialDate()),
                    String.valueOf(company.getLastUpdateDate()),
                    String.valueOf(company.getIsActive()),
                    String.valueOf(company.getMadePayment())
            };
            csvWriter.writeNext(data);
        }
    }

    private static String[] getHeader() {
        return new String[]{"ID_EMPRESA", "CNPJ", "NUMERO_CONTRATO", "NOME_EMPRESA", "EMAIL", "STATUS_PAGAMENTO",
                "VALOR_TOTAL", "DATA_CADASTRO", "DATA_ULTIMA_ATUALIZACAO", "ATIVA", "PAGAMENTO_EFETUADO"};
    }
}
