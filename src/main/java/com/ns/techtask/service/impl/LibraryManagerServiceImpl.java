package com.ns.techtask.service.impl;

import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.BookRepository;
import com.ns.techtask.repository.BorrowedBookRepository;
import com.ns.techtask.repository.MemberRepository;
import com.ns.techtask.service.BookService;
import com.ns.techtask.service.BorrowedBookService;
import com.ns.techtask.service.LibraryManagerService;
import com.ns.techtask.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryManagerServiceImpl implements LibraryManagerService {

    @Value("${techtask.app.borrow_book_amount_limit}")
    private int limit;

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowedBookService borrowedBookService;

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowedBookRepository borrowedBookRepository;

    @Transactional
    @Override
    public void borrowBooks(Long bookId, Long memberId) {
        Optional<Book> bookOptional=bookRepository.findById(bookId);
        Optional<Member> memberOptional=memberRepository.findById(memberId);
        if(bookOptional.isPresent() && memberOptional.isPresent()){
            Book book=bookOptional.get();
            Member member=memberOptional.get();
            if((memberService.getAmountBorrowedBooks(memberId))<10 && book.getAmount()>0){
                bookService.borrowBook(bookId);
                borrowedBookService.add(book,member);
            }
        }
    }

    @Override
    public void returnBooks(Long bookId, Long memberId) {
        Optional<BorrowedBook> borrowedBook=borrowedBookRepository.findByBook_IdAndMember_Id(bookId,memberId);
        if(borrowedBook.isPresent()){
            bookService.returnBook(bookId);
            borrowedBookService.returnBook(borrowedBook.get());
        }
    }

    @Override
    public List<Book> getBorrowedBooksByMemberName(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with name: " + name));

        return borrowedBookRepository.findByMember(member)
                .stream()
                .map(BorrowedBook::getBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDistinctBorrowedBookNames() {
        return borrowedBookRepository.findAll()
                .stream()
                .map(borrowedBook -> borrowedBook.getBook().getTitle())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getDistinctBorrowedBookNamesAndAmounts() {
        return borrowedBookRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        borrowedBook -> borrowedBook.getBook().getTitle(),
                        Collectors.summingInt(BorrowedBook::getAmount)
                ));
    }
}
