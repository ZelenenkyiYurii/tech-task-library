package com.ns.techtask.repository;

import com.ns.techtask.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    boolean existsByTitleAndAuthor(String title, String author);

    Optional<Book> findByTitleAndAuthor(String title, String author);
}
