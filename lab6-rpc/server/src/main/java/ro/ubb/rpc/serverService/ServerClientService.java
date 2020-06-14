package ro.ubb.rpc.serverService;

import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.service.ClientService;
import ro.ubb.rpc.service.CommonClientService;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServerClientService implements CommonClientService {

    private ClientService clientService;
    private ExecutorService executorService;

    public ServerClientService(ClientService clientService, ExecutorService executorService) {
        this.clientService = clientService;
        this.executorService = executorService;
    }

    @Override
    public CompletableFuture<Void> addClient(Client client) {

        return CompletableFuture.supplyAsync(()->{
            clientService.addClient(client);
            return null;
        }, executorService);

    }

    @Override
    public CompletableFuture<Void> deleteClient(Long id) throws SQLException {
        return CompletableFuture.supplyAsync(()->{
            try {
                clientService.deleteClient(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;},executorService);

    }
    @Override
    public CompletableFuture<Void> updateClient(Client client) {
        return CompletableFuture.supplyAsync(()->{clientService.updateClient(client);
            return null;},executorService);


    }

    @Override
    public CompletableFuture<Boolean> isPresent(Long id) {
        return CompletableFuture.supplyAsync(() -> clientService.isPresent(id));
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClients() throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                return clientService.getAllClients();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        },executorService);
    }
}
