package com.lms.memberService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.memberService.model.Member;
import com.lms.memberService.repo.MemberRepository;
import com.lms.memberService.util.MemberRole;
import com.lms.memberService.util.MemberStatus;

@Service
public class MemberService {
	
	private final MemberRepository memberRepo;

	public MemberService(MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}

	public ResponseEntity<List<Member>> getMembers() {		
		try {
			List<Member> memberList = memberRepo.findAll();
			if(!memberList.isEmpty()) {
				return ResponseEntity.ok(memberList);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	public ResponseEntity<?> saveMembers(Member member) {		
		try {
			Optional<Member> memberObj = memberRepo.findByEmail(member.getEmail());
			if(memberObj.isEmpty()) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);				
				member.setPassword(passwordEncoder.encode(member.getPassword()));
				member.setJoinedAt(LocalDate.now());
				member.setRole(MemberRole.USER);
				member.setStatus(MemberStatus.ACTIVE);
				memberRepo.save(member);
				return ResponseEntity.ok(member);
			} else {
				 return ResponseEntity
				            .status(HttpStatus.CONFLICT)
				            .body("User with this email already exists");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	public ResponseEntity<Member> getMemberById(Long id) {
		Optional<Member> member = memberRepo.findById(id);
		if(!member.isEmpty()) {
			return ResponseEntity.ok(member.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
