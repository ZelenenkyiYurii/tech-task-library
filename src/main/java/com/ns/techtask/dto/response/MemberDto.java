package com.ns.techtask.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.ns.techtask.model.Member}
 */
public record MemberDto(Long id, String name, LocalDate membershipDate) implements Serializable {
}