package ro.ubb.rpc.serverService.handlers;

import ro.ubb.rpc.ConnectionServer;
import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.message.Message;
import ro.ubb.rpc.serverService.ServerClientService;
import ro.ubb.rpc.serverService.ServerRentalService;
import ro.ubb.rpc.service.CommonClientService;
import ro.ubb.rpc.service.CommonRentalService;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RentalHandlers {

    public RentalHandlers() {
    }

    public static void setHandlers(ConnectionServer connectionServer, ServerRentalService rentalService){


        connectionServer.addHandler(CommonRentalService.RENT_MOVIE,(request)->{
            String body = request.getBody();
            Rental rental = Rental.rentalFromString(body);
            try {
                rentalService.rentMovie(rental);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR,"Error occured during adding.");
            }
        });

        connectionServer.addHandler(CommonRentalService.DELETE_RENTAL, (request)->{
            String body = request.getBody();
            Long id = Long.parseLong(body);
            try{
                rentalService.deleteRental(id);
                return new Message(Message.OK, "Successfully deleted.");
            } catch (RuntimeException | SQLException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during delete.");

            }
        });

        connectionServer.addHandler(CommonRentalService.UPDATE_RENTAL, (request)->{
            String body = request.getBody();
            Rental rental = Rental.rentalFromString(body);
            try {
                rentalService.updateRental(rental);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during update.");
            }
        });


        connectionServer.addHandler(CommonRentalService.PRINT_RENTALS, (request)->{
            String body = request.getBody();
            try{
                Set<Rental> rentals = rentalService.getAllRentals().get();
                Message message = new Message();
                message.setHeader(Message.OK);
                message.setBody(Rental.stringFromSetRentals(rentals));
                return message;
            } catch (InterruptedException | ExecutionException | SQLException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occuring during print.");
            }
        });


        connectionServer.addHandler(CommonRentalService.DELETE_RENTALS_CLIENT, (request)->{
            String body = request.getBody();
            try{
                rentalService.deleteRentalsofClient(Long.parseLong(request.getBody()));
                return new Message(Message.OK, "Successfully deleted cascade.");
            }
             catch (RuntimeException e) {
                 e.printStackTrace();
                 return new Message(Message.ERROR, "Error occured during cascade delete.");
             }
        });

        connectionServer.addHandler(CommonRentalService.DELETE_RENTALS_MOVIE, (request)->{
            String body = request.getBody();
            try{
                rentalService.deleteRentalsOfMovie(Long.parseLong(request.getBody()));
                return new Message(Message.OK, "Successfully deleted cascade.");
            }
            catch (RuntimeException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during cascade delete.");
            }
        });


    }

}
