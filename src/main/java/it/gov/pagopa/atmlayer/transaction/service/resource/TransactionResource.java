package it.gov.pagopa.atmlayer.transaction.service.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.mapper.TransactionMapper;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.service.TransactionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Tag(name = "Transactions")
@Slf4j
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @Inject
    TransactionMapper transactionMapper;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionDTO> insert(@RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO) {
        TransactionEntity transactionEntity = transactionMapper.toEntityInsertion(transactionInsertionDTO);
        return this.transactionService.insertTransactionEntity(transactionEntity)
                .onItem()
                .transformToUni(insertedTransaction -> Uni.createFrom().item(this.transactionMapper.toDTO(insertedTransaction)));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionDTO> update(@RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO) {
        return transactionService.updateTransactionEntity(transactionUpdateDTO)
                .onItem()
                .transformToUni(updatedTransaction -> Uni.createFrom().item(transactionMapper.toDTO(updatedTransaction)));
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PageInfo<TransactionDTO>> search(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                @QueryParam("transactionId") String transactionId,
                                                @QueryParam("functionType") String functionType,
                                                @QueryParam("acquirerId") String acquirerId,
                                                @QueryParam("branchId") String branchId,
                                                @QueryParam("terminalId") String terminalId,
                                                @QueryParam("transactionStatus") String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp endTime) {
        return this.transactionService.searchTransactions(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Transaction entity meets the applied filters");
                    }
                    return transactionMapper.toDtoPaged(pagedList);
                }));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<TransactionDTO>> getAll() {
        return this.transactionService.getAllTransactions()
                .onItem()
                .transform(Unchecked.function(list -> {
                    if (list.isEmpty()) {
                        log.info("There is not any transaction saved in database!");
                    }
                    return transactionMapper.toDTOList(list);
                }));
    }

}
