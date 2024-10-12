package org.nthing.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(ErrorMapper.class.getName());

    private final ObjectMapper objectMapper;
    public ErrorMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Failed to handle request", exception);

        int code;
        String message;
        if (exception instanceof WebApplicationException) {
            code = ((WebApplicationException) exception).getResponse().getStatus();
            message = exception.getMessage();
        } else if (exception instanceof WebApplicationException) {
            code = 404;
            message = exception.getMessage();
        } else {
            code = 500;
            message = "Ocorreu um erro inesperado.";
        }

        ObjectNode exceptionJson = objectMapper.createObjectNode();
        exceptionJson.put("status", code);
        exceptionJson.put("error", exception.getClass().getSimpleName());
        exceptionJson.put("message", exception.getClass().getSimpleName());
        exceptionJson.put("timestamp", java.time.LocalDate.now().toString());

        return Response.status(code)
                .entity(exceptionJson)
                .build();

    }
}
