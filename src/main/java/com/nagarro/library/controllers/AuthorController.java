package com.nagarro.library.controllers;

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

import com.nagarro.library.dto.AuthorResponseModel;
import com.nagarro.library.dto.ErrorResponseDTO;
import com.nagarro.library.entities.Author;
import com.nagarro.library.service.AuthorService;

@RestController
@CrossOrigin
public class AuthorController {

	@Autowired
	AuthorService authorService;

	@RequestMapping(value = "/author", method = RequestMethod.POST)
	public ResponseEntity<?> addAuthor(@RequestBody Author author) {

		Author createdAuthor = authorService.saveAuthor(author);
		if (createdAuthor == null) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("Something went wrong"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(createdAuthor);
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public Iterable<Author> getAuthors() {
		return authorService.findAllAuthors();
	}

	@RequestMapping(value = "/author", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAuthor(@RequestBody AuthorResponseModel author) {

		int rowUpdated = authorService.updateAuthorById(author.getName(), UUID.fromString(author.getId()));

		if (rowUpdated == 0) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("No record found with given id"),
					HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(author);

	}

	@RequestMapping(value = "/author", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAuthor(@RequestParam String id) {

		// int rowDeleted = authorService.deleteAuthorById(UUID.fromString(id));

		Optional<Author> authorOptional = authorService.findAuthorById(UUID.fromString(id));
		if (authorOptional.isEmpty()) {
			return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("No record found with given id"),
					HttpStatus.NOT_FOUND);
		}

		authorService.deleteByEntity(authorOptional.get());

		return new ResponseEntity<ErrorResponseDTO>(new ErrorResponseDTO("Document deleted successfully"),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/author-by-id", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorById(@RequestParam String id) {

		Optional<Author> autOptional = authorService.findAuthorById(UUID.fromString(id));
		if (!autOptional.isPresent()) {
			return new ResponseEntity<>(new ErrorResponseDTO("No author found with given id"), HttpStatus.NOT_FOUND);
		}
		Author author = autOptional.get();
		return new ResponseEntity<>(author, HttpStatus.OK);
	}

}
