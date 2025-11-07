package com.service.employee.modal;

import lombok.Builder;

@Builder
public record Address(
        int empId,
        String address
) {
}
