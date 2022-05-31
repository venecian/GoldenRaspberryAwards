package br.com.filmes.controller;

import br.com.filmes.ConfigTest;
import br.com.filmes.dto.ProducerPrizeDTO;
import br.com.filmes.dto.ProducerRangeDTO;
import br.com.filmes.exceptions.MovieException;
import br.com.filmes.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class MovieControllerTest extends ConfigTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void validaMinMaxIntervalorTest_returnStatusCode200() throws Exception {

        //int registrosBancoDados = movieRepository.count();

        //busca por endpoint
        ResultActions resultActions = mockMvc.perform(get("/productor-prize")
                        .contentType("application/json")
                        .content(""))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();


        //registro base para teste
        ArrayList<ProducerPrizeDTO> ProducerPrizeDTOMins = new ArrayList<>();
        ProducerPrizeDTO producerPrizeDTOMin = new ProducerPrizeDTO();
        producerPrizeDTOMin.setInterval(1);
        producerPrizeDTOMin.setPreviousWin(1990);
        producerPrizeDTOMin.setFollowingWin(1991);
        producerPrizeDTOMin.setProducer("Joel Silver");
        ProducerPrizeDTOMins.add(producerPrizeDTOMin);

        ArrayList<ProducerPrizeDTO> ProducerPrizeDTOMaxs = new ArrayList<>();
        ProducerPrizeDTO producerPrizeDTOMax = new ProducerPrizeDTO();
        producerPrizeDTOMax.setInterval(13);
        producerPrizeDTOMax.setPreviousWin(2002);
        producerPrizeDTOMax.setFollowingWin(2015);
        producerPrizeDTOMax.setProducer("Matthew Vaughn");
        ProducerPrizeDTOMaxs.add(producerPrizeDTOMax);

        ProducerRangeDTO producerRangeDTO = new ProducerRangeDTO();
        producerRangeDTO.setMin(ProducerPrizeDTOMins);
        producerRangeDTO.setMax(ProducerPrizeDTOMaxs);

        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(producerRangeDTO));


    }


}
