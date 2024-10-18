package br.com.fiap.dto.jogo;

import br.com.fiap.model.Plataforma;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class JogoCreateDto {
        private String nome;
        private String descricao;
        private Plataforma plataforma;
        @Past
        private LocalDate dataLancamento;
        private Double valor;
        private Boolean multiplayer;
        private String imgUrl;
}
