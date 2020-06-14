package ro.ubb.rpc.service;

import ro.ubb.rpc.domain.Movie;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CommonMovieService {

    String ADD_MOVIE = "addMovie";
    String DELETE_MOVIE="deleteMovie";
    String IS_PRESENT="isPresent";
    String UPDATE_MOVIE="updateMovie";
    String PRINT_MOVIES = "printMovies";


    CompletableFuture<Void> addMovie(Movie movie);
    CompletableFuture<Void> deleteMovie(Long id) throws SQLException;
    CompletableFuture<Void> updateMovie(Movie movie);
    CompletableFuture<Boolean> isPresent(Long id);
    CompletableFuture<Set<Movie>> getAll() throws SQLException;
}

