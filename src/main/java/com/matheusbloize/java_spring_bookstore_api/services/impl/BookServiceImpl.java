package com.matheusbloize.java_spring_bookstore_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheusbloize.java_spring_bookstore_api.models.Book;
import com.matheusbloize.java_spring_bookstore_api.repositories.BookRepository;
import com.matheusbloize.java_spring_bookstore_api.services.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
