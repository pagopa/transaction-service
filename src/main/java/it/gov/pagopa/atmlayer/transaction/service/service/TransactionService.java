package it.gov.pagopa.atmlayer.transaction.service.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDeleteDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;

import java.sql.Timestamp;
import java.util.List;


public interface TransactionService {

    Uni<TransactionEntity> insertTransactionEntity(TransactionEntity transactionEntity);

    Uni<TransactionEntity> updateTransactionEntity(TransactionUpdateDTO transactionUpdateDTO);

    Uni<Boolean> deleteTransactions(TransactionDeleteDTO transactionDeleteDTO);

    Uni<PageInfo<TransactionEntity>> searchTransactions(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transacionStatus, Timestamp startTime, Timestamp endTime);

    Uni<List<TransactionEntity>> getAllTransactions();

    Uni<TransactionEntity> findById(String transactionId);

}
