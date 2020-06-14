package ro.ubb.rpc.service;

//import ro.ubb.movie.domain.Client;
//import ro.ubb.movie.repository.InterfaceRepository;



import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.repository.InterfaceRepository;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private InterfaceRepository<Long, Client> repository;

    public ClientService(InterfaceRepository<Long, Client> repository) {
        this.repository = repository;
    }

    public void addClient(Client client)
    {
        repository.save(client);
    }

    public void updateClient(Client client)
    {
        repository.update(client);
    }

    public void deleteClient(Long id) throws SQLException {
        repository.delete(id);
    }

    public Set<Client> getAllClients() throws SQLException {
        Iterable<Client> clients = repository.findALL();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    public boolean isPresent (Long id){
        try{
            repository.findOne(id);
            return true;
        }catch (IllegalArgumentException e)
        {
            return false;
        }
    }

}
