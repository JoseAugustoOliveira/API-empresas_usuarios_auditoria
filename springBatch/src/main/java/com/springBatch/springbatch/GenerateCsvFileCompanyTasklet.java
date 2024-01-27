package com.springBatch.springbatch;

import com.springBatch.enums.PaymentStatus;
import com.springBatch.service.GenerateCSVFileCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateCsvFileCompanyTasklet implements Tasklet {

    private final GenerateCSVFileCompanyService generateCSVFileCompanyService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("1 - Erro no processamento");
        generateCSVFileCompanyService.generateCsvForPaymentStatus(PaymentStatus.ERROR);

        log.info("2 - Enviado para parceiro");
        generateCSVFileCompanyService.generateCsvForPaymentStatus(PaymentStatus.PARTNER_SENT);

        log.info("3 - Pagamento rejeitado");
        generateCSVFileCompanyService.generateCsvForPaymentStatus(PaymentStatus.PAYMENT_REJECTED);

        log.info("4 - Pagamento autorizado");
        generateCSVFileCompanyService.generateCsvForPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);

        log.info("5 - Aguardando processamento");
        generateCSVFileCompanyService.generateCsvForPaymentStatus(PaymentStatus.WAITING_PROCESSING);

        return RepeatStatus.FINISHED;
    }
}
