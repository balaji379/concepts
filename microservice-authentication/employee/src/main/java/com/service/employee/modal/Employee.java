package com.service.employee.modal;

import lombok.Builder;
import lombok.Data;

@Builder
public record Employee(
        int id,
        String name,
        String department,
        Address address
) {
}
