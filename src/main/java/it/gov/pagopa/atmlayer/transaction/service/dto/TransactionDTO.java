package it.gov.pagopa.atmlayer.transaction.service.dto;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionDTO {
    private String transactionId;
    private String functionType;
    private String acquirerId;
    private String branchId;
    private String terminalId;
    private String transactionStatus;
    @Schema(example = "2000-04-25T15:50:50.000000")
    private LocalDateTime createdAt;
    @Schema(example = "2000-04-25T15:50:50.000000")
    private LocalDateTime lastUpdatedAt;
}
