package com.service.bookverse.feature.book.repository;

import com.service.bookverse.feature.book.model.Book;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByIsbn(String isbn);

    Book getBookById(Integer id);
}
