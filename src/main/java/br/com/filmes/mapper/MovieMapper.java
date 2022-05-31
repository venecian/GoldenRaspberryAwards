package br.com.filmes.mapper;

import br.com.filmes.dto.MovieDTO;
import br.com.filmes.model.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static Movie dtoToEntity(MovieDTO movieDTO){

       Movie movie = new Movie();
       movie.setId(movieDTO.getId());
       movie.setYear(movieDTO.getYear());
       movie.setProducers(movieDTO.getProducers());
       movie.setTitle(movieDTO.getTitle());
       movie.setStudio(movieDTO.getStudio());
       movie.setWinner(movieDTO.getWinner());
       return movie;
    }

    public static List<Movie> dtoToEntity(List<MovieDTO> movieDTOList){
        List<Movie> list = new ArrayList<>();

        for (MovieDTO movieDTO : movieDTOList){
            Movie dtoToEntity = dtoToEntity(movieDTO);
            list.add(dtoToEntity);
        }
        return list;
    }


    public static MovieDTO entityToDTO(Movie movie) {
        return objectMapper.convertValue(movie, MovieDTO.class);
    }

    public static List<MovieDTO> entityToDTO(List<Movie> movies){

        if (movies == null) {
            return null;
        }
        return movies.stream().map(MovieMapper::entityToDTO).collect(Collectors.toList());
    }

}
