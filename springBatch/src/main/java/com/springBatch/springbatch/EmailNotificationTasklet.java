package com.springBatch.springbatch;

import com.springBatch.entites.CompanyData;
import com.springBatch.service.EmailNotificationService;
import com.springBatch.utils.TextEmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationTasklet implements Tasklet {

    private final EmailNotificationService emailNotificationService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        CompanyData companyData = new CompanyData();

        emailNotificationService.sendEmailNotification(companyData.getEmail(), TextEmailUtil.SUBJECT, TextEmailUtil.BODY, "");

        return RepeatStatus.FINISHED;
    }
}
