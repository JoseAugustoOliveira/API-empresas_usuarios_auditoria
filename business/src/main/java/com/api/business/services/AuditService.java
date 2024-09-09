package com.api.business.services;

import com.api.business.repositories.AuditRepository;
import com.api.business.repositories.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.api.business.entites.Audit;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;
    private final StaffRepository staffRepository;

    public void registerAuditToStaff(String spokesmanDocument, String details) {
        try {
            var staff = staffRepository.findAll().stream()
                    .filter(s -> BCrypt.checkpw(spokesmanDocument, s.getSpokesmanDocument()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("No staff found with SpokesmanDocument: "
                            + spokesmanDocument));

            Audit audit = Audit.builder()
                    .accessLastDate(LocalDate.now())
                    .details(details)
                    .qtdAccess(staff.getAudits().size() + 1)
                    .staff(staff)
                    .build();

            auditRepository.save(audit);

            log.info("Audit generated successfully.");

        } catch (EntityNotFoundException ex) {
            log.error("Staff with SpokesmanDocument not found: {}", spokesmanDocument);
            throw new EntityNotFoundException("No staff found with SpokesmanDocument: " + spokesmanDocument);

        } catch (Exception ex) {
            log.error("Error generating new audit", ex);
            throw new RuntimeException("Error generating new audit", ex);
        }
    }

}
