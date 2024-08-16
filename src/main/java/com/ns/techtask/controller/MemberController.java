package com.ns.techtask.controller;

import com.ns.techtask.dto.request.MemberCreateAndUpdateDto;
import com.ns.techtask.dto.response.MemberDto;
import com.ns.techtask.model.Member;
import com.ns.techtask.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> read(@PathVariable(name = "id") Long id) {
        final Member member = memberService.read(id);
        final MemberDto memberDto=new MemberDto(
                member.getId(),
                member.getName(),
                member.getMembershipDate()
        );
        return (member != null)
                ? new ResponseEntity<>(memberDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> read() {
        final List<MemberDto> list = memberService.read()
                .stream()
                .map(member -> new MemberDto(
                        member.getId(),
                        member.getName(),
                        member.getMembershipDate()
                )).toList();
        return !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MemberCreateAndUpdateDto memberCreateAndUpdateDto) {
        memberService.create(memberCreateAndUpdateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@RequestBody @Valid MemberCreateAndUpdateDto memberUpdateDto, @PathVariable Long id) {
        final Member member = memberService.update(id, memberUpdateDto);
        final MemberDto memberDto=new MemberDto(
                member.getId(),
                member.getName(),
                member.getMembershipDate()
        );
        return member != null
                ? new ResponseEntity<>(memberDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        final boolean delete = memberService.delete(id);
        return delete
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
