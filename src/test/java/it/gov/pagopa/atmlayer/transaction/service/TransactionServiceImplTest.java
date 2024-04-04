package it.gov.pagopa.atmlayer.transaction.service;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.transaction.service.repository.TransactionRepository;
import it.gov.pagopa.atmlayer.transaction.service.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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



   /* @Test
    void testGetAllTransactions() {
        List<TransactionEntity> transactionList = Arrays.asList(
                new TransactionEntity("1", "type1", "acquirer1", "branch1", "terminal1", "status1", LocalDateTime.now(), LocalDateTime.now()),
                new TransactionEntity("2", "type2", "acquirer2", "branch2", "terminal2", "status2", LocalDateTime.now(), LocalDateTime.now()),
                new TransactionEntity("3", "type3", "acquirer3", "branch3", "terminal3", "status3", LocalDateTime.now(), LocalDateTime.now())
        );

        when(transactionRepository.findAll().list()).thenReturn(Uni.createFrom().item(transactionList));

        UniAssertSubscriber<List<TransactionEntity>> subscriber = transactionService.getAllTransactions()
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber.assertCompleted();

        assertEquals(transactionList, subscriber.getItem());

        verify(transactionRepository).findAll().list();
    }*/

}
