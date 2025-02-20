package com.matheusbloize.java_spring_bookstore_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheusbloize.java_spring_bookstore_api.models.Author;
import com.matheusbloize.java_spring_bookstore_api.repositories.AuthorRepository;
import com.matheusbloize.java_spring_bookstore_api.services.AuthorService;

import jakarta.transaction.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAllById(Set<UUID> id) {
        return authorRepository.findAllById(id);
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void delete(Author author) {
        authorRepository.delete(author);
    }

}
