package com.ns.techtask.dto.request;

import com.ns.techtask.model.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Book}
 */
public record BookUpdateDto(
        @NotBlank
        @Size(min = 3, message = "Title must be at least 3 characters long  ")
        @Pattern(
                regexp = "^[A-Z][\\s\\w]*",
                message = "Name must start with a capital letter"
        )
        String title,
        @NotBlank
        @Pattern(
                regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$",
                message = "Author must contain two capitalized words with a space between them"
        )
        String author,
        @Min(value = 0,message = "Amount only > 0")
        Integer amount) implements Serializable {
}