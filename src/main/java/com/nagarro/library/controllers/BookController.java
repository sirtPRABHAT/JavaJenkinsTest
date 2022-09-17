package com.nagarro.library.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.library.dto.BookRequestModel;
import com.nagarro.library.dto.ErrorResponseDTO;
import com.nagarro.library.entities.Book;
import com.nagarro.library.service.AuthorService;
import com.nagarro.library.service.BookService;

@RestController
@CrossOrigin
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	AuthorService authorService;

	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<?> addBook(@RequestBody BookRequestModel bookRequestModel) throws ParseException {
		Date currentDate = new Date();
		System.out.println(currentDate);
		Optional<Book> existingBook = bookService.findBookById(bookRequestModel.getId());
		if (existingBook.isPresent())
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("Book already present with give code"),
					HttpStatus.BAD_REQUEST);
		Book book = new Book();
		book.setId(bookRequestModel.getId());
		book.setName(bookRequestModel.getName());
		book.setAuthor(authorService.findAuthorById(UUID.fromString(bookRequestModel.getAuthorId())).get());
		book.setTimestamp(new Date());

		Book createdBook = bookService.saveBook(book);
		if (createdBook == null) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("Something went wrong !!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(createdBook);

	}

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public Iterable<Book> getAllBooks() {
		return bookService.findAllBookSortByName();
	}

	@RequestMapping(value = "/book", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBook(@RequestBody BookRequestModel book) {

		int rowUpdated = bookService.updateBookById(book.getName(),
				authorService.findAuthorById(UUID.fromString(book.getAuthorId())).get(), book.getId());

		if (rowUpdated == 0) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("No record found with given id"),
					HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(book);

	}

	@RequestMapping(value = "/book", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBook(@RequestParam String id) {
		int deletedRowCount = bookService.deleteBookById(id);

		if (deletedRowCount == 0) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("No record found with given id"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("Document deleted successfully"),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/book-by-id", method = RequestMethod.GET)
	public ResponseEntity<?> getBookById(@RequestParam String id) {

		Optional<Book> bookOptional = bookService.findBookById(id);
		if (!bookOptional.isPresent()) {
			return new ResponseEntity<>(new ErrorResponseDTO("No book found with given id"), HttpStatus.NOT_FOUND);
		}
		Book book = bookOptional.get();
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

}
