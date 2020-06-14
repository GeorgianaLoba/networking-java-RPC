package ro.ubb.rpc.service;

import ro.ubb.rpc.domain.Rental;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CommonRentalService {
    String DELETE_RENTALS_MOVIE="deleteRentalsOfMovie";
    String DELETE_RENTALS_CLIENT="deleteRentalsOfClient";
    String RENT_MOVIE="rentMovie";
    String DELETE_RENTAL="deleteRental";
    String UPDATE_RENTAL="updateRental";
    String PRINT_RENTALS="printRentals";



    Future<Void> deleteRentalsOfMovie(Long movieId);
    Future<Void> deleteRentalsofClient(Long clientId);
    CompletableFuture<Void> rentMovie(Rental rental);
    CompletableFuture<Void> deleteRental(Long id) throws SQLException;
    CompletableFuture<Void> updateRental(Rental rental);
    CompletableFuture<Set<Rental>> getAllRentals() throws SQLException;


}
