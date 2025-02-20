package com.matheusbloize.java_spring_bookstore_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.matheusbloize.java_spring_bookstore_api.dtos.BookDto;
import com.matheusbloize.java_spring_bookstore_api.models.Author;
import com.matheusbloize.java_spring_bookstore_api.models.Book;
import com.matheusbloize.java_spring_bookstore_api.models.Publisher;
import com.matheusbloize.java_spring_bookstore_api.models.Review;
import com.matheusbloize.java_spring_bookstore_api.services.AuthorService;
import com.matheusbloize.java_spring_bookstore_api.services.BookService;
import com.matheusbloize.java_spring_bookstore_api.services.PublisherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private AuthorService authorService;
    private PublisherService publisherService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    private Object isBookFound(UUID id) {
        Optional<Book> bookOpt = bookService.findById(id);
        if (!bookOpt.isPresent()) {
            return null;
        }
        return bookOpt;
    }

    private ResponseEntity<Object> setPublishers(Book book, BookDto bookDto) {
        Optional<Publisher> publisher = publisherService.findById(bookDto.publisherId());
        if (!publisher.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Publisher.");
        }
        book.setPublisher(publisher.get());
        publisher.get().addBook(book);
        return null;
    }

    private ResponseEntity<Object> setAuthors(Book book, BookDto bookDto) {
        Set<Author> authors = authorService.findAllById(bookDto.authorsIds()).stream().map(author -> author).collect(Collectors.toSet());
        if (authors.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Author(s).");
        }
        book.setAuthors(authors);
        authors.forEach(author -> author.addBook(book));
        return null;
    }

    private Review setReview(Book book, BookDto bookDto, Optional<Book> bookOpt, boolean setId) {
        Review review = new Review();
        if (setId) {
            review.setId(bookOpt.get().getReview().getId());
        }
        review.setComment(bookDto.review());
        review.setBook(book);
        return review;
    }

    @GetMapping
    public ResponseEntity<List<Book>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(isBookFound(id) != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(isBookFound(id) != null ? bookService.findById(id) : "Book not found.");
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);

        var setPublishers = setPublishers(book, bookDto);
        if (setPublishers instanceof ResponseEntity) {
            return setPublishers;
        }

        var setAuthors = setAuthors(book, bookDto);
        if (setAuthors instanceof ResponseEntity) {
            return setAuthors;
        }

        book.setReview(setReview(book, bookDto, null, false));

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookDto bookDto) {
        Optional<Book> bookOpt = bookService.findById(id);
        if (!bookOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        book.setId(bookOpt.get().getId());

        var setPublishers = setPublishers(book, bookDto);
        if (setPublishers instanceof ResponseEntity) {
            return setPublishers;
        }

        var setAuthors = setAuthors(book, bookDto);
        if (setAuthors instanceof ResponseEntity) {
            return setAuthors;
        }

        book.setReview(setReview(book, bookDto, bookOpt, true));

        return ResponseEntity.status(HttpStatus.OK).body(bookService.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Book> bookOpt = bookService.findById(id);
        if (!bookOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        bookService.delete(bookOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
    }
}
