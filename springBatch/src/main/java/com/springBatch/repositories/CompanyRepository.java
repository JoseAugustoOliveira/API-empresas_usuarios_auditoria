package com.springBatch.repositories;

import com.springBatch.entites.CompanyData;
import com.springBatch.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyData, Long> {

    List<CompanyData> findByPaymentStatus(PaymentStatus paymentStatus);
    void deleteAllByInitialDateBefore(LocalDate date);
}
