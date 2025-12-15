package com.jkbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold response information"
)
public class ResponseDto {

    @Schema(
            name = "Status Code of the Response",
            example = "200"
    )
    private String statusCode;

    @Schema(
            name = "Status message in the Response",
            example = "Request processed successfully"
    )
    private String statusMsg;
}
