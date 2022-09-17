package com.nagarro.library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.nagarro.library.entities.Author;
import com.nagarro.library.entities.Book;
import com.nagarro.library.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public Iterable<Book> findAllBookSortByName() {
		Order order = new Order(Direction.ASC, "name");
		return bookRepository.findAll(Sort.by(order));
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public Optional<Book> findBookById(String id) {
		return bookRepository.findById(id);
	}

	public int updateBookById(String name, Author author, String id) {
		return bookRepository.updateBook(name, author, id);
	}

	public int deleteBookById(String id) {
		return bookRepository.costumDeleteById(id);
	}

}
