package it.gov.pagopa.atmlayer.transaction.model;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.model.ATMLayerErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ATMLayerErrorResponseTest {

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
}
