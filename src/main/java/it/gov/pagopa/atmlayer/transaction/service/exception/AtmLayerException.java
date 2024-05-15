package it.gov.pagopa.atmlayer.transaction.service.exception;

import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorCodeEnum;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.Getter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorCodeEnum.ATMLTS_500;

@Getter
public class AtmLayerException extends WebApplicationException {

    @Schema(example = "Validation Error")
    private final String type;

    @Schema(example = "500")
    private final int statusCode;

    private final String message;

    private String errorCode;

    @Builder
    public AtmLayerException(Throwable error) {
        super(error);
        this.message = error.getMessage();
        this.type = ATMLTS_500.getType().name();
        this.statusCode = 500;
        this.errorCode = ATMLTS_500.getErrorCode();
    }

    @Builder
    public AtmLayerException(Response.Status statusCode, AppErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorMessage(), statusCode);
        this.message = errorCodeEnum.getErrorMessage();
        this.type = errorCodeEnum.getType().name();
        this.statusCode = statusCode.getStatusCode();
        this.errorCode = errorCodeEnum.getErrorCode();
    }

}
