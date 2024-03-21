package it.gov.pagopa.atmlayer.transaction.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionUpdateDTO {
    @NotBlank
    private String transactionId;
    @NotBlank
    private String transactionStatus;
    @NotBlank
    private String functionType;
}
