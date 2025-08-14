package com.lms.borrowService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.borrowService.dto.BorrowRequestDto;
import com.lms.borrowService.dto.ReturnRequestDto;
import com.lms.borrowService.service.BorrowService;

@RestController
@RequestMapping("/lms")
public class BorrowController {
	
	@Autowired
	private BorrowService borrowService;
	
	@PostMapping("/borrow")
	public ResponseEntity<?> borrowBook(@RequestBody BorrowRequestDto borrowRequestDto) {
		return borrowService.borrowBook(borrowRequestDto);
	}

	@PostMapping("/return")
	public ResponseEntity<?> returnBook(@RequestBody ReturnRequestDto returnRequestDto) {
		return borrowService.returnBook(returnRequestDto);
	}
	
	
}
