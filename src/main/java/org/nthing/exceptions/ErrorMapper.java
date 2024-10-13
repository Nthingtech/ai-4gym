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
        if(exception instanceof WebApplicationException webApplicationException) {
                code = webApplicationException.getResponse().getStatus();
                message = exception.getMessage();
        } else {
            switch (exception.getClass().getSimpleName()) {
                case "RecordNotFoundException" -> {
                    code = 404;
                    message = exception.getMessage();
                }
                case "BusinessException" -> {
                    code = 422;
                    message = exception.getMessage();
                }
                default -> {
                    code = 500;
                    message = "Ocorreu um erro inesperado";
                }
            }
        }

        ObjectNode exceptionJson = objectMapper.createObjectNode();
        exceptionJson.put("status", code);
        exceptionJson.put("error", exception.getClass().getSimpleName());
        exceptionJson.put("message", message);
        exceptionJson.put("timestamp", java.time.LocalDate.now().toString());

        return Response.status(code)
                .entity(exceptionJson)
                .build();

    }
}
