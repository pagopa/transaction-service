package it.gov.pagopa.atmlayer.transaction.service.mapper;

import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class TransactionMapper {

    public abstract TransactionDTO toDTO(TransactionEntity transactionEntity);

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

    public abstract PageInfo<TransactionDTO> toFrontEndDtoPaged(PageInfo<TransactionEntity> pagedTransactions);

    public List<TransactionDTO> toDTOList(List<TransactionEntity> list) {
        return list.stream().map(this::toDTO).toList();
    }
}
