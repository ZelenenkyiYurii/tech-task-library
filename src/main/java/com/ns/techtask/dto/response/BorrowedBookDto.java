package com.ns.techtask.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.ns.techtask.model.BorrowedBook}
 */
public record BorrowedBookDto(Long id, Long bookId, String bookTitle, String bookAuthor, Integer bookAmount,
                              Long memberId, String memberName, LocalDate memberMembershipDate,
                              int amount) implements Serializable {
}