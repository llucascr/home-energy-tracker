package com.llucascr.user_service.exception;

import java.time.Instant;

public record CustomErrorResponse(
        int code,
        String message,
        Instant timestamp,
        String path
) {
}
