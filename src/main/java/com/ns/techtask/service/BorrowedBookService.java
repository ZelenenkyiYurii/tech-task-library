package com.ns.techtask.service;

import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;

public interface BorrowedBookService {
    public void add(Book book, Member member);
    public void returnBook(BorrowedBook borrowedBook);
}
