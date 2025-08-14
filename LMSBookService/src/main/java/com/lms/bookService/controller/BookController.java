package com.lms.bookService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.bookService.dto.BookDTO;
import com.lms.bookService.dto.BookRequestDto;
import com.lms.bookService.model.Book;
import com.lms.bookService.service.BookService;

@RestController
@RequestMapping("/lms")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getBooks(
				@RequestParam(required = false) String title,
				@RequestParam(required = false) String author,
				@RequestParam(required = false) String genre,
				@RequestParam(required = false) Boolean available) {
		List<BookDTO> books = bookService.searchBooks(title, author, genre, available);
		return ResponseEntity.ok(books);		
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<?> getBookById(@PathVariable Long id) {
		return bookService.searchBookById(id);

	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/books")
	public ResponseEntity<Book> saveBook(@RequestBody BookRequestDto bookDto) {
		Book book = bookService.saveBook(bookDto);
		return ResponseEntity.ok(book);
	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/books/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookDto) {
		return bookService.updateBook(id, bookDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		return bookService.deleteBookById(id);
	}

	
	@PutMapping("/booksQuantity/{id}/{param}")
	public ResponseEntity<?> updateBookQuantity(@PathVariable Long id, @PathVariable boolean param) {
		return bookService.updateBookQuantity(id, param);
	}
}
