package org.ntt.entity;

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
import java.sql.Timestamp;

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

    @Column(name = "function_type")
    private String functionType;

    @Column(name = "acquirer_id")
    private String acquirerId;

    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "last_updated_at")
    @UpdateTimestamp
    private Timestamp lastUpdatedAt;

}
