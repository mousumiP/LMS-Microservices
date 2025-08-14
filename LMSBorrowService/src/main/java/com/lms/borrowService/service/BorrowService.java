package com.lms.borrowService.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lms.borrowService.dto.BorrowRequestDto;
import com.lms.borrowService.dto.ReturnRequestDto;
import com.lms.borrowService.model.BorrowTransaction;
import com.lms.borrowService.model.Member;
import com.lms.borrowService.repo.BorrowTransactionRepository;
import com.lms.borrowService.util.MemberStatus;
import com.lms.borrowService.util.TransactionStatus;
import com.lms.external.Book;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BorrowService {

	private final RestTemplate restTemplate;

	@Value("${member.service.name}")
	private String memberServiceName;

	@Value("${book.service.name}")
	private String bookServiceName;
	
	@Value("${kafka.topic.name.book.borrowed.event}")
	private String bookBorrowedEvent;
	
	@Value("${kafka.topic.name.book.returned.event}")
	private String BookReturnedEvent;
	

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BorrowTransactionRepository transactionRepository;
	
	@Autowired
	private KafkaMessageProducer kafkaMessageProducer;

	public BorrowService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Book getBookById(Long id) {

		String token = request.getHeader("Authorization");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		String url = "http://" + bookServiceName + "/lms/books/" + id;

		ResponseEntity<Book> response = restTemplate.exchange(url, HttpMethod.GET, entity, Book.class);

		return response.getBody();
	}

	public ResponseEntity<?> borrowBook(BorrowRequestDto borrowRequestDto) {

		String token = request.getHeader("Authorization");
		HttpEntity<String> entity = createAuthEntity(token);

		// Validate Member
		Member member = fetchMember(borrowRequestDto.getMemberId(), entity);
		if (member == null) {
			return badRequest("Member not found");
		}
		if (!member.getStatus().equals(MemberStatus.ACTIVE)) {
			return badRequest("User is not Active");
		}

		// Validate Book
		Book book = fetchBook(borrowRequestDto.getBookId(), entity);
		if (book == null) {
			return badRequest("Book not found");
		}
		if (book.getQuantity() <= 0) {
			return badRequest("Book is out of stock");
		}

		// Save Transaction
		BorrowTransaction transaction = createBorrowTransaction(borrowRequestDto);
		transactionRepository.save(transaction);

		// Update Book Quantity
		updateBookQuantity(borrowRequestDto.getBookId(), entity, false);
		
		// Publish event to Kafka
		kafkaMessageProducer.sendMessage(bookBorrowedEvent, transaction);

		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);		
	}

	private void updateBookQuantity(Long bookId, HttpEntity<String> entity, boolean param) {
		String url = "http://" + bookServiceName + "/lms/booksQuantity/" + bookId+"/"+param;
		restTemplate.exchange(url, HttpMethod.PUT, entity, Book.class);
	}

	private BorrowTransaction createBorrowTransaction(BorrowRequestDto dto) {
		BorrowTransaction transaction = new BorrowTransaction();
		transaction.setBookId(dto.getBookId());
		transaction.setMemberId(dto.getMemberId());
		transaction.setBorrowDate(LocalDateTime.now());
		transaction.setDueDate(LocalDateTime.now().plusDays(14));
		transaction.setStatus(TransactionStatus.BORROWED);
		return transaction;
	}

	private Book fetchBook(Long bookId, HttpEntity<String> entity) {
		String url = "http://" + bookServiceName + "/lms/books/" + bookId;
		try {
			return restTemplate.exchange(url, HttpMethod.GET, entity, Book.class).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	private Member fetchMember(Long memberId, HttpEntity<String> entity) {
		String url = "http://" + memberServiceName + "/lms/members/" + memberId;
		try {
			return restTemplate.exchange(url, HttpMethod.GET, entity, Member.class).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	private HttpEntity<String> createAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		return new HttpEntity<>(headers);
	}

	private ResponseEntity<String> badRequest(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

		

	public ResponseEntity<?> returnBook(ReturnRequestDto returnRequestDto) {
		
		String token = request.getHeader("Authorization");
		HttpEntity<String> entity = createAuthEntity(token);
		
		Optional<BorrowTransaction> transaction = transactionRepository.findById(returnRequestDto.getTransactionId());
		if(transaction.isEmpty()) {
			return badRequest("Transaction not found"); 
		}
		BorrowTransaction transactionObj = transaction.get();
		transactionObj.setReturnDate(LocalDateTime.now());
		transactionObj.setStatus(getDaysDifference(transactionObj.getDueDate()) > 14 ? TransactionStatus.OVERDUE : TransactionStatus.RETURNED);
		transactionRepository.save(transactionObj);
		
		// Update Book Quantity
		updateBookQuantity(transactionObj.getBookId(), entity, true);
		
		// Publish event to Kafka
		kafkaMessageProducer.sendMessage(BookReturnedEvent, transactionObj);

		return ResponseEntity.status(HttpStatus.CREATED).body(transactionObj);
	}
	
	public long getDaysDifference(LocalDateTime dueDate) {
	    LocalDateTime today = LocalDateTime.now();
	    return ChronoUnit.DAYS.between(today, dueDate);
	}

}