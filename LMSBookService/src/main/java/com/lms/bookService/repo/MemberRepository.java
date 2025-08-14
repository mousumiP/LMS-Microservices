package com.lms.bookService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.bookService.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	
	Optional<Member> findByEmail(String email);
	
	List<Member> findAll();

}
