package br.com.fiap.dto.jogo;

import br.com.fiap.model.Plataforma;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class JogoDetailDto {
    private Integer codigo;
    private String nome;
    private String descricao;
    private Plataforma plataforma;
    private LocalDate dataLancamento;
    private Double valor;
    private Boolean multiplayer;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String imgUrl;
}
