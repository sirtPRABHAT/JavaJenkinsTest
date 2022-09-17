package com.nagarro.library.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nagarro.library.entities.Author;
import com.nagarro.library.entities.Book;

public interface BookRepository extends CrudRepository<Book, String> {
	/*
	 * The @Modifying annotation is used to enhance the @Query annotation so that we
	 * can execute not only SELECT queries, but also INSERT, UPDATE, DELETE, and
	 * even DDL queries
	 * 
	 */
	List<Book> findAll(Sort sort);

	@Transactional
	@Modifying
	@Query("UPDATE Book b SET b.name = ?1, b.author = ?2 WHERE id = ?3")
	int updateBook(String name, Author author, String id);

	// why is use it -> because default didn't return anything
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Book b where b.id = ?1")
	int costumDeleteById(String id);

}
