package com.matheusbloize.java_spring_bookstore_api.dtos;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(@NotBlank String title, @NotNull UUID publisherId, @NotNull Set<UUID> authorsIds,
        @NotBlank String review) {

}
