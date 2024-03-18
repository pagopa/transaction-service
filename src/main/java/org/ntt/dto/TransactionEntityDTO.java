package org.ntt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionEntityDTO {
    private String transactionId;
    private String functionType;
    private String acquirerId;
    private String branchId;
    private String terminalId;
    private String transactionStatus;
    @Schema(example = "2024-02-07T11:38:58.445+00:00")
    private Timestamp createdAt;
    @Schema(example = "2024-02-07T11:38:58.445+00:00")
    private Timestamp lastUpdatedAt;
}
