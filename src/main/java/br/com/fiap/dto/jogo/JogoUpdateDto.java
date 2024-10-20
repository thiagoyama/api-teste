package br.com.fiap.dto.jogo;

import br.com.fiap.model.Plataforma;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class JogoUpdateDto {
    @NotBlank
    @Size(max = 50)
    private String nome;
    @Size(max = 255)
    private String descricao;
    private Plataforma plataforma;
    @Past
    private LocalDate dataLancamento;
    @DecimalMin("0")
    private Double valor;
    private Boolean multiplayer;
    private String imgUrl;
}
