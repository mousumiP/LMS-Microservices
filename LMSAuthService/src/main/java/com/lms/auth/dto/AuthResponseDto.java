package com.lms.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class AuthResponseDto {

	private String accessToken;
	private String refreshToken;
	private String expiresIn;
	private String scope;
}
