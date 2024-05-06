package it.gov.pagopa.atmlayer.transaction.service.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Provider
@Slf4j
public class HttpResponseLogger implements ContainerResponseFilter {
    public void logResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        log.info("====================================Response received with Status code : {}",  responseContext.getStatus());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logResponse(requestContext, responseContext);
    }
}
