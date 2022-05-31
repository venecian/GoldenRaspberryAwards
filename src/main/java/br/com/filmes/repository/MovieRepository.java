package br.com.filmes.repository;

import br.com.filmes.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {

    @Query(value="select * from movie where winner=true order by ano",nativeQuery = true)
    public List<Movie> getMoviesResult();


}
