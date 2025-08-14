package com.lms.external;

import java.time.LocalDate;

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
public class Book {

	private Long id;
	private String title;
	private String author;
	private String genre;
	private String isbn;
	private Integer quantity;
	private Boolean deleted;
	private LocalDate createdDate;

}
