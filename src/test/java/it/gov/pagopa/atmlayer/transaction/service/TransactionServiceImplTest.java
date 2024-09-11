package it.gov.pagopa.atmlayer.transaction.service;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.repository.TransactionRepository;
import it.gov.pagopa.atmlayer.transaction.service.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testInsertTransactionEntityExceptionCase() {
        String transactionId = "transactionId";
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionId);

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().item(new TransactionEntity()));

        transactionService.insertTransactionEntity(transactionEntity)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailed()
                .assertFailedWith(AtmLayerException.class, "A transaction having the same transaction id is already present in the database");

        verify(transactionRepository, never()).persist(any(TransactionEntity.class));
    }


    @Test
    void testInsertTransactionEntity() {
        String transactionId = "transactionId";
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionId);

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().nullItem());
        when(transactionRepository.persist(any(TransactionEntity.class))).thenReturn(Uni.createFrom().item(transactionEntity));

        transactionService.insertTransactionEntity(transactionEntity)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();

        verify(transactionRepository).persist(transactionEntity);
    }

    @Test
    void testUpdate() {
        TransactionUpdateDTO dto = new TransactionUpdateDTO();
        dto.setTransactionId("transactionId");
        dto.setTransactionStatus("newStatus");
        dto.setFunctionType("newFunction");

        TransactionEntity existingTransaction = new TransactionEntity();
        existingTransaction.setTransactionId(dto.getTransactionId());

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().item(existingTransaction));
        when(transactionRepository.persist(any(TransactionEntity.class))).thenReturn(Uni.createFrom().item(existingTransaction));

        transactionService.updateTransactionEntity(dto)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(existingTransaction);

        verify(transactionRepository).persist(existingTransaction);
    }

    @Test
    void testUpdateTransactionEntitySuccessPartialStatusOnly() {
        TransactionUpdateDTO dto = new TransactionUpdateDTO();
        dto.setTransactionId("transactionId");
        dto.setTransactionStatus("newStatus");
        dto.setFunctionType("");

        TransactionEntity existingTransaction = new TransactionEntity();
        existingTransaction.setTransactionId(dto.getTransactionId());

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().item(existingTransaction));
        when(transactionRepository.persist(any(TransactionEntity.class))).thenReturn(Uni.createFrom().item(existingTransaction));

        transactionService.updateTransactionEntity(dto)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(existingTransaction);

        verify(transactionRepository).persist(existingTransaction);
    }

    @Test
    void testUpdateTransactionEntitySuccessPartialFunctionTypeOnly() {
        TransactionUpdateDTO dto = new TransactionUpdateDTO();
        dto.setTransactionId("transactionId");
        dto.setTransactionStatus("");
        dto.setFunctionType("newFunction");

        TransactionEntity existingTransaction = new TransactionEntity();
        existingTransaction.setTransactionId(dto.getTransactionId());

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().item(existingTransaction));
        when(transactionRepository.persist(any(TransactionEntity.class))).thenReturn(Uni.createFrom().item(existingTransaction));

        transactionService.updateTransactionEntity(dto)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(existingTransaction);

        verify(transactionRepository).persist(existingTransaction);
    }

    @Test
    void testUpdateTransactionEntityErrorAllFieldsBlank() {
        TransactionUpdateDTO dto = new TransactionUpdateDTO();
        dto.setTransactionId("transactionId");
        dto.setTransactionStatus("");
        dto.setFunctionType("");

        when(transactionRepository.findById(any(String.class))).thenReturn(Uni.createFrom().item(new TransactionEntity()));

        transactionService.updateTransactionEntity(dto)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailed()
                .assertFailedWith(AtmLayerException.class, "All the fields that can be updated are blank");

        verify(transactionRepository, never()).persist(any(TransactionEntity.class));
    }

    @Test
    void testDeleteTransactionsOK() {
        String transactionId = "transactionId";
        TransactionEntity transaction = new TransactionEntity();

        when(transactionRepository.findById(transactionId)).thenReturn(Uni.createFrom().item(transaction));
        when(transactionRepository.deleteById(transactionId)).thenReturn(Uni.createFrom().item(true));

        transactionService.deleteTransactions(transactionId)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted().assertItem(true);

        verify(transactionRepository, times(1)).findById(transactionId);
        verify(transactionRepository, times(1)).deleteById(transactionId);
    }


    @Test
    void testFindById() {
        String transactionId = "existentId";
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionId);

        when(transactionRepository.findById(transactionId)).thenReturn(Uni.createFrom().item(transactionEntity));

        transactionService.findById(transactionId)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(transactionEntity);

        verify(transactionRepository).findById(transactionId);
    }

    @Test
    void testFindByIdExceptionCase() {
        String transactionId = "nonExistentId";

        when(transactionRepository.findById(transactionId)).thenReturn(Uni.createFrom().nullItem());

        transactionService.findById(transactionId)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailed()
                .assertFailedWith(AtmLayerException.class, "There is no such transaction id in database");
    }


    @Test
    void testSearchTransactions() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionsList.add(transactionEntity);
        int pageIndex = 0;
        int pageSize = 10;
        String transactionId = "transactionId";
        String functionType = "functionType";
        String acquirerId = "acquirerId";
        String branchId = "branchId";
        String terminalId = "terminalId";
        String transacionStatus = "transacionStatus";
        Timestamp startTime = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2024-12-31 23:59:59");

        PageInfo<TransactionEntity> expectedResult = new PageInfo<>(0, 10, 1, 1, transactionsList);

        when(transactionRepository.findByFilters(anyMap(), eq(pageIndex), eq(pageSize))).thenReturn(Uni.createFrom().item(expectedResult));

        Uni<PageInfo<TransactionEntity>> result = transactionService.searchTransactions(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transacionStatus, startTime, endTime);

        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(expectedResult);
    }

    /*@Test
    void testGetAllTransactions() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionsList.add(transactionEntity);

        PanacheQuery<TransactionEntity> panacheQuery = mock(PanacheQuery.class);

        when(transactionRepository.findAll()).thenReturn(panacheQuery);
        when(panacheQuery.list()).thenReturn(Uni.createFrom().item(transactionsList));

        Uni<List<TransactionEntity>> result = transactionService.getAllTransactions();

        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(transactionsList);
    }*/

    @Test
    void testScheduledDeleteWithTransactions() {
        List<TransactionEntity> mockTransactions = List.of(new TransactionEntity(), new TransactionEntity());

        when(transactionRepository.findOldTransactions()).thenReturn(Uni.createFrom().item(mockTransactions));
        when(transactionRepository.delete(any())).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = transactionService.scheduledDelete();

        result.subscribe().with(item -> {
            System.out.println("Completed with transactions to delete");
        }, failure -> {
            throw new AssertionError("Test failed with exception", failure);
        });

        verify(transactionRepository, times(mockTransactions.size())).delete(any());
    }

    @Test
    void testScheduledDeleteWithNoTransactions() {
        when(transactionRepository.findOldTransactions()).thenReturn(Uni.createFrom().item(Collections.emptyList()));

        Uni<Void> result = transactionService.scheduledDelete();
        result.subscribe().with(item -> {
            System.out.println("Completed with no transactions to delete");
        }, failure -> {
            throw new AssertionError("Test failed with exception", failure);
        });

        verify(transactionRepository, never()).delete(any());
    }

}
