package org.example.dtos.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponseDto {
    private final String message;
    private final int status;
    private final String error;
}
