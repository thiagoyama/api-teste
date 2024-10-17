package br.com.fiap.dto.jogo;

import br.com.fiap.model.Plataforma;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class JogoUpdateDto {
    private String nome;
    private String descricao;
    private Plataforma plataforma;
    private LocalDate dataLancamento;
    private Double valor;
    private Boolean multiplayer;
    private String imgUrl;
}
