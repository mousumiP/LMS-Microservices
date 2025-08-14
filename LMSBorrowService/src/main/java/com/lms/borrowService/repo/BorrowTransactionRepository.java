package com.lms.borrowService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.borrowService.model.BorrowTransaction;

@Repository
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long>{

}
