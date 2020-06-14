package ro.ubb.rpc.repository.adapters;


import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.repository.adapters.Adapter;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RentalAdapter implements Adapter<Long, Rental> {
    @Override
    public Set<Rental> findAll(Connection connection) throws SQLException {
        String sql="select * from rentals";
        Set<Rental> rentals = new HashSet<>();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Rental rental = new Rental((long)resultSet.getInt("movieid"), (long)resultSet.getInt("clientid"));
            rental.setId((long) resultSet.getInt("id"));
            rentals.add(rental);
        }
        return rentals;
    }

    @Override
    public Optional<Rental> findOne(Connection connection, Long id) throws SQLException {
        String sql = "select * from rentals where id=";
        sql+=Math.toIntExact(id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);;
        if (resultSet.next()) {
            Rental rental = new Rental((long) resultSet.getInt("movieid"), (long) resultSet.getInt("clientid"));
            rental.setId((long) resultSet.getInt("id"));
            return Optional.of(rental);
        }
        else{
            return Optional.empty();
        }

    }

    @Override
    public Optional<Rental> add(Connection connection, Rental rental) throws SQLException {
        String sql = "insert into rentals (id,movieId, clientID) values(?,?,?)";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, Math.toIntExact(rental.getId()));
        preparedStatement.setInt(2, Math.toIntExact(rental.getMovieId()));
        preparedStatement.setInt(3, Math.toIntExact(rental.getClientId()));

        preparedStatement.executeUpdate();
        return Optional.of(rental);
    }

    @Override
    public Optional<Rental> update(Connection connection, Rental rental) throws SQLException {
        String sql = "update rentals set movieId=?, clientID=? where id=?";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(3, Math.toIntExact(rental.getId()));
        preparedStatement.setInt(1, Math.toIntExact(rental.getMovieId()));
        preparedStatement.setInt(2, Math.toIntExact(rental.getClientId()));
        preparedStatement.executeUpdate();
        return Optional.of(rental);
    }

    @Override
    public Optional<Rental> delete(Connection connection, Long id) throws SQLException {
        String sql = "delete from rentals where id=?";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setInt(1, Math.toIntExact(id));
        preparedStatement.executeUpdate();
        return Optional.empty();
    }
}
