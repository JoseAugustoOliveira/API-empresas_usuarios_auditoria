package com.api.client.services;

import com.api.client.entites.Audit;
import com.api.client.exceptions.EntityNotFoundException;
import com.api.client.models.request.AuditRequest;
import com.api.client.repositories.AuditRepository;
import com.api.client.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;
    private final StaffRepository staffRepository;

    public ResponseEntity<String> registerAuditToStaff(AuditRequest request) {
        try {
            var staff = staffRepository.findBySpokesmanDocument(request.getSpokesmanDocument())
                    .orElseThrow(() -> new EntityNotFoundException("No staff found with SpokesmanDocument: "
                            + request.getSpokesmanDocument()));

            Audit audit = Audit.builder()
                    .accessLastDate(LocalDate.now())
                    .details(request.getDetails())
                    .qtdAccess(staff.getAudits().size() + 1)
                    .staff(staff)
                    .build();

            auditRepository.save(audit);

            log.info("Audit generated successfully.");
            return ResponseEntity.ok().build();

        } catch (EntityNotFoundException ex) {
            log.error("Staff with SpokesmanDocument not found: {}", request.getSpokesmanDocument());
            throw new EntityNotFoundException("No staff found with SpokesmanDocument: " + request.getSpokesmanDocument());

        } catch (Exception ex) {
            log.error("Error generating new audit", ex);
            throw new RuntimeException("Error generating new audit", ex);
        }
    }

}
