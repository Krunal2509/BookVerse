package com.service.bookverse.feature.book.controller;

import com.service.bookverse.feature.book.dto.BookRequestDto;
import com.service.bookverse.feature.book.dto.BookResponseDto;
import com.service.bookverse.feature.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;


    @PreAuthorize("hasAuthority('BOOK_ADD')")
    @PostMapping("/addBook")
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookRequestDto request){

        BookResponseDto response = bookService.addBook(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BOOK_READ')")
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @PreAuthorize("hasAuthority('BOOK_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }


    @PreAuthorize("hasAuthority('BOOK_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Integer id,
            @Valid @RequestBody BookRequestDto request)
    {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
