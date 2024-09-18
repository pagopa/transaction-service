package it.gov.pagopa.atmlayer.transaction.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.transaction.service.dto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.transaction.service.entity.TransactionEntity;
import it.gov.pagopa.atmlayer.transaction.service.mapper.TransactionMapper;
import it.gov.pagopa.atmlayer.transaction.service.model.PageInfo;
import it.gov.pagopa.atmlayer.transaction.service.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class TransactionResourceTest {

    @InjectMock
    TransactionService transactionService;

    @InjectMock
    TransactionMapper transactionMapper;

    @Test
    void testInsert() {
        TransactionEntity transactionEntity = new TransactionEntity();
        TransactionDTO transactionDTO = new TransactionDTO();
        String myJson = """
                {
                    "transactionId": "1",
                    "functionType": "menu",
                    "acquirerId": "111",
                    "branchId": "222",
                    "terminalId": "3",
                    "transactionStatus": "created"
                }
                """;


        when(transactionMapper.toEntityInsertion(any(TransactionInsertionDTO.class))).thenReturn(transactionEntity);
        when(transactionService.insertTransactionEntity(any(TransactionEntity.class))).thenReturn(Uni.createFrom().item(transactionEntity));
        when(transactionMapper.toDTO(transactionEntity)).thenReturn(transactionDTO);

        TransactionDTO result = given()
                .contentType(ContentType.JSON)
                .body(myJson)
                .when()
                .post("/api/v1/transaction-service/transactions/insert")
                .then()
                .statusCode(200)
                .extract()
                .as(TransactionDTO.class);

        assertEquals(transactionDTO, result);
    }

    @Test
    void testUpdate() {
        TransactionEntity transactionEntity = new TransactionEntity();
        TransactionDTO transactionDTO = new TransactionDTO();
        TransactionUpdateDTO transactionUpdateDTO = new TransactionUpdateDTO();
        transactionUpdateDTO.setTransactionId("1");
        transactionUpdateDTO.setTransactionStatus("created");
        transactionUpdateDTO.setFunctionType("menu");

        when(transactionService.updateTransactionEntity(any(TransactionUpdateDTO.class))).thenReturn(Uni.createFrom().item(transactionEntity));
        when(transactionMapper.toDTO(any(TransactionEntity.class))).thenReturn(transactionDTO);

        TransactionDTO result = given()
                .contentType(ContentType.JSON)
                .body(transactionUpdateDTO)
                .when()
                .put("/api/v1/transaction-service/transactions/update")
                .then()
                .statusCode(200)
                .extract()
                .as(TransactionDTO.class);

        assertEquals(transactionDTO, result);
    }

    @Test
    void testDelete() {
        String transactionId = "1";
        when(transactionService.deleteTransactions(anyString())).thenReturn(Uni.createFrom().item(true));
        given()
                .pathParam("transactionId", transactionId)
                .when()
                .delete("/api/v1/transaction-service/transactions/delete/{transactionId}")
                .then()
                .statusCode(204);
        verify(transactionService, times(1)).deleteTransactions(transactionId);
    }

    @Test
    void testSearch() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionsList.add(transactionEntity);
        PageInfo<TransactionEntity> pageInfoEntity = new PageInfo<>(0, 10, 1, 1, transactionsList);
        List<TransactionDTO> dtoList = new ArrayList<>();
        TransactionDTO transactionDTO = new TransactionDTO();
        dtoList.add(transactionDTO);
        PageInfo<TransactionDTO> pageInfoDTO = new PageInfo<>(0, 10, 1, 1, dtoList);

        when(transactionService.searchTransactions(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(), any())).thenReturn(Uni.createFrom().item(pageInfoEntity));
        when(transactionMapper.toDtoPaged(any(PageInfo.class))).thenReturn(pageInfoDTO);

        PageInfo<TransactionDTO> result = given()
                .when()
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .queryParam("transactionId", "transactionId")
                .queryParam("functionType", "functionType")
                .queryParam("acquirerId", "acquirerId")
                .queryParam("branchId", "branchId")
                .queryParam("terminalId", "terminalId")
                .queryParam("transactionStatus", "transactionStatus")
                .get("/api/v1/transaction-service/transactions/search")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        assertEquals(pageInfoDTO.getResults().size(), result.getResults().size());
        assertEquals(pageInfoDTO.getItemsFound(), result.getItemsFound());
        assertEquals(pageInfoDTO.getTotalPages(), result.getTotalPages());

        verify(transactionService, times(1)).searchTransactions(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(), any());
        verify(transactionMapper, times(1)).toDtoPaged(any(PageInfo.class));
    }

    @Test
    void testSearchEmptyList() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        PageInfo<TransactionEntity> pageInfoEntity = new PageInfo<>(0, 10, 0, 1, transactionsList);
        List<TransactionDTO> dtoList = new ArrayList<>();
        PageInfo<TransactionDTO> pageInfoDTO = new PageInfo<>(0, 10, 0, 1, dtoList);

        when(transactionService.searchTransactions(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(), any())).thenReturn(Uni.createFrom().item(pageInfoEntity));
        when(transactionMapper.toDtoPaged(any(PageInfo.class))).thenReturn(pageInfoDTO);

        PageInfo<TransactionDTO> result = given()
                .when()
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .queryParam("transactionId", "transactionId")
                .queryParam("functionType", "functionType")
                .queryParam("acquirerId", "acquirerId")
                .queryParam("branchId", "branchId")
                .queryParam("terminalId", "terminalId")
                .queryParam("transactionStatus", "transactionStatus")
                .get("/api/v1/transaction-service/transactions/search")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {
                });

        assertEquals(0, result.getResults().size());
        assertEquals(pageInfoDTO.getItemsFound(), result.getItemsFound());
        assertEquals(pageInfoDTO.getTotalPages(), result.getTotalPages());

        verify(transactionService, times(1)).searchTransactions(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(), any());
        verify(transactionMapper, times(1)).toDtoPaged(any(PageInfo.class));
    }

    /*@Test
    void testGetAll() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionsList.add(transactionEntity);
        List<TransactionDTO> dtoList = new ArrayList<>();
        TransactionDTO transactionDTO = new TransactionDTO();
        dtoList.add(transactionDTO);

        when(transactionService.getAllTransactions()).thenReturn(Uni.createFrom().item(transactionsList));
        when(transactionMapper.toDTOList(any(ArrayList.class))).thenReturn(dtoList);

        ArrayList result = given()
                .when()
                .get("/api/v1/transaction-service/transactions")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ArrayList.class);

        assertEquals(1, result.size());
        verify(transactionService, times(1)).getAllTransactions();
        verify(transactionMapper, times(1)).toDTOList(transactionsList);
    }*/

    /*@Test
    void testGetAllEmptyList() {
        List<TransactionEntity> transactionsList = new ArrayList<>();
        List<TransactionDTO> dtoList = new ArrayList<>();

        when(transactionService.getAllTransactions()).thenReturn(Uni.createFrom().item(transactionsList));
        when(transactionMapper.toDTOList(any(ArrayList.class))).thenReturn(dtoList);

        ArrayList result = given()
                .when()
                .get("/api/v1/transaction-service/transactions")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ArrayList.class);

        assertEquals(0, result.size());
        verify(transactionService, times(1)).getAllTransactions();
        verify(transactionMapper, times(1)).toDTOList(transactionsList);
    }
*/
}
