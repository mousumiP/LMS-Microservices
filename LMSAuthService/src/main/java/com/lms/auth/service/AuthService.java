package com.lms.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.auth.dto.AuthResponseDto;
import com.lms.auth.model.Member;
import com.lms.auth.model.User;
import com.lms.auth.repository.MemberRepository;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private MemberRepository memberRepo;

	public AuthResponseDto getToken(String username, String password) throws UsernameNotFoundException {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		if (authentication.isAuthenticated()) {
			Optional<Member> member = memberRepo.findByEmail(username);
			if (!member.isEmpty()) {
				String token = jwtService.generateAccessToken(member.get());
				String refreshToken = jwtService.generateRefreshToken(username);
				AuthResponseDto responseDto = AuthResponseDto.builder().accessToken(token).refreshToken(refreshToken)
						.expiresIn("30").scope("read write").build();
				return responseDto;
			}

		} else {
			throw new UsernameNotFoundException("User not found");
		}
		return null;

	}

	/**
	public ResponseEntity<AuthResponseDto> refreshAccessToken(String refreshToken) {
		String username;
		try {
			username = jwtService.extractUsername(refreshToken);

			if (!jwtService.isTokenValid(refreshToken, username)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}

			Optional<User> user = userRepo.findByEmail(username);
			if (!user.isEmpty()) {
				// Step 3: Generate new tokens
				String newAccessToken = jwtService.generateAccessToken(user.get());
				String newRefreshToken = jwtService.generateRefreshToken(username);

				// Step 4: Build response
				AuthResponseDto response = AuthResponseDto.builder().accessToken(newAccessToken)
						.refreshToken(newRefreshToken).expiresIn("30").scope("read write").build();
				return ResponseEntity.ok(response);
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return null;

	}
**/
}
