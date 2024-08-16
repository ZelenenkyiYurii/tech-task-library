package com.ns.techtask.dto.request;

import com.ns.techtask.model.Member;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link Member}
 */
public record MemberCreateAndUpdateDto(@NotBlank String name) implements Serializable {
}