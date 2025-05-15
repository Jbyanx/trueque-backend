package com.ingsoft.trueque.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ResetPasswordRequest(
        @NotBlank String newPassword
) implements Serializable {
}
