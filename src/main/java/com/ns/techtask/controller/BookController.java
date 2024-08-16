package com.ns.techtask.controller;

import com.ns.techtask.dto.request.BookCreateDto;
import com.ns.techtask.dto.request.BookUpdateDto;
import com.ns.techtask.dto.response.BookDto;
import com.ns.techtask.model.Book;
import com.ns.techtask.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> read(@PathVariable(name = "id") Long id) {
        final Book book = bookService.read(id);
        final BookDto bookDto = new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getAmount());
        return (book != null)
                ? new ResponseEntity<>(bookDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> read() {
        final List<BookDto> list = bookService.read()
                .stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getAmount()
                )).toList();
        return !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid BookCreateDto bookCreateDto) {
        bookService.create(bookCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@RequestBody @Valid BookUpdateDto bookUpdateDto, @PathVariable Long id) {
        final Book book = bookService.update(id, bookUpdateDto);
        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        final boolean delete = bookService.delete(id);
        return delete
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
