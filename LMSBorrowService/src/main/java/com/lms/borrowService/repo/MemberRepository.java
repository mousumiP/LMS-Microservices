package com.lms.borrowService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.borrowService.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	
	Optional<Member> findByEmail(String email);

}
