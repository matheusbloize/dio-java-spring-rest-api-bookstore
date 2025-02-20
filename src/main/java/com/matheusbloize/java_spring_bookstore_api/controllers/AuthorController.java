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

import com.matheusbloize.java_spring_bookstore_api.dtos.AuthorDto;
import com.matheusbloize.java_spring_bookstore_api.models.Author;
import com.matheusbloize.java_spring_bookstore_api.services.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private Object isAuthorFound(UUID id) {
        Optional<Author> authorOpt = authorService.findById(id);
        if (!authorOpt.isPresent()) {
            return null;
        }
        return authorOpt;
    }

    @GetMapping
    public ResponseEntity<List<Author>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(isAuthorFound(id) != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(isAuthorFound(id) != null ? authorService.findById(id) : "Author not found.");
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody @Valid AuthorDto authorDto) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid AuthorDto authorDto) {
        Optional<Author> authorOpt = authorService.findById(id);
        if (!authorOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }
        Author author = new Author();
        BeanUtils.copyProperties(authorDto, author);
        author.setId(authorOpt.get().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Author> authorOpt = authorService.findById(id);
        if (!authorOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }
        authorService.delete(authorOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully.");
    }
}
