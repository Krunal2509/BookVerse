package com.service.bookverse.feature.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookResponseDto {

    private Integer id;

    private String title;

    private String author;

    private  String isbn;

    private String description;

    private Integer stock;

    private Integer price;
}
