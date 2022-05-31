package br.com.filmes.controller;

import br.com.filmes.dto.MovieDTO;
import br.com.filmes.dto.ProducerPrizeDTO;
import br.com.filmes.dto.ProducerRangeDTO;
import br.com.filmes.mapper.MovieMapper;
import br.com.filmes.model.Movie;
import br.com.filmes.service.MovieServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Controller de filmes",
        tags = {"Controller de filmes"})
public class MovieController {

    private final MovieServices movieServices;

    public MovieController(MovieServices movieServices) {
        this.movieServices = movieServices;
    }

    @ApiOperation("Api para listar todos filmes")
    @GetMapping("/movies-list")
    public ResponseEntity<List<MovieDTO>> getTodosFilmes() {
        List<Movie> movies = movieServices.getMovies();
        return new ResponseEntity<>(MovieMapper.entityToDTO(movies), HttpStatus.OK);
    }

    @ApiOperation("Api para obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que\n" +
            "obteve dois prêmios mais rápido")
    @GetMapping("/productor-prize")
    public ResponseEntity<ProducerRangeDTO> getProdutoresPremiados() {

        ProducerRangeDTO producerRangeDTO = movieServices.getProdutoresPremiados(movieServices.getMoviesResult());
        return new ResponseEntity<>(producerRangeDTO, HttpStatus.OK);
    }


    @ApiOperation("Api para cadastro de filmes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Filme criado com sucesso")
    })
    @PostMapping(value = "/create-movie", consumes = "application/json")
    public ResponseEntity<MovieDTO> criarFilme(@Valid @RequestBody MovieDTO movieDTO) {
        Movie movieConvertido = MovieMapper.dtoToEntity(movieDTO);
        movieDTO = MovieMapper.entityToDTO(movieServices.criarMovie(movieConvertido));
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

}
