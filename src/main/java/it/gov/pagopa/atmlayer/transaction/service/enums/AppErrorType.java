package it.gov.pagopa.atmlayer.transaction.service.enums;

import lombok.Getter;

@Getter
public enum AppErrorType {
    GENERIC,
    TRANSACTION_ID_NOT_FOUND,
    CONSTRAINT_VIOLATION,
    BLANK_FIELDS
    }
