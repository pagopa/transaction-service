package it.gov.pagopa.atmlayer.transaction.service.mapper;

import org.mapstruct.Mapper;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionEntityDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;

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
