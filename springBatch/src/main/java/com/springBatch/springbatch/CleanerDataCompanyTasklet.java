package com.springBatch.springbatch;

import com.springBatch.service.CleanerDataCompanyService;
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
public class CleanerDataCompanyTasklet implements Tasklet {

    private final CleanerDataCompanyService cleanerDataCompanyService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        cleanerDataCompanyService.cleanOldData();

        log.info("Batch finalized successfully!!!");

        return RepeatStatus.FINISHED;
    }
}
