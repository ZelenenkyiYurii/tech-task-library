package com.ns.techtask.dto.response;

import java.io.Serializable;

/**
 * DTO for {@link com.ns.techtask.model.Book}
 */
public record BookDto(Long id, String title, String author, Integer amount) implements Serializable {
}