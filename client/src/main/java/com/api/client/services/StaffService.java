package com.api.client.services;

import com.api.client.entites.Staff;
import com.api.client.exceptions.EntityErrorException;
import com.api.client.exceptions.EntityNotFoundException;
import com.api.client.models.request.StaffRequest;
import com.api.client.models.response.StaffResponse;
import com.api.client.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StaffResponse registerStaff(StaffRequest request) {

        var staffExisting = staffRepository.findBySpokesmanDocument(request.getSpokesmanDocument());

        if (staffExisting.isPresent()) {
            log.error("Staff with SpokesmanDocument already exists: {}", request.getSpokesmanDocument());
            throw new EntityNotFoundException("Staff with SpokesmanDocument already exists: " + request.getSpokesmanDocument());
        }

        try {
            Staff newStaff = Staff.builder()
                    .registerDate(LocalDate.now())
                    .completeName(request.getCompleteName())
                    .isActive(true)
                    .email(request.getEmail())
                    .department(request.getDepartment())
                    .spokesmanDocument(passwordEncoder.encode(request.getSpokesmanDocument()))
                    .build();

            staffRepository.save(newStaff);

            log.info("Staff generated with successfully.");

            return StaffResponse.builder()
                    .lastRegisterDate(LocalDate.now())
                    .completeName(request.getCompleteName())
                    .department(request.getDepartment())
                    .build();

        } catch (EntityErrorException ex) {
            throw new EntityErrorException("Error to generate new staff", ex.getCause());
        }
    }
}
