package com.springBatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final GenerateCsvFileCompanyTasklet generateCsvFileCompanyTasklet;
    private final EmailNotificationTasklet emailNotificationTasklet;
    private final CleanerDataCompanyTasklet cleanerDataCompanyTasklet;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("generateFile", jobRepository)
                .tasklet(generateCsvFileCompanyTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("emailNotification", jobRepository)
                .tasklet(emailNotificationTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step step3() {
        return new StepBuilder("deleteOldData", jobRepository)
                .tasklet(cleanerDataCompanyTasklet, transactionManager)
                .build();
    }
}
