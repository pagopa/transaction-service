package org.ntt.enums;

import lombok.Getter;

import static org.ntt.enums.AppErrorType.*;

@Getter
public enum AppErrorCodeEnum {

    ATMLTS_500("ATMLTS_500", "An unexpected error has occurred, see logs for more info", GENERIC),
    TRANSACTION_NOT_FOUND("ATMLTS_404","There is no such transaction id in database",TRANSACTION_ID_NOT_FOUND),
    TRANSACTION_ID_ALREADY_EXISTS("ATMLTS_1000001", "A transaction having the same transaction id is already present in the database", CONSTRAINT_VIOLATION);

    private final String errorCode;
    private final String errorMessage;
    private final AppErrorType type;

    AppErrorCodeEnum(String errorCode, String errorMessage, AppErrorType type) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.type = type;
    }
}
