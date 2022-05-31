package br.com.filmes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProducerPrizeDTO {

    @ApiModelProperty(position = 1, example = "Producer 1")
    private String producer;

    @ApiModelProperty(position = 2, example = "1")
    private Integer interval;

    @ApiModelProperty(position = 3, example = "2008")
    private Integer previousWin;

    @ApiModelProperty(position = 4, example = "2009")
    private Integer followingWin;
}
