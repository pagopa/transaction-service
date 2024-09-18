package it.gov.pagopa.atmlayer.transaction.service.service.impl;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.transaction.service.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.repository.TransactionRepository;
import it.gov.pagopa.atmlayer.transaction.service.service.TransactionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Inject
    TransactionRepository transactionRepository;


    @Override
    @WithTransaction
    public Uni<TransactionEntity> insertTransactionEntity(TransactionEntity transactionEntity) {
        String transactionId = transactionEntity.getTransactionId();
        return this.transactionRepository.findById(transactionEntity.getTransactionId())
                .onItem()
                .transformToUni(Unchecked.function(transaction -> {
                    if (transaction != null) {
                        throw new AtmLayerException(Response.Status.BAD_REQUEST, AppErrorCodeEnum.TRANSACTION_ID_ALREADY_EXISTS);
                    }
                    return transactionRepository.persist(transactionEntity);
                }));
    }

    @Override
    @WithTransaction
    public Uni<TransactionEntity> updateTransactionEntity(TransactionUpdateDTO transactionUpdateDTO) {
        String transactionId = transactionUpdateDTO.getTransactionId();
        return this.findById(transactionUpdateDTO.getTransactionId())
                .onItem()
                .transformToUni(Unchecked.function(transactionFound -> {
                    if (transactionUpdateDTO.getTransactionStatus().isBlank() && transactionUpdateDTO.getFunctionType().isBlank()) {
                        throw new AtmLayerException(Response.Status.BAD_REQUEST, AppErrorCodeEnum.ALL_FIELDS_ARE_BLANK);
                    } else if (transactionUpdateDTO.getTransactionStatus().isBlank()) {
                        transactionFound.setFunctionType(transactionUpdateDTO.getFunctionType());
                    } else if (transactionUpdateDTO.getFunctionType().isBlank()) {
                        transactionFound.setTransactionStatus(transactionUpdateDTO.getTransactionStatus());
                    } else {
                        transactionFound.setTransactionStatus(transactionUpdateDTO.getTransactionStatus());
                        transactionFound.setFunctionType(transactionUpdateDTO.getFunctionType());
                    }
                    transactionFound.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    return transactionRepository.persist(transactionFound);
                }));
    }

    @Override
    @WithTransaction
    public Uni<Boolean> deleteTransactions(String transactionId) {
        return this.findById(transactionId)
                .onItem()
                .transformToUni(x -> this.transactionRepository.deleteById(transactionId));
    }

    @Override
    @WithSession
    public Uni<PageInfo<TransactionEntity>> searchTransactions(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transacionStatus, Timestamp startTime, Timestamp endTime) {
        if (startTime != null && endTime!= null && startTime.after(endTime)) {
            throw new AtmLayerException(Response.Status.BAD_REQUEST, AppErrorCodeEnum.STARTTIME_CANNOT_BE_GREATER_THAN_ENDTIME);
        }
        Map<String, Object> filters = new HashMap<>();
        filters.put("transactionId", transactionId);
        filters.put("functionType", functionType);
        filters.put("acquirerId", acquirerId);
        filters.put("branchId", branchId);
        filters.put("terminalId", terminalId);
        filters.put("transactionStatus", transacionStatus);
        filters.put("startTime", startTime);
        filters.put("endTime", endTime);
        filters.remove(null);
        filters.values().removeAll(Collections.singleton(null));
        filters.values().removeAll(Collections.singleton(""));
        return transactionRepository.findByFilters(filters, pageIndex, pageSize);
    }

   /* @Override
    @WithSession
    public Uni<List<TransactionEntity>> getAllTransactions() {
        return this.transactionRepository.findAll().list();
    }*/

    @Override
    @WithSession
    public Uni<TransactionEntity> findById(String transactionId) {
        return this.transactionRepository.findById(transactionId)
                .onItem()
                .ifNull()
                .switchTo(() -> {
                    throw new AtmLayerException(Response.Status.NOT_FOUND, AppErrorCodeEnum.TRANSACTION_NOT_FOUND);
                })
                .onItem()
                .transformToUni(Unchecked.function(x -> Uni.createFrom().item(x)));
    }

    @Scheduled(every = "{transaction.cleanup.schedule}")
    @WithTransaction
    public Uni<Void> scheduledDelete() {
        return transactionRepository.findOldTransactions()
                .onItem().transformToUni(entities -> {
                    if (entities.isEmpty()) {
                        return Uni.createFrom().voidItem();
                    } else {
                        return Uni.combine().all().unis(
                                entities.stream()
                                        .map(entity -> transactionRepository.delete(entity))
                                        .toList()
                        ).discardItems();
                    }
                }).replaceWith(Uni.createFrom().voidItem());
    }

}