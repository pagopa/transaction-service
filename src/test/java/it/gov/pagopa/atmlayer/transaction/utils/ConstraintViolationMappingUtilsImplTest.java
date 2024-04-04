package it.gov.pagopa.atmlayer.transaction.utils;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.transaction.service.utils.ConstraintViolationMappingUtilsImpl;
import jakarta.validation.ConstraintViolation;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ConstraintViolationMappingUtilsImplTest {

    @Test
    void testExtractErrorMessages() {
        ConstraintViolationMappingUtilsImpl constraintViolationMappingUtilsImpl = new ConstraintViolationMappingUtilsImpl();
        ConstraintViolation<?> constraintViolation = mock(ConstraintViolation.class);

        when(constraintViolation.getMessage()).thenReturn("Generic Error");
        when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createRootPath());

        HashSet<ConstraintViolation<?>> errors = new HashSet<>();
        errors.add(constraintViolation);
        List<String> actualExtractErrorMessagesResult = constraintViolationMappingUtilsImpl.extractErrorMessages(errors);

        verify(constraintViolation).getMessage();
        verify(constraintViolation).getPropertyPath();
        assertEquals(" Generic Error", actualExtractErrorMessagesResult.get(0));
        assertEquals(1, actualExtractErrorMessagesResult.size());
    }

    @Test
    void testExtractErrorMessage() {
        ConstraintViolationMappingUtilsImpl constraintViolationMappingUtilsImpl = new ConstraintViolationMappingUtilsImpl();
        PathImpl createRootPathResult = PathImpl.createRootPath();
        createRootPathResult.addPropertyNode(" ");
        ConstraintViolation<?> error = mock(ConstraintViolation.class);

        when(error.getMessage()).thenReturn("Generic Error");
        when(error.getPropertyPath()).thenReturn(createRootPathResult);

        String actualExtractErrorMessageResult = constraintViolationMappingUtilsImpl.extractErrorMessage(error);

        verify(error).getMessage();
        verify(error).getPropertyPath();
        assertEquals("  Generic Error", actualExtractErrorMessageResult);
    }

}
