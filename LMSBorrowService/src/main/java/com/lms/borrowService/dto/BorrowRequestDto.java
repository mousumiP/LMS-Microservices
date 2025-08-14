package com.lms.borrowService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDto {
	
	private Long memberId;
	private Long bookId;

}
