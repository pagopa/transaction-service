package org.ntt.service.impl;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.ntt.dto.TransactionUpdateDTO;
import org.ntt.entity.TransactionEntity;
import org.ntt.enums.AppErrorCodeEnum;
import org.ntt.exception.AtmLayerException;
import org.ntt.model.PageInfo;
import org.ntt.repository.TransactionEntityRepository;
import org.ntt.service.TransactionEntityService;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TransactionEntityServiceImpl implements TransactionEntityService {

    @Inject
    TransactionEntityRepository transactionEntityRepository;

    @Override
    @WithTransaction
    public Uni<TransactionEntity> insertTransactionEntity(TransactionEntity transactionEntity) {
        return this.transactionEntityRepository.findById(transactionEntity.getTransactionId())
                .onItem()
                .transformToUni(Unchecked.function(transaction -> {
                    if(transaction != null){
                        throw new AtmLayerException(Response.Status.BAD_REQUEST, AppErrorCodeEnum.TRANSACTION_ID_ALREADY_EXISTS);
                    }
                    return transactionEntityRepository.persist(transactionEntity);
                }));
    }

    @Override
    @WithTransaction
    public Uni<TransactionEntity> updateTransactionEntity(TransactionUpdateDTO transactionUpdateDTO) {
        return this.findById(transactionUpdateDTO.getTransactionId())
                .onItem()
                .transformToUni(Unchecked.function(transactionFound -> {
                    transactionFound.setTransactionStatus(transactionUpdateDTO.getTransactionStatus());
                    transactionFound.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    return transactionEntityRepository.persist(transactionFound);
                }));
    }

    @Override
    @WithSession
    public Uni<TransactionEntity> findById(String transactionId) {
        return this.transactionEntityRepository.findById(transactionId)
                .onItem()
                .ifNull()
                .switchTo(() -> {
                    throw new AtmLayerException(Response.Status.NOT_FOUND, AppErrorCodeEnum.TRANSACTION_NOT_FOUND);
                })
                .onItem()
                .transformToUni(Unchecked.function(x -> Uni.createFrom().item(x)));
    }

    @Override
    @WithSession
    public Uni<PageInfo<TransactionEntity>> searchTransactions(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transacionStatus) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("transactionId", transactionId);
        filters.put("functionType", functionType);
        filters.put("acquirerId", acquirerId);
        filters.put("branchId", branchId);
        filters.put("terminalId", terminalId);
        filters.put("transactionStatus", transacionStatus);
        filters.remove(null);
        filters.values().removeAll(Collections.singleton(null));
        filters.values().removeAll(Collections.singleton(""));
        return transactionEntityRepository.findByFilters(filters, pageIndex, pageSize);
    }

    @Override
    @WithSession
    public Uni<List<TransactionEntity>> getAllTransactions() {
        return this.transactionEntityRepository.findAll().list();
    }

}
