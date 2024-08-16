package com.ns.techtask.service.impl;

import com.ns.techtask.dto.request.MemberCreateAndUpdateDto;
import com.ns.techtask.model.BorrowedBook;
import com.ns.techtask.model.Member;
import com.ns.techtask.repository.MemberRepository;
import com.ns.techtask.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member read(Long id) {
        Optional<Member> memberOptional =memberRepository.findById(id);
        return memberOptional.orElseThrow(()->new IllegalArgumentException("Member not found with ID: " + id));
    }

    @Override
    public List<Member> read() {
        return memberRepository.findAll();
    }

    @Override
    public Member create(MemberCreateAndUpdateDto member) {
        Member newMember=new Member(member.name());
        return memberRepository.save(newMember);
    }

    @Override
    public Member update(Long id, MemberCreateAndUpdateDto memberDto) {
        Optional<Member> memberOptional=memberRepository.findById(id);
        if(memberOptional.isPresent()){
            Member member=memberOptional.get();
            member.setName(memberDto.name());
            return memberRepository.save(member);
        }else {
            throw new IllegalArgumentException("Member not found with ID: " + id);
        }
    }


    @Override
    public boolean delete(Long id) {
        Optional<Member> memberOptional=memberRepository.findById(id);
        if(memberOptional.isPresent()){
            Member member=memberOptional.get();
            if(member.getBorrowedBooks().isEmpty()){
                memberRepository.deleteById(id);
                return true;
            }
        }else {
            throw new IllegalArgumentException("Member not found with ID: " + id);
        }
        return false;
    }

    @Override
    public Integer getAmountBorrowedBooks(Long id) {
        Optional<Member> memberOptional=memberRepository.findById(id);
        if(memberOptional.isPresent()){
            Member member=memberOptional.get();
            return member.getBorrowedBooks().stream()
                    .mapToInt(BorrowedBook::getAmount)
                    .sum();
        }else {
            throw new IllegalArgumentException("Member not found with ID: " + id);
        }
    }


}
