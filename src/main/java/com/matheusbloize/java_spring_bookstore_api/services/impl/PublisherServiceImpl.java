package com.matheusbloize.java_spring_bookstore_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheusbloize.java_spring_bookstore_api.models.Publisher;
import com.matheusbloize.java_spring_bookstore_api.repositories.PublisherRepository;
import com.matheusbloize.java_spring_bookstore_api.services.PublisherService;

import jakarta.transaction.Transactional;

@Service
public class PublisherServiceImpl implements PublisherService {
    private PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> listAll() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> findById(UUID id) {
        return publisherRepository.findById(id);
    }

    @Override
    @Transactional
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    @Transactional
    public void delete(Publisher publisher) {
        publisherRepository.delete(publisher);
    }

}
