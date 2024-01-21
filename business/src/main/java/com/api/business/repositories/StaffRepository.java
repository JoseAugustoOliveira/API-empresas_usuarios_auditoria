package com.api.business.repositories;

import com.api.business.entites.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findBySpokesmanDocumentAndIsActive(String spokesmanDocument, boolean isActive);
    Optional<Staff> findBySpokesmanDocument(String spokesmanDocument);
}