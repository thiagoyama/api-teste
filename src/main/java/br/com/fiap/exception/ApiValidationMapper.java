package br.com.fiap.exception;

import br.com.fiap.dto.error.ErrorResponseDto;
import br.com.fiap.dto.error.FieldErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ApiValidationMapper implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<FieldErrorDto> erros = new ArrayList<>();
        for (ConstraintViolation<?> ex : exception.getConstraintViolations()){
            erros.add(new FieldErrorDto(ex.getPropertyPath().toString(), ex.getMessage()));
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponseDto("Dados inválidos", erros)).build();
    }
}
