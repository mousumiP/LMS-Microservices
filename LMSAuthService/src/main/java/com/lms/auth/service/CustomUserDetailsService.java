package com.lms.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.auth.model.Member;
import com.lms.auth.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepo;

	public CustomUserDetailsService(MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return org.springframework.security.core.userdetails.User.builder().username(member.getEmail())
				.password(member.getPassword()).roles(member.getRole().name()) // If enum: USER, ADMIN
				.build();

	}

}
