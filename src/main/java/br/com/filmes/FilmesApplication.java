package br.com.filmes;

import br.com.filmes.model.Movie;
import br.com.filmes.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FilmesApplication {

	@Autowired
	private MovieServices movieServices;

	public static void main(String[] args) {
		SpringApplication.run(FilmesApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		movieServices.importarDadosCSV();
	}

}
