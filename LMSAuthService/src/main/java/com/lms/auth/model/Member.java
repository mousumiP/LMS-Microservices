package com.lms.auth.model;

import java.time.LocalDate;

import com.lms.auth.util.MemberRole;
import com.lms.auth.util.MemberStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private Long id;
	private String name;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING) // store enum name (ACTIVE, SUSPENDED, DEACTIVATED) in DB
	private MemberStatus status;
	
	@Enumerated(EnumType.STRING) // store enum name ("USER", "ADMIN") in DB
	private MemberRole role;

	private LocalDate joinedAt;

}
