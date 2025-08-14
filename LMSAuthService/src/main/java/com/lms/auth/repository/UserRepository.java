package com.lms.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	
	Optional<User> findByEmail(String email);

}
