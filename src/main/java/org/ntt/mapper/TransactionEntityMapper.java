package org.ntt.mapper;

import org.mapstruct.Mapper;
import org.ntt.dto.TransactionEntityDTO;
import org.ntt.dto.TransactionInsertionDTO;
import org.ntt.entity.TransactionEntity;
import org.ntt.model.PageInfo;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class TransactionEntityMapper {

    public abstract TransactionEntityDTO toDTO(TransactionEntity transactionEntity);

    public TransactionEntity toEntityInsertion(TransactionInsertionDTO transactionInsertionDTO) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionInsertionDTO.getTransactionId());
        transactionEntity.setFunctionType(transactionInsertionDTO.getFunctionType());
        transactionEntity.setAcquirerId(transactionInsertionDTO.getAcquirerId());
        transactionEntity.setBranchId(transactionInsertionDTO.getBranchId());
        transactionEntity.setTerminalId(transactionInsertionDTO.getTerminalId());
        transactionEntity.setTransactionStatus(transactionInsertionDTO.getTransactionStatus());
        return transactionEntity;
    }

    public abstract PageInfo<TransactionEntityDTO> toFrontEndDtoPaged(PageInfo<TransactionEntity> pagedTransactions);

    public List<TransactionEntityDTO> toDTOList(List<TransactionEntity> list) {
        return list.stream().map(this::toDTO).toList();
    }
}
