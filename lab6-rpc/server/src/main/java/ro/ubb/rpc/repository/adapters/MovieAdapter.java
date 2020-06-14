package ro.ubb.rpc.repository.adapters;

import ro.ubb.rpc.domain.Movie;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovieAdapter implements Adapter<Long,Movie > {

    @Override
    public Set<Movie> findAll(Connection connection) throws SQLException {
        String sql="select * from movies";
        Set<Movie> movies = new HashSet<>();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Movie movie = new Movie(resultSet.getString("title"), resultSet.getString("director"),
                    resultSet.getInt("imdbrating"), resultSet.getInt("releaseyear"));
            movie.setId((long) resultSet.getInt("id"));
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public Optional<Movie> findOne(Connection connection, Long id) throws SQLException {
        String sql = "select * from movies where id=";
        sql+=Math.toIntExact(id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);;
        if (resultSet.next()) {
            Movie movie = new Movie(resultSet.getString("title"), resultSet.getString("director"),
                    resultSet.getInt("imdbrating"), resultSet.getInt("releaseyear"));
            movie.setId((long) resultSet.getInt("id"));
            return Optional.of(movie);
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Movie> add(Connection connection, Movie movie) throws SQLException {
        String sql = "insert into movies (id,title, director, imdbRating, releaseYear) values(?,?,?,?,?)";
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setInt(1, Math.toIntExact(movie.getId()));
        preparedStatement.setString(2, movie.getTitle());
        preparedStatement.setString(3, movie.getDirector());
        preparedStatement.setInt(4, movie.getImdbRating());
        preparedStatement.setInt(5, movie.getReleaseYear());
        preparedStatement.executeUpdate();
        return Optional.of(movie);
    }

    @Override
    public Optional<Movie> update(Connection connection, Movie movie) throws SQLException {
        String sql = "update movies set title=?, director=?, imdbRating=?, releaseYear=? where id=?";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setString(1, movie.getTitle());
        preparedStatement.setString(2, movie.getDirector());
        preparedStatement.setInt(3, movie.getImdbRating());
        preparedStatement.setInt(4, movie.getImdbRating());
        preparedStatement.setInt(5, Math.toIntExact(movie.getId()));
        return Optional.of(movie);
    }

    @Override
    public Optional<Movie> delete(Connection connection, Long id) throws SQLException {
        String sql = "delete from movies where id=?";
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setInt(1, Math.toIntExact(id));
        preparedStatement.executeUpdate();
        return Optional.empty();
    }
}
