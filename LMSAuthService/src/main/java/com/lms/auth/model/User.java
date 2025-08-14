package com.lms.auth.model;

import com.lms.auth.util.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	
	@Id
	private String id;
	private String email;
	private String password;
	private boolean isActive;
	
	@Enumerated(EnumType.STRING) // store enum name ("USER", "ADMIN") in DB
	private UserRole userRole;

}
