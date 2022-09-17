package com.nagarro.library.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.library.entities.Author;
import com.nagarro.library.repositories.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRepository;

	public Iterable<Author> findAllAuthors() {
		return authorRepository.findAll();
	}

	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}

	public Optional<Author> findAuthorById(UUID id) {
		return authorRepository.findById(id);
	}

	public int updateAuthorById(String name, UUID id) {
		return authorRepository.updateAuthor(name, id);
	}

	public int deleteByIdWithReturn(UUID id) {
		authorRepository.deleteById(id);
		return 1;
	}

	public void deleteById(UUID id) {

		authorRepository.deleteById(id);

	}

	public void deleteByEntity(Author author) {

		authorRepository.delete(author);

	}

}
