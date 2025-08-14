package com.lms.borrowService.model;

import java.time.LocalDateTime;

import com.lms.borrowService.util.TransactionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "transaction")
public class BorrowTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long memberId;
	private Long bookId;
	private LocalDateTime borrowDate;
	private LocalDateTime dueDate;
	private LocalDateTime returnDate;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	

}
