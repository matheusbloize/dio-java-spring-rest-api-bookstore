package com.matheusbloize.java_spring_bookstore_api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheusbloize.java_spring_bookstore_api.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
