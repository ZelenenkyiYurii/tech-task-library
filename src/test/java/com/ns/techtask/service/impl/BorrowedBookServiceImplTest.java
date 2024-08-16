package com.ns.techtask.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.BorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowedBookServiceImplTest {

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private BorrowedBookServiceImpl borrowedBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBook_WhenBorrowedBookExists() {
        Book book = new Book("Title", "Author", 5);
        book.setId(1L);
        Member member = new Member("John Doe");
        member.setId(1L);

        BorrowedBook existingBorrowedBook = new BorrowedBook();
        existingBorrowedBook.setAmount(2);
        when(borrowedBookRepository.findByBook_IdAndMember_Id(1L, 1L)).thenReturn(Optional.of(existingBorrowedBook));

        borrowedBookService.add(book, member);

        assertEquals(3, existingBorrowedBook.getAmount());
        verify(borrowedBookRepository, times(1)).save(existingBorrowedBook);
    }

    @Test
    void testAddBook_WhenBorrowedBookDoesNotExist() {
        Book book = new Book("Title", "Author", 5);
        book.setId(1L);
        Member member = new Member("John Doe");
        member.setId(1L);

        when(borrowedBookRepository.findByBook_IdAndMember_Id(1L, 1L)).thenReturn(Optional.empty());

        borrowedBookService.add(book, member);

        verify(borrowedBookRepository, times(1)).save(any(BorrowedBook.class));
    }

    @Test
    void testReturnBook_WhenAmountIsMoreThanOne() {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setAmount(3);

        borrowedBookService.returnBook(borrowedBook);

        assertEquals(2, borrowedBook.getAmount());
        verify(borrowedBookRepository, times(1)).save(borrowedBook);
    }

    @Test
    void testReturnBook_WhenAmountIsOne() {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setAmount(1);

        borrowedBookService.returnBook(borrowedBook);

        verify(borrowedBookRepository, times(1)).delete(borrowedBook);
        verify(borrowedBookRepository, never()).save(borrowedBook);
    }
}
