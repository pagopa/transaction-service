package it.gov.pagopa.atmlayer.transaction.service.enums;

import lombok.Getter;

@Getter
public enum AppErrorCodeEnum {

    ATMLTS_500("ATMLTS_500", "An unexpected error has occurred, see logs for more info", AppErrorType.GENERIC),
    TRANSACTION_NOT_FOUND("ATMLTS_404","There is no such transaction id in database", AppErrorType.TRANSACTION_ID_NOT_FOUND),
    STARTTIME_CANNOT_BE_GREATER_THAN_ENDTIME("ATMLTS_1000004", "startTime should come before endTime", AppErrorType.INCORRECT_INPUT),
    BOTH_STARTTIME_AND_ENDTIME_SHOULD_BE_PRESENT("ATMLTS_1000003", "Both startTime and endTime should be present", AppErrorType.NULL_FIELDS),
    ALL_FIELDS_ARE_BLANK("ATMLTS_1000002", "All the fields that can be updated are blank", AppErrorType.BLANK_FIELDS),
    TRANSACTION_ID_ALREADY_EXISTS("ATMLTS_1000001", "A transaction having the same transaction id is already present in the database", AppErrorType.CONSTRAINT_VIOLATION);

    private final String errorCode;
    private final String errorMessage;
    private final AppErrorType type;

    AppErrorCodeEnum(String errorCode, String errorMessage, AppErrorType type) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.type = type;
    }
}
