package com.ns.techtask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name="membership_date")
    LocalDate membershipDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowedBook> borrowedBooks=new HashSet<>();

    public Member(String name) {
        this.name=name;
    }

    @PrePersist
    protected void onCreate(){
        this.membershipDate=LocalDate.now();
    }
}
