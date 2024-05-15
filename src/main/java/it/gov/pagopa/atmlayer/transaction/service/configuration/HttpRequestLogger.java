package it.gov.pagopa.atmlayer.transaction.service.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.owasp.encoder.Encode;

import java.io.IOException;

@Provider
@Slf4j
public class HttpRequestLogger implements ContainerRequestFilter {

    public void logRequest(ContainerRequestContext requestContext) {
        String uri = requestContext.getUriInfo().getAbsolutePath() != null ? Encode.forJava(requestContext.getUriInfo().getAbsolutePath().toString()) : null;
        String method = requestContext.getMethod();
        String headers = requestContext.getHeaders() != null ? Encode.forJava(requestContext.getHeaders().toString()) : null;
        log.info("====================================Request received with Method : {} and Path : {}, Headers  :  {}", method, uri, headers);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logRequest(requestContext);
    }
}
