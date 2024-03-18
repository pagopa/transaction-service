package it.gov.pagopa.atmlayer.transaction.service.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionEntityDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.mapper.TransactionEntityMapper;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.service.TransactionEntityService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;

import java.util.List;

@ApplicationScoped
@Path("/transactions")
@Tag(name = "Transactions")
@Slf4j
public class TransactionEntityResource {

    @Inject
    TransactionEntityService transactionEntityService;

    @Inject
    TransactionEntityMapper transactionEntityMapper;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionEntityDTO> insert(@RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO) {
        TransactionEntity transactionEntity = transactionEntityMapper.toEntityInsertion(transactionInsertionDTO);
        return this.transactionEntityService.insertTransactionEntity(transactionEntity)
                .onItem()
                .transformToUni(insertedTransaction -> Uni.createFrom().item(this.transactionEntityMapper.toDTO(insertedTransaction)));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionEntityDTO> update(@RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO) {
        return transactionEntityService.updateTransactionEntity(transactionUpdateDTO)
                .onItem()
                .transformToUni(updatedTransaction -> Uni.createFrom().item(transactionEntityMapper.toDTO(updatedTransaction)));
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PageInfo<TransactionEntityDTO>> search(@QueryParam("pageIndex") @DefaultValue("0")
                                                      @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                      @QueryParam("pageSize") @DefaultValue("10")
                                                      @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                      @QueryParam("transactionId") String transactionId,
                                                      @QueryParam("functionType") String functionType,
                                                      @QueryParam("acquirerId") String acquirerId,
                                                      @QueryParam("branchId") String branchId,
                                                      @QueryParam("terminalId") String terminalId,
                                                      @QueryParam("transactionStatus") String transactionStatus) {
        return this.transactionEntityService.searchTransactions(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Transaction entity meets the applied filters");
                    }
                    return transactionEntityMapper.toFrontEndDtoPaged(pagedList);
                }));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<TransactionEntityDTO>> getAll() {
        return this.transactionEntityService.getAllTransactions()
                .onItem()
                .transform(Unchecked.function(list -> {
                    if (list.isEmpty()) {
                        log.info("There is not any transaction saved in database");
                    }
                    return transactionEntityMapper.toDTOList(list);
                }));
    }

}
