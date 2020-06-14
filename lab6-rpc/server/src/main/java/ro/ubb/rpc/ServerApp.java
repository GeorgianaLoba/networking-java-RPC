package ro.ubb.rpc;

import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.domain.validators.ClientValidator;
import ro.ubb.rpc.domain.validators.MovieValidator;
import ro.ubb.rpc.domain.validators.RentalValidator;
import ro.ubb.rpc.domain.validators.Validator;
import ro.ubb.rpc.repository.DBRepository;
import ro.ubb.rpc.repository.InterfaceRepository;
import ro.ubb.rpc.repository.adapters.Adapter;
import ro.ubb.rpc.repository.adapters.ClientAdapter;
import ro.ubb.rpc.repository.adapters.MovieAdapter;
import ro.ubb.rpc.repository.adapters.RentalAdapter;
import ro.ubb.rpc.serverService.ServerClientService;
import ro.ubb.rpc.serverService.ServerMovieService;
import ro.ubb.rpc.serverService.ServerRentalService;
import ro.ubb.rpc.serverService.handlers.ClientHandlers;
import ro.ubb.rpc.serverService.handlers.MovieHandlers;
import ro.ubb.rpc.serverService.handlers.RentalHandlers;
import ro.ubb.rpc.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    public static void main(String[] args) {
        try {
            System.out.println("Server started...");
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Connection connection = null;

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/moviestore", "postgres", "geo");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Validator<Client> clientValidator=new ClientValidator();
            Validator<Movie> movieValidator = new MovieValidator();
            Validator<Rental> rentalValidator=new RentalValidator();
            Adapter<Long, Movie> movieAdapter = new MovieAdapter();
            Adapter<Long,Client> clientAdapter=new ClientAdapter();
            Adapter<Long,Rental> rentalAdapter=new RentalAdapter();
            InterfaceRepository<Long, Movie> movieRepository = new DBRepository<>(movieValidator, connection, movieAdapter);
            InterfaceRepository<Long,Client> clientInterfaceRepository=new DBRepository<>(clientValidator,connection,clientAdapter);
            InterfaceRepository<Long,Rental> rentalInterfaceRepository=new DBRepository<>(rentalValidator,connection,rentalAdapter);
            MovieService movieService = new MovieService(movieRepository);
            ClientService clientService=new ClientService(clientInterfaceRepository);
            RentalService rentalService=new RentalService(rentalInterfaceRepository,movieRepository,clientInterfaceRepository);

            ServerMovieService commonMovieService = new ServerMovieService(movieService, executorService);
            ServerClientService commonClientService=new ServerClientService(clientService,executorService);
            ServerRentalService commonRentalService=new ServerRentalService(rentalService,executorService);

            ConnectionServer connectionServer = new ConnectionServer(executorService);
            MovieHandlers.setHandlers(connectionServer, commonMovieService);
            ClientHandlers.setHandlers(connectionServer,commonClientService);
            RentalHandlers.setHandlers(connectionServer,commonRentalService);



            connectionServer.startServer();
            executorService.shutdown();
        }
        catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }
}
