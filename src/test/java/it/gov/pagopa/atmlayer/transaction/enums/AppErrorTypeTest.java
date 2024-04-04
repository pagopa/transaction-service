package it.gov.pagopa.atmlayer.transaction.enums;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class AppErrorTypeTest {

    @Test
    void testEnumContainsExpectedValues() {
        // Check that each expected value is present in the enum
        assertTrue(contains("GENERIC"));
        assertTrue(contains("TRANSACTION_ID_NOT_FOUND"));
        assertTrue(contains("CONSTRAINT_VIOLATION"));
        assertTrue(contains("BLANK_FIELDS"));
        assertTrue(contains("NULL_FIELDS"));
        assertTrue(contains("INCORRECT_INPUT"));
    }

    private boolean contains(String name) {
        for (AppErrorType type : AppErrorType.values()) {
            if (type.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
