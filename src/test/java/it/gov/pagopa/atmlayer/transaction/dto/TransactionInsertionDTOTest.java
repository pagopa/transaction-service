package it.gov.pagopa.atmlayer.transaction.dto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TransactionInsertionDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorAndGetters() {
        TransactionInsertionDTO dto = new TransactionInsertionDTO();
        assertNotNull(dto);

        assertNull(dto.getTransactionId());
        assertNull(dto.getFunctionType());
        assertNull(dto.getAcquirerId());
        assertNull(dto.getBranchId());
        assertNull(dto.getTerminalId());
        assertNull(dto.getTransactionStatus());
    }

    @Test
    void testSetterAndGetters() {
        TransactionInsertionDTO dto = new TransactionInsertionDTO();

        String transactionId = "123";
        String functionType = "type";
        String acquirerId = "acquirer";
        String branchId = "branch";
        String terminalId = "terminal";
        String transactionStatus = "status";

        dto.setTransactionId(transactionId);
        dto.setFunctionType(functionType);
        dto.setAcquirerId(acquirerId);
        dto.setBranchId(branchId);
        dto.setTerminalId(terminalId);
        dto.setTransactionStatus(transactionStatus);

        assertEquals(transactionId, dto.getTransactionId());
        assertEquals(functionType, dto.getFunctionType());
        assertEquals(acquirerId, dto.getAcquirerId());
        assertEquals(branchId, dto.getBranchId());
        assertEquals(terminalId, dto.getTerminalId());
        assertEquals(transactionStatus, dto.getTransactionStatus());
    }

    @Test
    void testBuilder() {
        String transactionId = "123";
        String functionType = "type";
        String acquirerId = "acquirer";
        String branchId = "branch";
        String terminalId = "terminal";
        String transactionStatus = "status";

        TransactionInsertionDTO dto = TransactionInsertionDTO.builder()
                .transactionId(transactionId)
                .functionType(functionType)
                .acquirerId(acquirerId)
                .branchId(branchId)
                .terminalId(terminalId)
                .transactionStatus(transactionStatus)
                .build();

        assertNotNull(dto);
        assertEquals(transactionId, dto.getTransactionId());
        assertEquals(functionType, dto.getFunctionType());
        assertEquals(acquirerId, dto.getAcquirerId());
        assertEquals(branchId, dto.getBranchId());
        assertEquals(terminalId, dto.getTerminalId());
        assertEquals(transactionStatus, dto.getTransactionStatus());
    }

    @Test
    void testToString() {
        TransactionInsertionDTO dto = TransactionInsertionDTO.builder()
                .transactionId("123")
                .functionType("type")
                .acquirerId("acquirer")
                .branchId("branch")
                .terminalId("terminal")
                .transactionStatus("status")
                .build();

        String expectedToString = "TransactionInsertionDTO(transactionId=123, functionType=type, acquirerId=acquirer, branchId=branch, terminalId=terminal, transactionStatus=status)";
        assertEquals(expectedToString, dto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        TransactionInsertionDTO dto1 = TransactionInsertionDTO.builder()
                .transactionId("123")
                .functionType("type")
                .acquirerId("acquirer")
                .branchId("branch")
                .terminalId("terminal")
                .transactionStatus("status")
                .build();

        TransactionInsertionDTO dto2 = TransactionInsertionDTO.builder()
                .transactionId("123")
                .functionType("type")
                .acquirerId("acquirer")
                .branchId("branch")
                .terminalId("terminal")
                .transactionStatus("status")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testValidation() {
        TransactionInsertionDTO dto = new TransactionInsertionDTO();
        dto.setTransactionId("");
        dto.setFunctionType("");
        dto.setAcquirerId("");
        dto.setBranchId("");
        dto.setTerminalId("");
        dto.setTransactionStatus("");

        var violations = validator.validate(dto);
        assertEquals(6, violations.size());
    }
}
