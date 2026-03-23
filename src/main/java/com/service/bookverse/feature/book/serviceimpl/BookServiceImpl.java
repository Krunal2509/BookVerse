package com.service.bookverse.feature.book.serviceimpl;

import com.service.bookverse.feature.book.dto.BookRequestDto;
import com.service.bookverse.feature.book.dto.BookResponseDto;
import com.service.bookverse.feature.book.model.Book;
import com.service.bookverse.feature.book.repository.BookRepository;
import com.service.bookverse.feature.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookResponseDto addBook(BookRequestDto request) {

        if(request.getIsbn() != null &&  bookRepository.existsByIsbn(request.getIsbn() )) {
            throw new RuntimeException("ISBN already exists");
        }

        Book book = mapToModel(request);

        Book savedBook = bookRepository.save(book);

        return mapToResponse(savedBook);
    }

    @Override
    public BookResponseDto getBookById(Integer id) {
        Book book = bookRepository.getBookById(id);

        if(book == null) throw new IllegalArgumentException("Book isn't exists");

        return mapToResponse(book);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<BookResponseDto> books = bookRepository.findAll().
                                        stream().
                                            map(this::mapToResponse).
                                        collect(Collectors.toList());
        return books;
    }

    @Override
    public BookResponseDto updateBook(Integer id, BookRequestDto request) {

        Book book = bookRepository.getBookById(id);

        if(book == null) throw new IllegalArgumentException("Book isn't exists");

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setStock(request.getStock());
        book.setPrice(request.getPrice());

        //jpa automatically understands this is update query
        Book updatedBook = bookRepository.save(book);


        return mapToResponse(updatedBook);
    }

    @Override
    public BookResponseDto deleteBook(Integer id) {
        Book book = bookRepository.getBookById(id);

        if(book == null) throw new IllegalArgumentException("Book isn't exists");

        bookRepository.delete(book);

        return mapToResponse(book);
    }

    private Book mapToModel(BookRequestDto request) {

        return Book.builder().
                    title(request.getTitle()).
                    author(request.getAuthor()).
                    isbn(request.getIsbn()).
                    description(request.getDescription()).
                    stock(request.getStock()).
                    price(request.getPrice()).
                    build();
    }


    private BookResponseDto mapToResponse(Book savedBook) {
        return BookResponseDto.builder().
                id(savedBook.getId()).
                title(savedBook.getTitle()).
                author(savedBook.getAuthor()).
                isbn(savedBook.getIsbn()).
                description(savedBook.getDescription()).
                stock(savedBook.getStock()).
                price(savedBook.getPrice()).build();
    }


}
