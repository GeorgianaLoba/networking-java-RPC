package ro.ubb.rpc.service;



import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.domain.exceptions.ValidatorException;
import ro.ubb.rpc.repository.InterfaceRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MovieService {
    private InterfaceRepository<Long, Movie> repository;

    public MovieService(InterfaceRepository<Long,Movie> repository)
    {
        this.repository=repository;
    }

    public Set<Movie> getAllMovies() throws SQLException {
        Iterable<Movie> movies=repository.findALL();
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());
    }

    public void addMovie (Movie movie){
        repository.save(movie);
    }

    public void updateMovie(Movie movie)
    {
        repository.save(movie);
    }

    public void deleteMovie(Long id) throws SQLException {
        repository.delete(id);
    }

    public Set<Movie> filterMovieByTitle(String s) throws SQLException {
        Iterable<Movie> movies=repository.findALL();
        Set<Movie> filteredMovie=new HashSet<>();
        movies.forEach(filteredMovie::add);
        filteredMovie.removeIf(movie -> !movie.getTitle().contains(s));
        return StreamSupport.stream(filteredMovie.spliterator(),false).collect(Collectors.toSet());
    }

    public boolean isPresent (Long id){
        try{
            repository.findOne(id);
            return true;
        }catch (IllegalArgumentException e)
        {
            return false;
        }
    }
}
