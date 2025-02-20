package com.matheusbloize.java_spring_bookstore_api.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.matheusbloize.java_spring_bookstore_api.models.Author;

public interface AuthorService {

    List<Author> listAll();

    Optional<Author> findById(UUID id);

    List<Author> findAllById(Set<UUID> id);

    Author save(Author author);

    void delete(Author author);
}
