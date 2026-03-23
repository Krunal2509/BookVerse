package com.service.bookverse.feature.book.service;

import com.service.bookverse.feature.book.dto.BookRequestDto;
import com.service.bookverse.feature.book.dto.BookResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookResponseDto addBook(BookRequestDto bookRequestDto);

    BookResponseDto getBookById(Integer id);

    List<BookResponseDto> getAllBooks();

    BookResponseDto updateBook(Integer id ,BookRequestDto bookRequestDto);

    BookResponseDto deleteBook(Integer id);

}
