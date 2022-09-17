package com.nagarro.library.repositories;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nagarro.library.entities.Author;

public interface AuthorRepository extends CrudRepository<Author, UUID> {

	@Transactional
	@Modifying
	@Query("UPDATE Author a SET a.name = ?1 WHERE id = ?2")
	int updateAuthor(String name, UUID id);

	// why is use it -> because default didn't return anything
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Author a WHERE a.id = ?1")
	int deleteAuthorById(UUID id);

}
