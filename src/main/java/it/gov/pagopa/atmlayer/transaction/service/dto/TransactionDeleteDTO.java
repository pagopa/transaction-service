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
public class TransactionDeleteDTO {

    @NotBlank
    private String transactionId;

}
