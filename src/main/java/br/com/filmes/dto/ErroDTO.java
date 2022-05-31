package br.com.filmes.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErroDTO {
    private Integer statusCode;
    private List<DetalheErroDTO> erros;
}
