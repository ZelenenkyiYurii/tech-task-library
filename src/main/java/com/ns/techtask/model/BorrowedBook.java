package com.ns.techtask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrowed_books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"book_id", "member_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer amount;
    public BorrowedBook(Book book,Member member, Integer amount){
        this.book=book;
        this.member=member;
        this.amount=amount;
    }
}
