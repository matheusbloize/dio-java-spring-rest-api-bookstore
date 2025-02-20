package com.matheusbloize.java_spring_bookstore_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusbloize.java_spring_bookstore_api.dtos.PublisherDto;
import com.matheusbloize.java_spring_bookstore_api.models.Publisher;
import com.matheusbloize.java_spring_bookstore_api.services.PublisherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    private Object isPublisherFound(UUID id) {
        Optional<Publisher> publisherOpt = publisherService.findById(id);
        if (!publisherOpt.isPresent()) {
            return null;
        }
        return publisherOpt;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(isPublisherFound(id) != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(isPublisherFound(id) != null ? publisherService.findById(id) : "Publisher not found.");
    }

    @PostMapping
    public ResponseEntity<Publisher> save(@RequestBody @Valid PublisherDto publisherDto) {
        Publisher publisher = new Publisher();
        BeanUtils.copyProperties(publisherDto, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.save(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid PublisherDto publisherDto) {
        Optional<Publisher> publisherOpt = publisherService.findById(id);
        if (!publisherOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        Publisher publisher = new Publisher();
        BeanUtils.copyProperties(publisherDto, publisher);
        publisher.setId(publisherOpt.get().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.save(publisher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Publisher> publisherOpt = publisherService.findById(id);
        if (!publisherOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        publisherService.delete(publisherOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Publisher deleted successfully.");
    }
}
