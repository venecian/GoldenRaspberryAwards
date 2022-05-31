package br.com.filmes.service;

import br.com.filmes.dto.ProducerPrizeDTO;
import br.com.filmes.dto.ProducerRangeDTO;
import br.com.filmes.exceptions.MovieException;
import br.com.filmes.model.Movie;
import br.com.filmes.repository.MovieRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MovieServices {

    @Value("${file-import-csv}")
	private String CSV_PATH;

    private final MovieRepository movieRepository;

    public MovieServices(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies(){
        List<Movie> movieList = (List<Movie>) movieRepository.findAll();
        if (movieList.isEmpty()){
            return null;
        }
        return movieList;
    }

	public List<Movie> getMoviesResult() {
		return movieRepository.getMoviesResult();
	}

	public ArrayList<String> getAllProducers(List<Movie> movies){

		ArrayList<String> todosProdutores = new ArrayList<>();
		int i, p;

		for (i = 0; i< movies.size();i++) {
			String[] producers = movies.get(i).getProducers().split(", | and ");

			for (p = 0; p < producers.length; p++) {
				if (!todosProdutores.contains(producers[p])) {
					todosProdutores.add(producers[p]);
				}
			}
		}

		return todosProdutores;
	}


	public ProducerRangeDTO getProdutoresPremiados(List<Movie> movies){

		ArrayList<String> todosProdutores = getAllProducers(movies);
		ArrayList<ProducerPrizeDTO> producersPrizeDTO = new ArrayList<>();
		int i, reg, min, max, intevalo, minInvervalo=9999, maxIntervalo = 0;

		//percorre todos produtores
		for(String producer : todosProdutores) {

			reg = 0;
			min = 0;
			max = 0;

			//percorre todos os filmes de um respectivo produtor
			for (i = 0; i < movies.size(); i++) {
				if (movies.get(i).getProducers().contains(producer)) {

					if (reg==0) {
						min = movies.get(i).getYear();
					} else {

						//cria registro
						max = movies.get(i).getYear();
						intevalo = max - min;

						ProducerPrizeDTO producerPrizeDTO = new ProducerPrizeDTO();
						producerPrizeDTO.setProducer(producer);
						producerPrizeDTO.setPreviousWin(min);
						producerPrizeDTO.setFollowingWin(max);
						producerPrizeDTO.setInterval(intevalo);
						producersPrizeDTO.add(producerPrizeDTO);

						min = max;

						//obtendo intervalo: minimo e maximo
						if (intevalo>maxIntervalo) {
							maxIntervalo = intevalo;
						}

						if (intevalo<minInvervalo) {
							minInvervalo = intevalo;
						}
					}
					reg++;
				}
			}
		}

		//percorrendo todos os registros do producersPrizeDTO
		ArrayList<ProducerPrizeDTO> listaMin = new ArrayList<>();
		ArrayList<ProducerPrizeDTO> listaMax = new ArrayList<>();

		for(ProducerPrizeDTO obj : producersPrizeDTO) {
			if (obj.getInterval()==minInvervalo) {
				listaMin.add(obj);
			}
			if (obj.getInterval()==maxIntervalo) {
				listaMax.add(obj);
			}
		}

		ProducerRangeDTO producerRangeDTO = new ProducerRangeDTO();
		producerRangeDTO.setMin(listaMin);
		producerRangeDTO.setMax(listaMax);

		return producerRangeDTO;
	}


    public Movie criarMovie(Movie movie){
        if (movie.getId() > 0) {
            throw new MovieException("Ao criar um movie não deve ser informado o ID!");
        }
        return movieRepository.save(movie);
    }

    public int importarDadosCSV(){

        movieRepository.deleteAll();
		int registros = 0;

       try {

			FileReader filereader = new FileReader(new File(CSV_PATH));
			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

			CSVReader csvReader = new CSVReaderBuilder(filereader)
					.withCSVParser(parser)
					.build();

			List<List<String>> linhas = new ArrayList<List<String>>();
			String[] colunas = null;

			while((colunas = csvReader.readNext())!=null){
				linhas.add(Arrays.asList(colunas));
				registros++;
			}

			linhas.remove(0);
			linhas.forEach(cols-> {

				try{
					//System.out.println(cols.get(0) + cols.get(1) + cols.get(2) + cols.get(3) + cols.get(4));

					Movie movie = new Movie();
					movie.setId(Long.valueOf(0));
					movie.setYear(Integer.valueOf(cols.get(0)));
					movie.setTitle(cols.get(1));
					movie.setStudio(cols.get(2));
					movie.setProducers(cols.get(3));

					movie.setWinner(false);
					if (cols.get(4).trim().toLowerCase().equalsIgnoreCase("YES")){
						movie.setWinner(true);
					}

                    //System.out.println(movie);
                    criarMovie(movie);


				} catch(Exception e){
					System.out.println("Erro no campo " + e.getMessage());
				}

			});

		} catch (CsvValidationException c) {
			System.out.println(c.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return registros-1;
    }

}
