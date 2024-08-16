package com.ns.techtask.service;

import com.ns.techtask.dto.request.BookCreateDto;
import com.ns.techtask.dto.request.BookUpdateDto;
import com.ns.techtask.model.Book;

import java.util.List;

public interface BookService {
    public Book read(Long id);
    public List<Book> read();
    public Book create(BookCreateDto book);
    public Book update(Long id, BookUpdateDto bookUpdateDto);
    public boolean delete(Long id);
    public void borrowBook(Long id);
    public void returnBook(Long id);
}
