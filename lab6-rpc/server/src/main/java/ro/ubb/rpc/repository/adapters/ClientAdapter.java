package ro.ubb.rpc.repository.adapters;



import ro.ubb.rpc.domain.Client;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientAdapter implements Adapter<Long, Client> {

    @Override
    public Set<Client> findAll(Connection connection) throws SQLException {
        String sql = "select * from clients";
        Set<Client> clients = new HashSet<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            Client client = new Client(resultSet.getString("name"), resultSet.getString("address"),
                    resultSet.getInt("age"));
            client.setId((long) resultSet.getInt("id"));
            clients.add(client);
        }
        return clients;
    }

    @Override
    public Optional<Client> findOne(Connection connection, Long id) throws SQLException {
        String sql = "select * from clients where id=";
        sql+=Math.toIntExact(id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);;
        if (resultSet.next()) {
            Client client = new Client(resultSet.getString("name"), resultSet.getString("address"),
                    resultSet.getInt("age"));
            client.setId((long) resultSet.getInt("id"));
            return Optional.of(client);
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> add(Connection connection, Client client) throws SQLException {
        String sql = "insert into clients (id,name, address, age) values(?,?,?,?)";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, Math.toIntExact(client.getId()));
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getAddress());
        preparedStatement.setInt(4, client.getAge());
        preparedStatement.executeUpdate();

        return Optional.of(client);
    }

    @Override
    public Optional<Client> update(Connection connection, Client client) throws SQLException {
        String sql = "update Client set name=?, address=?, age=? where id=?";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getAddress());
        preparedStatement.setInt(3, client.getAge());
        preparedStatement.setInt(4, Math.toIntExact(client.getId()));

        preparedStatement.executeUpdate();
        return Optional.of(client);
    }

    @Override
    public Optional<Client> delete(Connection connection, Long id) throws SQLException {
        String sql = "delete from clients where id=?";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, Math.toIntExact(id));
        preparedStatement.executeUpdate();
        return Optional.empty();
    }
}
