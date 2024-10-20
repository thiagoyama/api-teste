package br.com.fiap.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponseDto {

    private String mensagem;

    private List<FieldErrorDto> erros;
}
