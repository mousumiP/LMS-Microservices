package com.lms.bookService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lms.bookService.dto.BookDTO;
import com.lms.bookService.dto.BookRequestDto;
import com.lms.bookService.model.Book;
import com.lms.bookService.repo.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;

	public List<BookDTO> searchBooks(String title, String author, String genre, Boolean available) {
		List<Book> bookList = bookRepo.searchBooks(title, author, genre, available);
		return bookList.stream().map(BookDTO::fromEntity).toList();
	}

	public ResponseEntity<?> searchBookById(Long id) {
		Optional<Book> book = bookRepo.findById(id);
		if(!book.isEmpty()) {
			Optional<BookDTO> bookDto = book.map(BookDTO :: fromEntity);
			return ResponseEntity.ok(bookDto.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
		}
	}
	
	
	public Book saveBook(BookRequestDto dto) {
		Book book = new Book();
		try {
			book.setAuthor(dto.getAuthor());
			book.setTitle(dto.getTitle());
			book.setGenre(dto.getGenre());
			book.setIsbn(dto.getIsbn());
			book.setQuantity(dto.getQuantity());
			book.setCreatedDate(LocalDate.now());
			book.setDeleted(false);
			return bookRepo.save(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResponseEntity<?> updateBook(Long id, BookRequestDto dto) {
		Optional<Book> bookObj = bookRepo.findById(id);
		if(!bookObj.isEmpty()) {
			Book book = bookObj.get();
			book.setAuthor(dto.getAuthor());
			book.setTitle(dto.getTitle());
			book.setGenre(dto.getGenre());
			book.setIsbn(dto.getIsbn());
			book.setQuantity(dto.getQuantity());
			bookRepo.save(book);
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
		}		
	}

	
	public ResponseEntity<?> deleteBookById(Long id) {
		Optional<Book> book = bookRepo.findById(id);
		if(!book.isEmpty()) {
			bookRepo.delete(book.get());
			return ResponseEntity.status(HttpStatus.OK).body("Book has benn delete");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
		}
	}

	public ResponseEntity<?> updateBookQuantity(Long id, boolean param) {
		Optional<Book> bookObj = bookRepo.findById(id);
		if(!bookObj.isEmpty()) {
			Book book = bookObj.get();			
			book.setQuantity(param	== true ? book.getQuantity() + 1: book.getQuantity() - 1);
			bookRepo.save(book);
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
		}	
	}
}
