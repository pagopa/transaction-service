package it.gov.pagopa.atmlayer.transaction.exception;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.transaction.service.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.transaction.service.model.ATMLayerErrorResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class AtmLayerExceptionTest {

    @Test
    void testBuilderAndAllGetters() {

        String expectedErrorCode = "404";
        String expectedType = "Validation Error";
        int expectedStatusCode = 500;
        String expectedMessage = "Errore di esempio";

        ATMLayerErrorResponse response = ATMLayerErrorResponse.builder()
                .errorCode(expectedErrorCode)
                .type(expectedType)
                .statusCode(expectedStatusCode)
                .message(expectedMessage)
                .build();

        assertEquals(expectedErrorCode, response.getErrorCode(), "L'errorCode non corrisponde");
        assertEquals(expectedType, response.getType(), "Il tipo non corrisponde");
        assertEquals(expectedStatusCode, response.getStatusCode(), "Lo statusCode non corrisponde");
        assertEquals(expectedMessage, response.getMessage(), "Il messaggio non corrisponde");
    }

    @Test
    void testExceptionWithThrowable() {
        Throwable throwable = new RuntimeException("Test error");
        AtmLayerException exception = AtmLayerException.builder().error(throwable).build();

        assertNotNull(exception);
        assertEquals("Test error", exception.getMessage());
        assertEquals(AppErrorCodeEnum.ATMLTS_500.getType().name(), exception.getType());
        assertEquals(500, exception.getStatusCode());
        assertEquals(AppErrorCodeEnum.ATMLTS_500.getErrorCode(), exception.getErrorCode());
        assertEquals(throwable, exception.getCause());
    }

    @Test
    void testAtmLayerExceptionConstructor() {

        AppErrorCodeEnum errorCodeEnum = AppErrorCodeEnum.ATMLTS_500;

        AtmLayerException exception = new AtmLayerException(Response.Status.INTERNAL_SERVER_ERROR, errorCodeEnum);

        assertEquals(errorCodeEnum.getErrorMessage(), exception.getMessage());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getStatusCode());
        assertEquals(errorCodeEnum.getType().name(), exception.getType());
        assertEquals(errorCodeEnum.getErrorCode(), exception.getErrorCode());
    }
}
