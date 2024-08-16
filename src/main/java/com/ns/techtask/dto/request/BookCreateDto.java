package com.ns.techtask.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.io.Serializable;

/**
 * DTO for {@link com.ns.techtask.model.Book}
 */
public record BookCreateDto(@NotBlank
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
                            String author) implements Serializable {
}