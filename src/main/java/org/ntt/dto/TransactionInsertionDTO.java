package org.ntt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionInsertionDTO {
    private String transactionId;
    private String functionType;
    private String acquirerId;
    private String branchId;
    private String terminalId;
    private String transactionStatus;
}
