package com.stream.output_service.model;

import lombok.Builder;

@Builder
public record Thumbnails(
        int id,String img,String name
) {
}
