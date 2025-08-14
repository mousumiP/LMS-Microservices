package com.lms.bookService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.bookService.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("Select b from Book b where b.deleted = false "
			+ "AND (:title is NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))"
			+ "AND (:author is NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))"
			+ "AND (:genre is NULL OR LOWER(b.genre) LIKE LOWER(CONCAT('%', :genre, '%')))"
			+ "AND (:available IS NULL OR (:available = true AND b.quantity > 0) OR (:available = false AND b.quantity = 0))"
			)
	List<Book> searchBooks(
				@Param("title") String title,
				@Param("author") String author, 
				@Param("genre") String genre, 
				@Param("available") Boolean available);

}
