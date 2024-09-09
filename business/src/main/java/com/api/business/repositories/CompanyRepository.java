package com.api.business.repositories;

import com.api.business.entites.CompanyData;
import com.api.business.models.response.ActivesCompaniesToValues;
import com.api.business.models.response.CompanyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyData, Long> {

    Optional<CompanyData> findByContracteeDocumentNumberAndContractNumber(String cnpj, String contractNumber);
    List<CompanyData> findByContracteeDocumentNumber(String contracteeDocumentNumber);

    @Query("SELECT NEW com.api.business.models.response.CompanyResponse(" +
            "c.contractNumber, " +
            "c.companyName, " +
            "c.email, " +
            "c.contracteeDocumentNumber, " +
            "c.paymentStatus) " +
            "FROM CompanyData c")
    List<CompanyResponse> findAllCompanies();

    @Query("SELECT c FROM CompanyData c WHERE c.isActive = true " +
            "AND c.contracteeDocumentNumber = :contracteeDocumentNumber " +
            "AND c.contractNumber = :contractNumber")
    List<CompanyData> listCompaniesAndValues(String contracteeDocumentNumber, String contractNumber);
}
