package it.gov.pagopa.atmlayer.transaction.repository;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.repository.TransactionRepository;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TransactionRepositoryTest {
    @InjectMock
    TransactionRepository transactionRepository;

    @Test
    void testFindByFilters() {
    }

}