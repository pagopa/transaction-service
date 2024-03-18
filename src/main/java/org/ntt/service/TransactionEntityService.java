package org.ntt.service;

import io.smallrye.mutiny.Uni;
import org.ntt.dto.TransactionUpdateDTO;
import org.ntt.entity.TransactionEntity;
import org.ntt.model.PageInfo;

import java.util.List;


public interface TransactionEntityService {

    Uni<TransactionEntity> insertTransactionEntity(TransactionEntity transactionEntity);

    Uni<TransactionEntity> updateTransactionEntity(TransactionUpdateDTO transactionUpdateDTO);

    Uni<TransactionEntity> findById(String transactionId);

    Uni<PageInfo<TransactionEntity>> searchTransactions(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transacionStatus);

    Uni<List<TransactionEntity>> getAllTransactions();
}
