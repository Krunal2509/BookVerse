package com.service.bookverse.feature.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequestDto
{
    @NotBlank(message = "Title is required")
    private  String  title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Isbn is required")
    private String isbn;

    private String description;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Integer price;

}
