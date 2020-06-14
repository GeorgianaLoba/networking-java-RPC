package ro.ubb.rpc.service;

import ro.ubb.rpc.ConnectionClient;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.message.Message;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientMovieService implements CommonMovieService {
    private ExecutorService executorService;
    private ConnectionClient connectionClient;

    public ClientMovieService(ExecutorService executorService, ConnectionClient connectionClient) {
        this.executorService = executorService;
        this.connectionClient = connectionClient;
    }

    @Override
    public CompletableFuture<Void> addMovie(Movie movie) {
        return CompletableFuture.supplyAsync( ()->{
            Message message = new Message();
            message.setHeader(CommonMovieService.ADD_MOVIE);
            message.setBody(Movie.stringFromMovie(movie));
            Message response = connectionClient.sendAndReceive(message);
            return null;
        }, executorService);
    }

    @Override
    public CompletableFuture<Void> deleteMovie(Long id) {
        return CompletableFuture.supplyAsync(()->{
            Message message=new Message();
            message.setHeader(CommonMovieService.DELETE_MOVIE);
            message.setBody(id.toString());
            Message response=connectionClient.sendAndReceive(message);
            return null;
        },executorService);
    }


    @Override
    public CompletableFuture<Void> updateMovie(Movie movie) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonMovieService.UPDATE_MOVIE);
            message.setBody(Movie.stringFromMovie(movie));
            Message response = connectionClient.sendAndReceive(message);
            return null;
        }, executorService);
    }

    @Override
    public CompletableFuture<Boolean> isPresent(Long id) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonMovieService.IS_PRESENT);
            message.setBody(id.toString());
            Message response = connectionClient.sendAndReceive(message);
            return Boolean.parseBoolean(response.getBody());
        }, executorService);
    }

    @Override
    public CompletableFuture<Set<Movie>> getAll() {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonMovieService.PRINT_MOVIES);
            Message response = connectionClient.sendAndReceive(message);
            return Movie.movieSetFromString(response.getBody());
        }, executorService);
    }


}
