package com.ns.techtask.service.impl;

import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.BookRepository;
import com.ns.techtask.repository.BorrowedBookRepository;
import com.ns.techtask.repository.MemberRepository;
import com.ns.techtask.service.BookService;
import com.ns.techtask.service.BorrowedBookService;
import com.ns.techtask.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryManagerServiceImplTest {

    @Mock
    private BookService bookService;

    @Mock
    private MemberService memberService;

    @Mock
    private BorrowedBookService borrowedBookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private LibraryManagerServiceImpl libraryManagerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBooks_SuccessfulBorrow() {
        Book book = new Book("Title", "Author", 5);
        book.setId(1L);
        Member member = new Member("John Doe");
        member.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.getAmountBorrowedBooks(1L)).thenReturn(5);

        libraryManagerService.borrowBooks(1L, 1L);

        verify(bookService, times(1)).borrowBook(1L);
        verify(borrowedBookService, times(1)).add(book, member);
    }

    @Test
    void testBorrowBooks_BookNotAvailable() {
        Book book = new Book("Title", "Author", 0);
        book.setId(1L);
        Member member = new Member("John Doe");
        member.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        libraryManagerService.borrowBooks(1L, 1L);

        verify(bookService, never()).borrowBook(1L);
        verify(borrowedBookService, never()).add(any(Book.class), any(Member.class));
    }

    @Test
    void testReturnBooks_SuccessfulReturn() {
        BorrowedBook borrowedBook = new BorrowedBook();
        Book book = new Book("Title", "Author", 1);
        Member member = new Member("John Doe");
        borrowedBook.setBook(book);
        borrowedBook.setMember(member);
        borrowedBook.setAmount(1);

        when(borrowedBookRepository.findByBook_IdAndMember_Id(1L, 1L)).thenReturn(Optional.of(borrowedBook));

        libraryManagerService.returnBooks(1L, 1L);

        verify(bookService, times(1)).returnBook(1L);
        verify(borrowedBookService, times(1)).returnBook(borrowedBook);
    }

    @Test
    void testGetBorrowedBooksByMemberName_SuccessfulRetrieval() {
        Member member = new Member("John Doe");
        List<BorrowedBook> borrowedBooks = Arrays.asList(new BorrowedBook(new Book("Title", "Author", 1), member, 1));

        when(memberRepository.findByName("John Doe")).thenReturn(Optional.of(member));
        when(borrowedBookRepository.findByMember(member)).thenReturn(borrowedBooks);

        List<Book> result = libraryManagerService.getBorrowedBooksByMemberName("John Doe");

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    void testGetDistinctBorrowedBookNames_SuccessfulRetrieval() {
        List<BorrowedBook> borrowedBooks = Arrays.asList(
                new BorrowedBook(new Book("Title1", "Author1", 1), new Member(), 1),
                new BorrowedBook(new Book("Title2", "Author2", 1), new Member(), 1),
                new BorrowedBook(new Book("Title1", "Author1", 1), new Member(), 1)
        );

        when(borrowedBookRepository.findAll()).thenReturn(borrowedBooks);

        List<String> result = libraryManagerService.getDistinctBorrowedBookNames();

        assertEquals(2, result.size());
        assertTrue(result.contains("Title1"));
        assertTrue(result.contains("Title2"));
    }

    @Test
    void testGetDistinctBorrowedBookNamesAndAmounts_SuccessfulRetrieval() {
        List<BorrowedBook> borrowedBooks = Arrays.asList(
                new BorrowedBook(new Book("Title1", "Author1", 1), new Member(), 1),
                new BorrowedBook(new Book("Title2", "Author2", 1), new Member(), 1),
                new BorrowedBook(new Book("Title1", "Author1", 1), new Member(), 2)
        );

        when(borrowedBookRepository.findAll()).thenReturn(borrowedBooks);

        Map<String, Integer> result = libraryManagerService.getDistinctBorrowedBookNamesAndAmounts();

        assertEquals(2, result.size());
        assertEquals(3, result.get("Title1"));
        assertEquals(1, result.get("Title2"));
    }
}
