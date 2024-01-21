package com.api.client.controller;

import com.api.client.models.request.AuditRequest;
import com.api.client.models.request.StaffRequest;
import com.api.client.models.response.StaffResponse;
import com.api.client.services.AuditService;
import com.api.client.services.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Client")
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/register-staff")
    @Operation(summary = "Register new staff.")
    public StaffResponse registerStaff(@Valid @RequestBody StaffRequest request) {
        return staffService.registerStaff(request);
    }

    @PostMapping("/register-audit")
    @Operation(summary = "Register audit when staff do system")
    public ResponseEntity<String> registerAudit(@Valid @RequestBody AuditRequest request) {
        return auditService.registerAuditToStaff(request);
    }
}
