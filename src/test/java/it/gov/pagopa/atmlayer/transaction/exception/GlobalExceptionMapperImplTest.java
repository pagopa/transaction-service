package it.gov.pagopa.atmlayer.transaction.exception;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.CompositeException;
import it.gov.pagopa.atmlayer.transaction.service.exception.mapper.GlobalExceptionMapperImpl;
import it.gov.pagopa.atmlayer.transaction.service.model.ATMLayerErrorResponse;
import it.gov.pagopa.atmlayer.transaction.service.model.ATMLayerValidationErrorResponse;
import it.gov.pagopa.atmlayer.transaction.service.utils.ConstraintViolationMappingUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class GlobalExceptionMapperImplTest {

    @Mock
    Logger logger;

    @Mock
    ConstraintViolationMappingUtils constraintViolationMappingUtils;

    @InjectMocks
    GlobalExceptionMapperImpl globalExceptionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstraintViolationExceptionMapper() {
        String message = "Message";
        HashSet<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException(message, constraintViolations);
        RestResponse<ATMLayerValidationErrorResponse> response = globalExceptionMapper.constraintViolationExceptionMapper(exception);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testGenericExceptionMapper() {
        Exception exception = new RuntimeException("Test exception");
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.genericExceptionMapper(exception);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void testCompositeException() {
        Exception exception = new Exception("Test exception");
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.compositeException(new CompositeException(exception));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void testAtmLayerExceptionMapper() {
        Exception exception = new RuntimeException("Test exception");
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.genericExceptionMapper(exception);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

}
