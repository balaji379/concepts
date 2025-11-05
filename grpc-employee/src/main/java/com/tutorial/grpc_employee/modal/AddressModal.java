package com.tutorial.grpc_employee.modal;

import lombok.Builder;

@Builder
public record AddressModal(
        String address,
        int empId
) {

}