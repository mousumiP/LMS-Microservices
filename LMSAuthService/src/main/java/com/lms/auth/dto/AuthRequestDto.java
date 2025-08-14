package com.lms.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthRequestDto {
	
	private String username;
	private String password;

}
