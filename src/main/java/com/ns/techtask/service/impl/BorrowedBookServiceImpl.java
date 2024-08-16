package com.ns.techtask.service.impl;

import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.BorrowedBookRepository;
import com.ns.techtask.service.BorrowedBookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class BorrowedBookServiceImpl implements BorrowedBookService {
    private final BorrowedBookRepository borrowedBookRepository;

    @Override
    public void add(Book book, Member member) {
        Optional<BorrowedBook> borrowedBookOptional=borrowedBookRepository.findByBook_IdAndMember_Id(book.getId(),member.getId());
        BorrowedBook borrowedBook;
        if(borrowedBookOptional.isPresent()){
            borrowedBook = borrowedBookOptional.get();
            borrowedBook.setAmount(borrowedBook.getAmount()+1);
        }else {
            borrowedBook = new BorrowedBook();
            borrowedBook.setAmount(1);
            borrowedBook.setBook(book);
            borrowedBook.setMember(member);
        }
        borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public void returnBook(BorrowedBook borrowedBook) {
        if(borrowedBook.getAmount()>1){
            borrowedBook.setAmount(borrowedBook.getAmount()-1);
            borrowedBookRepository.save(borrowedBook);
        }else {
            borrowedBookRepository.delete(borrowedBook);
        }
    }

}
