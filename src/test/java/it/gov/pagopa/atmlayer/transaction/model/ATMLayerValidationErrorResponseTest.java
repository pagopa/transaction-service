package it.gov.pagopa.atmlayer.transaction.model;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.model.ATMLayerValidationErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ATMLayerValidationErrorResponseTest {

    @Test
    void testBuilderAndGetters() {

        ATMLayerValidationErrorResponse response = ATMLayerValidationErrorResponse.builder()
                .type("type")
                .errorCode("errorCode")
                .statusCode(404)
                .message("message")
                .errors(Arrays.asList("error1", "error2"))
                .build();

        assertEquals("type", response.getType());
        assertEquals("errorCode", response.getErrorCode());
        assertEquals(404, response.getStatusCode());
        assertEquals("message", response.getMessage());
        assertEquals(Arrays.asList("error1", "error2"), response.getErrors());

    }
}
