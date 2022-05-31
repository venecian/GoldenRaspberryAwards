package br.com.filmes.dto;

import lombok.Data;

@Data
public class DetalheErroDTO {
    private String campo;
    private String valorInvalido;
    private String mensagem;
}
