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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
    @Operation(
            operationId = "insert",
            description = "Inserimento transazione"
    )
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLM_500\"}"))
    public Uni<TransactionDTO> insert(@RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO) {
        log.info("Insert transaction: {}", transactionInsertionDTO);
        TransactionEntity transactionEntity = transactionMapper.toEntityInsertion(transactionInsertionDTO);
        return this.transactionService.insertTransactionEntity(transactionEntity)
                .onItem()
                .transformToUni(insertedTransaction -> Uni.createFrom().item(this.transactionMapper.toDTO(insertedTransaction)));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "update",
            description = "Aggiorna transazione"
    )
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLM_500\"}"))
    public Uni<TransactionDTO> update(@RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO) {
        log.info("Update transaction: {}", transactionUpdateDTO);
        return transactionService.updateTransactionEntity(transactionUpdateDTO)
                .onItem()
                .transformToUni(updatedTransaction -> Uni.createFrom().item(transactionMapper.toDTO(updatedTransaction)));
    }

    @DELETE
    @Path("/delete/{transactionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "delete",
            description = "Elimina transazione"
    )
    @APIResponse(responseCode = "204", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLM_500\"}"))
    public Uni<Void> delete(@PathParam("transactionId") @Schema(format = "byte", maxLength = 255) String transactionId) {
        return this.transactionService.deleteTransactions(transactionId)
                .onItem()
                .ignore()
                .andSwitchTo(Uni.createFrom().voidItem());
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "search",
            description = "Ricerca transazioni mettendo dei filtri"
    )
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLM_500\"}"))
    public Uni<PageInfo<TransactionDTO>> search(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "10000")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "100")) int pageSize,
                                                @QueryParam("transactionId") @Schema(format = "byte", maxLength = 255) String transactionId,
                                                @QueryParam("functionType") @Schema(format = "byte", maxLength = 255) String functionType,
                                                @QueryParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId,
                                                @QueryParam("branchId") @Schema(format = "byte", maxLength = 255) String branchId,
                                                @QueryParam("terminalId") @Schema(format = "byte", maxLength = 255) String terminalId,
                                                @QueryParam("transactionStatus") @Schema(format = "byte", maxLength = 255) String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "{\"Timestamp\":\"2023-07-15 15:00:00\"}") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "{\"Timestamp\":\"2023-07-15 15:00:00\"}") Timestamp endTime) {
        return this.transactionService.searchTransactions(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Transaction entity meets the applied filters");
                    }
                    return transactionMapper.toDtoPaged(pagedList);
                }));
    }

   /* @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getAll",
            description = "Recupera tutte le transazioni che si trovano sul database"
    )
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLM_500\"}"))
    public Uni<List<TransactionDTO>> getAll() {
        return this.transactionService.getAllTransactions()
                .onItem()
                .transform(Unchecked.function(list -> {
                    if (list.isEmpty()) {
                        log.info("There is not any transaction saved in database!");
                    }
                    return transactionMapper.toDTOList(list);
                }));
    }*/

}
