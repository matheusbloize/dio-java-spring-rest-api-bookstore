package com.matheusbloize.java_spring_bookstore_api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheusbloize.java_spring_bookstore_api.models.Publisher;

public interface PublisherService {

    List<Publisher> listAll();

    Optional<Publisher> findById(UUID id);

    Publisher save(Publisher publisher);

    void delete(Publisher publisher);
}
