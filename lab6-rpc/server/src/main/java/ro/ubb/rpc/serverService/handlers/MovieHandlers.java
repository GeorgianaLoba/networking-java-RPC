package ro.ubb.rpc.serverService.handlers;

import ro.ubb.rpc.ConnectionServer;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.message.Message;
import ro.ubb.rpc.serverService.ServerMovieService;
import ro.ubb.rpc.service.CommonMovieService;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MovieHandlers {

    public MovieHandlers() {
    }

    public static void setHandlers(ConnectionServer connectionServer, ServerMovieService movieService){


        connectionServer.addHandler( CommonMovieService.ADD_MOVIE,(request)->{
            String body = request.getBody();
            Movie movie = Movie.movieFromString(body);
            try {
                movieService.addMovie(movie);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR,"Error occured during adding.");
            }
        });

        connectionServer.addHandler(CommonMovieService.DELETE_MOVIE, (request)->{
            String body = request.getBody();
            Long id = Long.parseLong(body);
            try{
                movieService.deleteMovie(id);
                return new Message(Message.OK, "Successfully deleted.");
            } catch (RuntimeException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during delete.");

            }
        });

        connectionServer.addHandler(CommonMovieService.UPDATE_MOVIE, (request)->{
            String body = request.getBody();
            Movie movie = Movie.movieFromString(body);
            try {
                movieService.updateMovie(movie);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during update.");
            }
        });


        connectionServer.addHandler(CommonMovieService.PRINT_MOVIES, (request)->{
            String body = request.getBody();
            try{
                Set<Movie> movies = movieService.getAll().get();
                Message message = new Message();
                message.setHeader(Message.OK);
                message.setBody(Movie.stringFromSetMovies(movies));
                return message;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occuring during print.");
            }
        });
    }

}
