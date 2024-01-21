package com.api.client.repositories;

import com.api.client.entites.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findBySpokesmanDocument(String spokesmanDocument);
}
