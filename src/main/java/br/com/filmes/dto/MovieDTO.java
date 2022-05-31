package br.com.filmes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MovieDTO {

    @ApiModelProperty(position = 1, value = "Id")
    private Long id;

    @ApiModelProperty(position = 2, required = true, value = "Ano do filme", example = "1986", dataType = "Inteiro")
    private Integer year;

    @ApiModelProperty(position = 3, required = true, value = "Título", example = "Under the Cherry Moon")
    @NotNull
    private String title;

    @ApiModelProperty(position = 4, required = true, value = "Usar separador por ,",example = "Warner Bros")
    @NotNull
    private String studio;

    @ApiModelProperty(position = 5, required = true, value = "Usar separador por ,", example = "Bob Cavallo, Joe Ruffalo and Steve Fargnoli")
    @NotNull
    private String producers;

    @ApiModelProperty(position = 6, required = true, value = "true or false")
    private Boolean winner;

}
