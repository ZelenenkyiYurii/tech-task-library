package com.ns.techtask.service;

import com.ns.techtask.model.Book;

import java.util.List;
import java.util.Map;

public interface LibraryManagerService {
    public void borrowBooks(Long bookId, Long MemberId);
    public void returnBooks(Long bookId, Long MemberId);
    public List<Book> getBorrowedBooksByMemberName(String name);
    public List<String> getDistinctBorrowedBookNames();
    public Map<String,Integer> getDistinctBorrowedBookNamesAndAmounts();
}
