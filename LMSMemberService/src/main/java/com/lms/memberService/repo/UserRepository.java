package com.lms.memberService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.memberService.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	
	Optional<User> findByEmail(String email);

}
