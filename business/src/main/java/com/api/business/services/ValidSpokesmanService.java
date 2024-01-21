package com.api.business.services;

import com.api.business.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidSpokesmanService {

    private final StaffRepository staffRepository;
    private final AuditService auditService;

    public boolean validStaffToRegister(String spokesmanDocument) {
        var validStaff = staffRepository.findBySpokesmanDocumentAndIsActive(spokesmanDocument, true);

        if (validStaff.isEmpty()) {
            log.error("SpokesmanDocument or staff active is invalid.");
            return false;
        }

        log.info("SpokesmanDocument to staff valid and active.");
        return true;
    }
}
