package ro.ubb.rpc.serverService.handlers;

import ro.ubb.rpc.ConnectionServer;
import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.message.Message;
import ro.ubb.rpc.serverService.ServerClientService;
import ro.ubb.rpc.serverService.ServerMovieService;
import ro.ubb.rpc.service.CommonClientService;
import ro.ubb.rpc.service.CommonMovieService;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ClientHandlers {

    public ClientHandlers() {
    }

    public static void setHandlers(ConnectionServer connectionServer, ServerClientService clientService){


        connectionServer.addHandler( CommonClientService.ADD_CLIENT,(request)->{
            String body = request.getBody();
            Client client = Client.clientFromString(body);
            try {
                clientService.addClient(client);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR,"Error occured during adding.");
            }
        });

        connectionServer.addHandler(CommonClientService.DELETE_CLIENT, (request)->{
            String body = request.getBody();
            Long id = Long.parseLong(body);
            try{
                clientService.deleteClient(id);
                return new Message(Message.OK, "Successfully deleted.");
            } catch (RuntimeException | SQLException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during delete.");

            }
        });

        connectionServer.addHandler(CommonClientService.UPDATE_CLIENT, (request)->{
            String body = request.getBody();
            Client client = Client.clientFromString(body);
            try {
                clientService.updateClient(client);
                return new Message(Message.OK, "Successfully added.");
            }
            catch (RuntimeException e){
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occured during update.");
            }
        });


        connectionServer.addHandler(CommonClientService.PRINT_CLIENTS, (request)->{
            String body = request.getBody();
            try{
                Set<Client> clients = clientService.getAllClients().get();
                Message message = new Message();
                message.setHeader(Message.OK);
                message.setBody(Client.stringFromSetClients(clients));
                return message;
            } catch (InterruptedException | ExecutionException | SQLException e) {
                e.printStackTrace();
                return new Message(Message.ERROR, "Error occuring during print.");
            }
        });
    }
}
