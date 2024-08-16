package com.ns.techtask.service.impl;

import com.ns.techtask.dto.request.MemberCreateAndUpdateDto;
import com.ns.techtask.model.Book;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.MemberRepository;
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

class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadById_WhenMemberExists() {
        Member member = new Member("John Doe");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member result = memberService.read(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testReadById_WhenMemberDoesNotExist() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.read(1L));
        assertEquals("Member not found with ID: 1", exception.getMessage());
    }

    @Test
    void testReadAll() {
        Member member1 = new Member("John Doe");
        Member member2 = new Member("Jane Doe");
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member1, member2));

        List<Member> result = memberService.read();

        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testCreateMember() {
        MemberCreateAndUpdateDto memberDto = new MemberCreateAndUpdateDto("John Doe");
        Member newMember = new Member("John Doe");
        when(memberRepository.save(any(Member.class))).thenReturn(newMember);

        Member result = memberService.create(memberDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testUpdateMember_WhenMemberExists() {
        Member existingMember = new Member("John Doe");
        MemberCreateAndUpdateDto memberDto = new MemberCreateAndUpdateDto("Updated Name");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(any(Member.class))).thenReturn(existingMember);

        Member result = memberService.update(1L, memberDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(existingMember);
    }

    @Test
    void testUpdateMember_WhenMemberDoesNotExist() {
        MemberCreateAndUpdateDto memberDto = new MemberCreateAndUpdateDto("Updated Name");
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.update(1L, memberDto));
        assertEquals("Member not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteMember_WhenMemberExistsAndNotBorrowed() {
        Member member = new Member("John Doe");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        boolean result = memberService.delete(1L);

        assertTrue(result);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMember_WhenMemberExistsAndBorrowed() {
        Member member = new Member("John Doe");
        member.getBorrowedBooks().add(new BorrowedBook());
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        boolean result = memberService.delete(1L);

        assertFalse(result);
        verify(memberRepository, never()).deleteById(1L);
    }

    @Test
    void testDeleteMember_WhenMemberDoesNotExist() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.delete(1L));
        assertEquals("Member not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetAmountBorrowedBooks_WhenMemberExists() {
        Book book1 = new Book("Title1", "Author1", 1);
        Book book2 = new Book("Title2", "Author2", 1);
        Member member = new Member("John Doe");
        member.getBorrowedBooks().add(new BorrowedBook(book1,member,3));
        member.getBorrowedBooks().add(new BorrowedBook(book2,member,2));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        int result = memberService.getAmountBorrowedBooks(1L);

        assertEquals(5, result);
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAmountBorrowedBooks_WhenMemberDoesNotExist() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.getAmountBorrowedBooks(1L));
        assertEquals("Member not found with ID: 1", exception.getMessage());
    }

}