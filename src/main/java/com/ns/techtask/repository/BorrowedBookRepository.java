package com.ns.techtask.repository;

import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook,Long> {
    Optional<BorrowedBook> findByBook_IdAndMember_Id(Long id, Long id1);

    List<BorrowedBook> findByMember(Member member);

}
