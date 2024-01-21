package com.api.client.models.response;

import com.api.client.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private String completeName;
    private Department department;
    private LocalDate lastRegisterDate;
}
