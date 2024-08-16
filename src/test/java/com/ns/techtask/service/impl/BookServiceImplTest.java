package com.ns.techtask.service.impl;

import com.ns.techtask.dto.request.BookCreateDto;
import com.ns.techtask.dto.request.BookUpdateDto;
import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadById() {
        Book book = new Book("Title1", "Author1", 1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.read(1L);

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testReadAll() {
        Book book1 = new Book("Title1", "Author1", 1);
        Book book2 = new Book("Title2", "Author2", 1);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = bookService.read();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testCreateBook_WhenBookExists() {
        BookCreateDto bookDto = new BookCreateDto("Title1", "Author1");
        Book existingBook = new Book("Title1", "Author1", 1);
        when(bookRepository.findByTitleAndAuthor(bookDto.title(), bookDto.author())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        Book result = bookService.create(bookDto);

        assertNotNull(result);
        assertEquals(2, result.getAmount());
        verify(bookRepository, times(1)).findByTitleAndAuthor(bookDto.title(), bookDto.author());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testCreateBook_WhenBookDoesNotExist() {
        BookCreateDto bookDto = new BookCreateDto("Title1", "Author1");
        when(bookRepository.findByTitleAndAuthor(bookDto.title(), bookDto.author())).thenReturn(Optional.empty());
        Book newBook = new Book("Title1", "Author1", 1);
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        Book result = bookService.create(bookDto);

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        verify(bookRepository, times(1)).findByTitleAndAuthor(bookDto.title(), bookDto.author());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto("Updated Title", "Updated Author", 5);
        Book existingBook = new Book("Title1", "Author1", 1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        Book result = bookService.update(1L, bookUpdateDto);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals(5, result.getAmount());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testDeleteBook_WhenNotBorrowed() {
        Book book = new Book("Title1", "Author1", 1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.delete(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_WhenBorrowed() {
        Book book = new Book("Title1", "Author1", 1);
        Member member=new Member("John");
        book.getBorrowedBooks().add(new BorrowedBook(book,member,1)); // Adding a borrowed book to the list
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.delete(1L);

        assertFalse(result);
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    void testBorrowBook() {
        Book book = new Book("Title1", "Author1", 2);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.borrowBook(1L);

        assertEquals(1, book.getAmount());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook() {
        Book book = new Book("Title1", "Author1", 1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.returnBook(1L);

        assertEquals(2, book.getAmount());
        verify(bookRepository, times(1)).save(book);
    }
}