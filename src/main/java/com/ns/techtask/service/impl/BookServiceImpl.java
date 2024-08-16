package com.ns.techtask.service.impl;

import com.ns.techtask.dto.request.BookCreateDto;
import com.ns.techtask.dto.request.BookUpdateDto;
import com.ns.techtask.model.Book;
import com.ns.techtask.repository.BookRepository;
import com.ns.techtask.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public Book read(Long id) {
        Optional<Book> bookOptional =bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    @Override
    public List<Book> read() {
        return bookRepository.findAll();
    }

    @Override
    public Book create(BookCreateDto bookDto) {
        Optional<Book> bookOptional=bookRepository.findByTitleAndAuthor(bookDto.title(), bookDto.author());
        if(bookOptional.isPresent()){
            Book book=bookOptional.get();
            book.setAmount(book.getAmount()+1);
            return bookRepository.save(book);
        }else {
            Book newBook=new Book(bookDto.title(),bookDto.author(),1);
            return bookRepository.save(newBook);
        }
    }

    @Override
    public Book update(Long id, BookUpdateDto bookUpdateDto) {
        Optional<Book> bookOptional=bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book=bookOptional.get();
            book.setAmount(bookUpdateDto.amount());
            book.setAuthor(bookUpdateDto.author());
            book.setTitle(bookUpdateDto.title());
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Book> bookOptional=bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book=bookOptional.get();
            if(book.getBorrowedBooks().isEmpty()){
                bookRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public void borrowBook(Long id) {
        Optional<Book> bookOptional=bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book=bookOptional.get();
            if(book.getAmount()>0){
                book.setAmount(book.getAmount()-1);
                bookRepository.save(book);
            }
        }
    }

    @Override
    public void returnBook(Long id) {
        Optional<Book> bookOptional=bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book=bookOptional.get();
            book.setAmount(book.getAmount()+1);
            bookRepository.save(book);
        }
    }

}
