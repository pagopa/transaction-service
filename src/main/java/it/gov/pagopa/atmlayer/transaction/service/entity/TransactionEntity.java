package it.gov.pagopa.atmlayer.transaction.service.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_entity")
public class TransactionEntity extends PanacheEntityBase implements Serializable {

    @Column(name = "transaction_id", nullable = false, updatable = false)
    @Id
    private String transactionId;

    @Column(name = "function_type", nullable = false)
    private String functionType;

    @Column(name = "acquirer_id", nullable = false)
    private String acquirerId;

    @Column(name = "branch_id", nullable = false)
    private String branchId;

    @Column(name = "terminal_id", nullable = false)
    private String terminalId;

    @Column(name = "transaction_status", nullable = false)
    private String transactionStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

}
