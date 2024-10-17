package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class Jogo {

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
