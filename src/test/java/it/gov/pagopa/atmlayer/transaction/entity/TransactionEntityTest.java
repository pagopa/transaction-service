package it.gov.pagopa.atmlayer.transaction.entity;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TransactionEntityTest {

    @Test
    void testAllArgsConstructor() {

        String transactionId = "12345";
        String functionType = "functionType";
        String acquirerId = "acquirerId";
        String branchId = "branchId";
        String terminalId = "terminalId";
        String transactionStatus = "transactionStatus";
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastUpdatedAt = new Timestamp(System.currentTimeMillis());

        TransactionEntity entity = new TransactionEntity(
                transactionId,
                functionType,
                acquirerId,
                branchId,
                terminalId,
                transactionStatus,
                createdAt,
                lastUpdatedAt
        );

        assertNotNull(entity);
        assertEquals(transactionId, entity.getTransactionId());
        assertEquals(functionType, entity.getFunctionType());
        assertEquals(acquirerId, entity.getAcquirerId());
        assertEquals(branchId, entity.getBranchId());
        assertEquals(terminalId, entity.getTerminalId());
        assertEquals(transactionStatus, entity.getTransactionStatus());
        assertEquals(createdAt, entity.getCreatedAt());
        assertEquals(lastUpdatedAt, entity.getLastUpdatedAt());
    }

    @Test
    void testTransactionIdSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String transactionId = "transactionId";
        transactionEntity.setTransactionId(transactionId);
        assertEquals(transactionId, transactionEntity.getTransactionId());
    }

    @Test
    void testFunctionTypeSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String functionType = "functionType";
        transactionEntity.setFunctionType(functionType);
        assertEquals(functionType, transactionEntity.getFunctionType());
    }

    @Test
    void testAcquirerIdSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String acquirerId = "acquirerId";
        transactionEntity.setAcquirerId(acquirerId);
        assertEquals(acquirerId, transactionEntity.getAcquirerId());
    }

    @Test
    void testBranchIdSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String branchId = "branchId";
        transactionEntity.setBranchId(branchId);
        assertEquals(branchId, transactionEntity.getBranchId());
    }

    @Test
    void testTerminalIdSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String terminalId = "terminalId";
        transactionEntity.setTerminalId(terminalId);
        assertEquals(terminalId, transactionEntity.getTerminalId());
    }

    @Test
    void testTransactionStatusSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        String transactionStatus = "transactionStatus";
        transactionEntity.setTransactionStatus(transactionStatus);
        assertEquals(transactionStatus, transactionEntity.getTransactionStatus());
    }

    @Test
    void testCreatedAtSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        Timestamp testTimestamp = new Timestamp(System.currentTimeMillis());
        transactionEntity.setCreatedAt(testTimestamp);
        Timestamp retrievedTimestamp = transactionEntity.getCreatedAt();
        assertNotNull(retrievedTimestamp);
        assertEquals(testTimestamp.getTime(), retrievedTimestamp.getTime(), 1000);
    }

    @Test
    void testLastUpdatedAtSetterGetter() {
        TransactionEntity transactionEntity = new TransactionEntity();
        Timestamp testTimestamp = new Timestamp(System.currentTimeMillis());
        transactionEntity.setLastUpdatedAt(testTimestamp);
        Timestamp retrievedTimestamp = transactionEntity.getLastUpdatedAt();
        assertNotNull(retrievedTimestamp);
        assertEquals(testTimestamp, retrievedTimestamp);
    }
}
