package ro.ubb.rpc.serverService;

import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.service.CommonRentalService;
import ro.ubb.rpc.service.RentalService;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerRentalService implements CommonRentalService {
    private RentalService rentalService;
    private ExecutorService executorService;

    public ServerRentalService(RentalService rentalService, ExecutorService executorService) {
        this.rentalService = rentalService;
        this.executorService = executorService;
    }

    @Override
    public Future<Void> deleteRentalsOfMovie(Long movieId) {
        try {
            rentalService.deleteRentalsOfClient(movieId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Future<Void> deleteRentalsofClient(Long clientId) {

        try {
            rentalService.deleteRentalsOfClient(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CompletableFuture<Void> rentMovie(Rental rental) {
        return CompletableFuture.supplyAsync(()-> {
            rentalService.rentMovie(rental);
            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Void> deleteRental(Long id) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                rentalService.deleteRental(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Void> updateRental(Rental rental) {
        return CompletableFuture.supplyAsync(()->{rentalService.updateRental(rental);
            return null;},executorService);
    }

    @Override
    public CompletableFuture<Set<Rental>> getAllRentals() throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                return rentalService.getAllRentals();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        },executorService);
    }
}
