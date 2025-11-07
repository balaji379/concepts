package com.service.address_service.modal;

import lombok.Builder;

@Builder
public record Address(
        int empId,
        String address
) {
}
