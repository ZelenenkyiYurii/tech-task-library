package com.ns.techtask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "amount")
    Integer amount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowedBook> borrowedBooks = new HashSet<>();

    public Book(String title, String author, Integer amount) {
        this.title = title;
        this.author = author;
        this.amount = amount;
    }
}
