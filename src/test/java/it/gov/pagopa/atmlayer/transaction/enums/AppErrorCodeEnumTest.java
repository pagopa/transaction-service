package it.gov.pagopa.atmlayer.transaction.enums;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AppErrorCodeEnumTest {

    @Test
    void testEnumInitializationAndAccessors() {

        AppErrorCodeEnum error500 = AppErrorCodeEnum.ATMLTS_500;
        assertEquals("ATMLTS_500", error500.getErrorCode());
        assertEquals("An unexpected error has occurred, see logs for more info", error500.getErrorMessage());
        assertEquals(AppErrorType.GENERIC, error500.getType());

        AppErrorCodeEnum transactionNotFound = AppErrorCodeEnum.TRANSACTION_NOT_FOUND;
        assertEquals("ATMLTS_404", transactionNotFound.getErrorCode());
        assertEquals("There is no such transaction id in database", transactionNotFound.getErrorMessage());
        assertEquals(AppErrorType.TRANSACTION_ID_NOT_FOUND, transactionNotFound.getType());

        AppErrorCodeEnum startTimeGreaterThanEndTime = AppErrorCodeEnum.STARTTIME_CANNOT_BE_GREATER_THAN_ENDTIME;
        assertEquals("ATMLTS_1000004", startTimeGreaterThanEndTime.getErrorCode());
        assertEquals("startTime should come before endTime", startTimeGreaterThanEndTime.getErrorMessage());
        assertEquals(AppErrorType.INCORRECT_INPUT, startTimeGreaterThanEndTime.getType());

        AppErrorCodeEnum allFieldsBlank = AppErrorCodeEnum.ALL_FIELDS_ARE_BLANK;
        assertEquals("ATMLTS_1000002", allFieldsBlank.getErrorCode());
        assertEquals("All the fields that can be updated are blank", allFieldsBlank.getErrorMessage());
        assertEquals(AppErrorType.BLANK_FIELDS, allFieldsBlank.getType());

        AppErrorCodeEnum transactionIdAlreadyExists = AppErrorCodeEnum.TRANSACTION_ID_ALREADY_EXISTS;
        assertEquals("ATMLTS_1000001", transactionIdAlreadyExists.getErrorCode());
        assertEquals("A transaction having the same transaction id is already present in the database", transactionIdAlreadyExists.getErrorMessage());
        assertEquals(AppErrorType.CONSTRAINT_VIOLATION, transactionIdAlreadyExists.getType());
    }
}
