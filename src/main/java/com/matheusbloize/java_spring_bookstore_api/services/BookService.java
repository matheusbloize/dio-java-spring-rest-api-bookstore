package com.matheusbloize.java_spring_bookstore_api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheusbloize.java_spring_bookstore_api.models.Book;

public interface BookService {

    List<Book> listAll();

    Optional<Book> findById(UUID id);

    Book save(Book book);

    void delete(Book book);
}
