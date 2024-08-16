package com.ns.techtask.controller;

import com.ns.techtask.model.Book;
import com.ns.techtask.service.impl.LibraryManagerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/manager")
@AllArgsConstructor
public class LibraryManagerController {
    private final LibraryManagerServiceImpl libraryManager;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBooks(@RequestParam Long bookId, @RequestParam Long memberId) {
        try {
            libraryManager.borrowBooks(bookId, memberId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBooks(@RequestParam Long bookId, @RequestParam Long memberId) {
        try {
            libraryManager.returnBooks(bookId, memberId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/borrowed-books/member")
    public ResponseEntity<List<Book>> getBorrowedBooksByMemberName(@RequestParam String name) {
        try {
            List<Book> borrowedBooks = libraryManager.getBorrowedBooksByMemberName(name);
            return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/borrowed-books/distinct")
    public ResponseEntity<List<String>> getDistinctBorrowedBookNames() {
        List<String> bookNames = libraryManager.getDistinctBorrowedBookNames();
        return !bookNames.isEmpty()
                ? new ResponseEntity<>(bookNames, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/borrowed-books/amount")
    public ResponseEntity<Map<String, Integer>> getDistinctBorrowedBookNamesAndAmounts() {
        Map<String, Integer> bookNamesAndAmounts = libraryManager.getDistinctBorrowedBookNamesAndAmounts();
        return !bookNamesAndAmounts.isEmpty()
                ? new ResponseEntity<>(bookNamesAndAmounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
