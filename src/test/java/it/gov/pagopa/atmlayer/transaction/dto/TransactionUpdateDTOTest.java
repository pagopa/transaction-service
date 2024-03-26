package it.gov.pagopa.atmlayer.transaction.dto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionUpdateDTOTest {

    @Test
    void testBuilder() {
        String transactionId = "TX123";
        String transactionStatus = "Completed";
        String functionType = "Withdrawal";

        TransactionUpdateDTO dto = TransactionUpdateDTO.builder()
                .transactionId(transactionId)
                .transactionStatus(transactionStatus)
                .functionType(functionType)
                .build();

        assertNotNull(dto);
        assertEquals(transactionId, dto.getTransactionId());
        assertEquals(transactionStatus, dto.getTransactionStatus());
        assertEquals(functionType, dto.getFunctionType());
    }

    @Test
    void testSetterAndGetters() {
        TransactionUpdateDTO dto = new TransactionUpdateDTO();

        String transactionId = "TX456";
        String transactionStatus = "Pending";
        String functionType = "Deposit";

        dto.setTransactionId(transactionId);
        dto.setTransactionStatus(transactionStatus);
        dto.setFunctionType(functionType);

        assertEquals(transactionId, dto.getTransactionId());
        assertEquals(transactionStatus, dto.getTransactionStatus());
        assertEquals(functionType, dto.getFunctionType());
    }

    @Test
    void testToString() {
        TransactionUpdateDTO dto = TransactionUpdateDTO.builder()
                .transactionId("TX789")
                .transactionStatus("Failed")
                .functionType("Transfer")
                .build();

        String toStringResult = dto.toString();
        assertTrue(toStringResult.contains("TX789"));
        assertTrue(toStringResult.contains("Failed"));
        assertTrue(toStringResult.contains("Transfer"));
    }

    @Test
    void testEqualsAndHashCode() {
        TransactionUpdateDTO dto1 = TransactionUpdateDTO.builder()
                .transactionId("TX101")
                .transactionStatus("Processing")
                .functionType("Payment")
                .build();

        TransactionUpdateDTO dto2 = TransactionUpdateDTO.builder()
                .transactionId("TX101")
                .transactionStatus("Processing")
                .functionType("Payment")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
