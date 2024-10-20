package br.com.fiap.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FieldErrorDto {

    private String campo;

    private String mensagem;
}
