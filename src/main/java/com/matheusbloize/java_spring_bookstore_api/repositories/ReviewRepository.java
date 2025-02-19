package com.matheusbloize.java_spring_bookstore_api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheusbloize.java_spring_bookstore_api.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

}
