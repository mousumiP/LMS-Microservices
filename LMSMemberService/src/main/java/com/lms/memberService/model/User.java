package com.lms.memberService.model;

import jakarta.persistence.Entity;
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

}
