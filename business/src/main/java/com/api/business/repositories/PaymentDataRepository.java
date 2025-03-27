package com.api.business.repositories;

import com.api.business.entites.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {
}
