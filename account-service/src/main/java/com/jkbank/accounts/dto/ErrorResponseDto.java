package com.jkbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            name = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            name = "Error code returned by the API"
    )
    private HttpStatus errorCode;

    @Schema(
            name = "Error message returned by the API"
    )
    private String errorMessage;

    @Schema(
            name = "Time when the error occurred"
    )
    private LocalDateTime errorTime;
}
// validations not required since client is not sending this data to server