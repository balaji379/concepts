package com.tutorial.grpc_employee.modal;

import lombok.Builder;


@Builder
public record EmployeeModal(
        int empId,
        String name,
        AddressModal address

) {
}
