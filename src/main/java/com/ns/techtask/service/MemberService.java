package com.ns.techtask.service;
import com.ns.techtask.dto.request.MemberCreateAndUpdateDto;
import com.ns.techtask.model.Member;

import java.util.List;

public interface MemberService {
    public Member read(Long id);
    public List<Member> read();
    public Member create(MemberCreateAndUpdateDto member);
    public Member update(Long id, MemberCreateAndUpdateDto member);
    public boolean delete(Long id);

    public Integer getAmountBorrowedBooks(Long id);

}
