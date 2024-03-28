package it.gov.pagopa.atmlayer.transaction.dto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionDTOTest {

    @Test
    void testConstructorAndGetters() {
        TransactionDTO transactionDTO = new TransactionDTO();
        assertNotNull(transactionDTO);

        assertNull(transactionDTO.getTransactionId());
        assertNull(transactionDTO.getFunctionType());
        assertNull(transactionDTO.getAcquirerId());
        assertNull(transactionDTO.getBranchId());
        assertNull(transactionDTO.getTerminalId());
        assertNull(transactionDTO.getTransactionStatus());
        assertNull(transactionDTO.getCreatedAt());
        assertNull(transactionDTO.getLastUpdatedAt());

        String transactionId = "123";
        String functionType = "type";
        String acquirerId = "acquirer";
        String branchId = "branch";
        String terminalId = "terminal";
        String transactionStatus = "status";

        transactionDTO = new TransactionDTO(transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, null, null);
        assertEquals(transactionId, transactionDTO.getTransactionId());
        assertEquals(functionType, transactionDTO.getFunctionType());
        assertEquals(acquirerId, transactionDTO.getAcquirerId());
        assertEquals(branchId, transactionDTO.getBranchId());
        assertEquals(terminalId, transactionDTO.getTerminalId());
        assertEquals(transactionStatus, transactionDTO.getTransactionStatus());
    }

    @Test
    void testSetterAndGetters() {
        TransactionDTO transactionDTO = new TransactionDTO();

        String transactionId = "123";
        String functionType = "type";
        String acquirerId = "acquirer";
        String branchId = "branch";
        String terminalId = "terminal";
        String transactionStatus = "status";
        transactionDTO.setTransactionId(transactionId);
        transactionDTO.setFunctionType(functionType);
        transactionDTO.setAcquirerId(acquirerId);
        transactionDTO.setBranchId(branchId);
        transactionDTO.setTerminalId(terminalId);
        transactionDTO.setTransactionStatus(transactionStatus);

        assertEquals(transactionId, transactionDTO.getTransactionId());
        assertEquals(functionType, transactionDTO.getFunctionType());
        assertEquals(acquirerId, transactionDTO.getAcquirerId());
        assertEquals(branchId, transactionDTO.getBranchId());
        assertEquals(terminalId, transactionDTO.getTerminalId());
        assertEquals(transactionStatus, transactionDTO.getTransactionStatus());
    }

    @Test
    void testBuilder() {
        String transactionId = "123";
        String functionType = "type";
        String acquirerId = "acquirer";
        String branchId = "branch";
        String terminalId = "terminal";
        String transactionStatus = "status";

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .transactionId(transactionId)
                .functionType(functionType)
                .acquirerId(acquirerId)
                .branchId(branchId)
                .terminalId(terminalId)
                .transactionStatus(transactionStatus)
                .build();

        assertNotNull(transactionDTO);
        assertEquals(transactionId, transactionDTO.getTransactionId());
        assertEquals(functionType, transactionDTO.getFunctionType());
        assertEquals(acquirerId, transactionDTO.getAcquirerId());
        assertEquals(branchId, transactionDTO.getBranchId());
        assertEquals(terminalId, transactionDTO.getTerminalId());
        assertEquals(transactionStatus, transactionDTO.getTransactionStatus());
    }

    @Test
    void testToString() {
        TransactionDTO transaction = TransactionDTO.builder()
                .transactionId("1")
                .functionType("type1")
                .acquirerId("acquirer1")
                .branchId("branch1")
                .terminalId("terminal1")
                .transactionStatus("status1")
                .createdAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .lastUpdatedAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .build();
        String expectedToString = "TransactionDTO(transactionId=1, functionType=type1, acquirerId=acquirer1, branchId=branch1, terminalId=terminal1, transactionStatus=status1, createdAt=2023-11-03 14:18:36.635, lastUpdatedAt=2023-11-03 14:18:36.635)";
        assertEquals(expectedToString, transaction.toString());
    }


    @Test
    void testEqualsAndHashCode() {
        TransactionDTO transaction1 = TransactionDTO.builder()
                .transactionId("1")
                .functionType("type1")
                .acquirerId("acquirer1")
                .branchId("branch1")
                .terminalId("terminal1")
                .transactionStatus("status1")
                .createdAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .lastUpdatedAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .build();

        TransactionDTO transaction2 = TransactionDTO.builder()
                .transactionId("1")
                .functionType("type1")
                .acquirerId("acquirer1")
                .branchId("branch1")
                .terminalId("terminal1")
                .transactionStatus("status1")
                .createdAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .lastUpdatedAt(Timestamp.valueOf("2023-11-03 14:18:36.635"))
                .build();

        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

}
