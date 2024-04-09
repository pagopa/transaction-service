package it.gov.pagopa.atmlayer.transaction.mapper;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.mapper.TransactionMapper;
import it.gov.pagopa.atmlayer.transaction.service.mapper.TransactionMapperImpl;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class TransactionMapperTest {

    @Inject
    TransactionMapper transactionMapper;

    @Test
    void testToEntityInsertion() {
        TransactionMapperImpl transactionMapperImpl = new TransactionMapperImpl();
        TransactionEntity actualToEntityInsertionResult = transactionMapperImpl.toEntityInsertion(new TransactionInsertionDTO("transactionId", "functionType", "acquirerId", "branchId", "terminalId", "transactionStatus"));

        assertEquals("acquirerId", actualToEntityInsertionResult.getAcquirerId());
        assertEquals("terminalId", actualToEntityInsertionResult.getTerminalId());
        assertEquals("transactionId", actualToEntityInsertionResult.getTransactionId());
        assertEquals("functionType", actualToEntityInsertionResult.getFunctionType());
        assertEquals("transactionStatus", actualToEntityInsertionResult.getTransactionStatus());
        assertEquals("branchId", actualToEntityInsertionResult.getBranchId());
    }

    @Test
    void testToDTOList() {
        TransactionMapperImpl transactionMapperImpl = new TransactionMapperImpl();

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAcquirerId("acquirerId");
        transactionEntity.setBranchId("branchId");
        transactionEntity.setCreatedAt(mock(Timestamp.class));
        transactionEntity.setFunctionType("functionType");
        transactionEntity.setLastUpdatedAt(mock(Timestamp.class));
        transactionEntity.setTerminalId("terminalId");
        transactionEntity.setTransactionId("transactionId");
        transactionEntity.setTransactionStatus("transactionStatus");

        TransactionEntity transactionEntity2 = new TransactionEntity();
        transactionEntity2.setAcquirerId("acquirerId2");
        transactionEntity2.setBranchId("branchId2");
        transactionEntity2.setCreatedAt(mock(Timestamp.class));
        transactionEntity2.setFunctionType("functionType2");
        transactionEntity2.setLastUpdatedAt(mock(Timestamp.class));
        transactionEntity2.setTerminalId("terminalId2");
        transactionEntity2.setTransactionId("transactionId2");
        transactionEntity2.setTransactionStatus("transactionStatus2");

        ArrayList<TransactionEntity> list = new ArrayList<>();
        list.add(transactionEntity2);
        list.add(transactionEntity);

        assertEquals(2, transactionMapperImpl.toDTOList(list).size());
    }

    @Test
    void testEmptyToDTOList() {
        TransactionMapperImpl transactionMapperImpl = new TransactionMapperImpl();
        assertTrue(transactionMapperImpl.toDTOList(new ArrayList<>()).isEmpty());
    }

    @Test
    void testToDtoPaged() {
        TransactionEntity transactionEntity1 = mock(TransactionEntity.class);
        when(transactionEntity1.getAcquirerId()).thenReturn("acquirerId");

        TransactionEntity transactionEntity2 = mock(TransactionEntity.class);
        when(transactionEntity2.getAcquirerId()).thenReturn("acquirerId2");

        PageInfo<TransactionEntity> mockedPageInfo = mock(PageInfo.class);
        List<TransactionEntity> resultList = new ArrayList<>();
        resultList.add(transactionEntity1);
        resultList.add(transactionEntity2);
        when(mockedPageInfo.getResults()).thenReturn(resultList);

        PageInfo<TransactionDTO> resultPageInfo = transactionMapper.toDtoPaged(mockedPageInfo);

        assertNotNull(resultPageInfo);
        assertEquals(2, resultPageInfo.getResults().size());
        assertEquals("acquirerId", resultPageInfo.getResults().get(0).getAcquirerId());
        assertEquals("acquirerId2", resultPageInfo.getResults().get(1).getAcquirerId());
    }

    @Test
    void testToDTOWithNullInput() {
        TransactionDTO result = transactionMapper.toDTO(null);
        assertNull(result);
    }

    @Test
    void testToDtoPagedWithNullInput() {
        PageInfo<TransactionDTO> resultPageInfo = transactionMapper.toDtoPaged(null);
        assertNull(resultPageInfo);
    }

}
