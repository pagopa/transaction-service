package org.ntt.enums;

import lombok.Getter;

@Getter
public enum AppErrorType {
    GENERIC,
    TRANSACTION_ID_NOT_FOUND,
    CONSTRAINT_VIOLATION
}
