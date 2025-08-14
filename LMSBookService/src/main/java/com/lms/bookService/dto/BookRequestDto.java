package com.lms.bookService.dto;

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
public class BookRequestDto {
	private String title;
	private String author;
	private String genre;
	private String isbn;
	private Integer quantity;
}
