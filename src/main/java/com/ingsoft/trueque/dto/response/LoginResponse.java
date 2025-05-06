package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record LoginResponse(
        String jwt
) implements Serializable {
}
