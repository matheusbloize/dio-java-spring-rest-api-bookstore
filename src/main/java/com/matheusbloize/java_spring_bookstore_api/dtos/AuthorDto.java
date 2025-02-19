package com.matheusbloize.java_spring_bookstore_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(@NotBlank String name) {

}
