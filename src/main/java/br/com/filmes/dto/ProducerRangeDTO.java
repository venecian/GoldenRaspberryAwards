package br.com.filmes.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ProducerRangeDTO {
    private List<ProducerPrizeDTO> min;
    private List<ProducerPrizeDTO> max;
}
