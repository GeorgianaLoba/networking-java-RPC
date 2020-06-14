package ro.ubb.rpc.serverService;

import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.service.CommonMovieService;
import ro.ubb.rpc.service.MovieService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerMovieService implements CommonMovieService {
    private MovieService movieService;
    private ExecutorService executorService;

    public ServerMovieService(MovieService movieService, ExecutorService executorService) {
        this.movieService = movieService;
        this.executorService = executorService;
    }

    @Override
    public CompletableFuture<Void> addMovie(Movie movie) {
        //movieService.addMovie(movie);
        //return null;
        return CompletableFuture.supplyAsync(()->{
            movieService.addMovie(movie);
            return null;
            }, executorService);
    }

    @Override
    public CompletableFuture<Void> deleteMovie(Long id) {
        return CompletableFuture.supplyAsync(()->{
            try {
                movieService.deleteMovie(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;},executorService);
    }

    @Override
    public CompletableFuture<Void> updateMovie(Movie movie) {
        return CompletableFuture.supplyAsync(()->{movieService.updateMovie(movie);
        return null;},executorService);
    }

    @Override
    public CompletableFuture<Boolean> isPresent(Long id) {
        return CompletableFuture.supplyAsync(() -> movieService.isPresent(id));
    }

    @Override
    public CompletableFuture<Set<Movie>> getAll(){
        return CompletableFuture.supplyAsync(()-> {
            try {
                return movieService.getAllMovies();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        },executorService);
    }
}
