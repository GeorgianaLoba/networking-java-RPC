package ro.ubb.rpc.service;

import ro.ubb.rpc.ConnectionClient;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.message.Message;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientRentalService implements CommonRentalService {


    private ExecutorService executorService;
    private ConnectionClient connectionClient;

    public ClientRentalService(ExecutorService executorService, ConnectionClient connectionClient) {
        this.executorService = executorService;
        this.connectionClient = connectionClient;
    }

    @Override
    public Future<Void> deleteRentalsOfMovie(Long movieId) {
            Message message=new Message();
            message.setHeader(CommonRentalService.DELETE_RENTALS_MOVIE);
            message.setBody(movieId.toString());
            Message response=connectionClient.sendAndReceive(message);
            return null;
    }

    @Override
    public Future<Void> deleteRentalsofClient(Long clientId) {
            Message message=new Message();
            message.setHeader(CommonRentalService.DELETE_RENTALS_CLIENT);
            message.setBody(clientId.toString());
            Message response=connectionClient.sendAndReceive(message);
            return null;
    }

    @Override
    public CompletableFuture<Void> rentMovie(Rental rental) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonRentalService.RENT_MOVIE);
            message.setBody(Rental.stringFromRental(rental));
            Message response = connectionClient.sendAndReceive(message);

            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Void> deleteRental(Long id) {
        return CompletableFuture.supplyAsync(()->{
            Message message=new Message();
            message.setHeader(CommonRentalService.DELETE_RENTAL);
            message.setBody(id.toString());

            Message response=connectionClient.sendAndReceive(message);

            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Void> updateRental(Rental rental) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonRentalService.UPDATE_RENTAL);
            message.setBody(Rental.stringFromRental(rental));

            Message response = connectionClient.sendAndReceive(message);

            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Set<Rental>> getAllRentals() throws SQLException {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonRentalService.PRINT_RENTALS);
            Message response = connectionClient.sendAndReceive(message);
            return Rental.rentalSetFromString(response.getBody());
        }, executorService);
    }

}
