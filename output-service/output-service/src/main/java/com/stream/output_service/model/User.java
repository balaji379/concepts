package com.stream.output_service.model;

import com.stream.movie_service.MovieRequest;
import lombok.Builder;

@Builder
public record User(
        int id,
        String name,
        String address,
        int age
) {
}
