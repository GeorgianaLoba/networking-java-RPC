package ro.ubb.rpc.service;

import ro.ubb.rpc.domain.Client;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CommonClientService {
    String ADD_CLIENT="addClient";
    String DELETE_CLIENT="deleteClient";
    String UPDATE_CLIENT="updateClient";
    String IS_PRESENT="isPresent";
    String PRINT_CLIENTS="printClients";

    CompletableFuture<Void> addClient(Client client);
    CompletableFuture<Void> deleteClient(Long id) throws SQLException;
    CompletableFuture<Void> updateClient(Client client);
    CompletableFuture<Boolean> isPresent(Long id);
    CompletableFuture<Set<Client>> getAllClients() throws SQLException;



}
