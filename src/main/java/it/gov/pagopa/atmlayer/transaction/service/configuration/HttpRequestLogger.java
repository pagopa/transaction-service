package it.gov.pagopa.atmlayer.transaction.service.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Provider
@Slf4j
public class HttpRequestLogger implements ContainerRequestFilter {

    public void logRequest(ContainerRequestContext requestContext) {
        log.info("====================================Request received with Method : {} and Path : {}", requestContext.getMethod(), requestContext.getUriInfo().getPath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logRequest(requestContext);
    }
}
