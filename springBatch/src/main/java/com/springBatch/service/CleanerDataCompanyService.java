package com.springBatch.service;

import com.springBatch.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CleanerDataCompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public void cleanOldData() {
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);

        companyRepository.deleteAllByInitialDateBefore(tenYearsAgo);

        log.info("Old data removed successfully, with more 10 years!");

    }
}
