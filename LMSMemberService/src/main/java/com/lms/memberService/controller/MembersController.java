package com.lms.memberService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.memberService.model.Member;
import com.lms.memberService.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lms")
public class MembersController {
	
	@Autowired
	private MemberService memberService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/members")
	public ResponseEntity<List<Member>> getMembers() {
		return memberService.getMembers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/members")
	public ResponseEntity<?> saveMembers(@Valid @RequestBody Member member, BindingResult result) {
		if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .toList();

	        return ResponseEntity.badRequest().body(errors);
	    }

	    return memberService.saveMembers(member);
	}

	
	@GetMapping("/members/{id}")
	public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
		return memberService.getMemberById(id);
	}
}
