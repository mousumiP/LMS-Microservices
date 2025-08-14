package com.lms.memberService.model;

import java.time.LocalDate;

import com.lms.memberService.util.MemberRole;
import com.lms.memberService.util.MemberStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	private String email;
	
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Enumerated(EnumType.STRING) // store enum name (ACTIVE, SUSPENDED, DEACTIVATED) in DB
	private MemberStatus status;
	
	@Enumerated(EnumType.STRING) // store enum name ("USER", "ADMIN") in DB
	private MemberRole role;

	private LocalDate joinedAt;

}
