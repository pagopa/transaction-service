package it.gov.pagopa.atmlayer.transaction.service.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String transactionId;
    @NotBlank
    private String functionType;
    @NotBlank
    private String acquirerId;
    @NotBlank
    private String branchId;
    @NotBlank
    private String terminalId;
    @NotBlank
    private String transactionStatus;
}
