package it.gov.pagopa.atmlayer.transaction.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionDTO {
    @Schema(format = "byte", maxLength = 255)
    private String transactionId;
    @Schema(format = "byte", maxLength = 255)
    private String functionType;
    @Schema(format = "byte", maxLength = 255)
    private String acquirerId;
    @Schema(format = "byte", maxLength = 255)
    private String branchId;
    @Schema(format = "byte", maxLength = 255)
    private String terminalId;
    @Schema(format = "byte", maxLength = 255)
    private String transactionStatus;
    @Schema(example = "{\"Timestamp\":\"2023-07-15 15:00:00\"}")
    @JsonFormat(timezone = "Europe/Rome")
    private Timestamp createdAt;
    @Schema(example = "{\"Timestamp\":\"2023-07-15 15:00:00\"}")
    @JsonFormat(timezone = "Europe/Rome")
    private Timestamp lastUpdatedAt;
}
