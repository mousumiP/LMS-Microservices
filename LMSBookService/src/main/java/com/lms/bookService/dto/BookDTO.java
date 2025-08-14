package com.lms.bookService.dto;

import java.time.LocalDate;

import com.lms.bookService.model.Book;

public record BookDTO(Long id, String title, String author, String genre, String isbn, Integer quantity, Boolean deleted, LocalDate createdDate) {
	public static BookDTO fromEntity(Book book) {
		return new BookDTO(
				book.getId(),
				book.getTitle(),
				book.getAuthor(),
				book.getGenre(),
				book.getIsbn(),
				book.getQuantity(),
				book.getDeleted(),
				book.getCreatedDate()
				);
		
	}

}
