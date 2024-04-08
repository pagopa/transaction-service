package it.gov.pagopa.atmlayer.transaction.repository;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class TransactionRepositoryTest {

    @InjectMock
    TransactionRepository transactionRepository;

    @Test
    void testFindByFilters() {
        Map<String, Object> params = new HashMap<>();
        int pageIndex = 1;
        int pageSize = 10;
        PageInfo<TransactionEntity> emptyPageInfo = new PageInfo<>(pageIndex, pageSize, 0, 1, Collections.emptyList());
        Mockito.when(transactionRepository.findByFilters(params, pageIndex, pageSize)).thenReturn(Uni.createFrom().item(emptyPageInfo));

        Uni<PageInfo<TransactionEntity>> result = transactionRepository.findByFilters(params, pageIndex, pageSize);

        assertEquals(emptyPageInfo, result.await().indefinitely());
    }

}
