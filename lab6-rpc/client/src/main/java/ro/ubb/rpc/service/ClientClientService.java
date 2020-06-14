package ro.ubb.rpc.service;

import ro.ubb.rpc.ConnectionClient;
import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.message.Message;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientClientService implements CommonClientService {

    private ExecutorService executorService;
    private ConnectionClient connectionClient;

    public ClientClientService(ExecutorService executorService, ConnectionClient connectionClient) {
        this.executorService = executorService;
        this.connectionClient = connectionClient;
    }

    @Override
    public CompletableFuture<Void> addClient(Client client) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonClientService.ADD_CLIENT);
            message.setBody(Client.stringFromClient(client));
            Message response = connectionClient.sendAndReceive(message);

            return null;
        },executorService);

    }

    @Override
    public CompletableFuture<Void> deleteClient(Long id) {
        return CompletableFuture.supplyAsync(()->{
            Message message=new Message();
            message.setHeader(CommonClientService.DELETE_CLIENT);
            message.setBody(id.toString());

            Message response=connectionClient.sendAndReceive(message);

            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Void> updateClient(Client client) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonClientService.UPDATE_CLIENT);
            message.setBody(Client.stringFromClient(client));

            Message response = connectionClient.sendAndReceive(message);

            return null;
        },executorService);
    }

    @Override
    public CompletableFuture<Boolean> isPresent(Long id) {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonClientService.IS_PRESENT);
            message.setBody(id.toString());
            Message response = connectionClient.sendAndReceive(message);
            return Boolean.parseBoolean(response.getBody());
        }, executorService);
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClients() throws SQLException {
        return CompletableFuture.supplyAsync(()->{
            Message message = new Message();
            message.setHeader(CommonClientService.PRINT_CLIENTS);
            Message response = connectionClient.sendAndReceive(message);
            return Client.clientSetFromString(response.getBody());
        }, executorService);
    }
}
