package ro.ubb.rpc.repository.adapters;



import ro.ubb.rpc.domain.BaseEntity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface Adapter<ID extends Serializable,T extends BaseEntity<ID>> {

    Set<T> findAll(Connection connection) throws SQLException;
    Optional<T> findOne(Connection connection, ID id) throws SQLException;
    Optional<T> add(Connection connection, T entity) throws SQLException;
    Optional<T> update(Connection connection, T entity) throws SQLException;
    Optional<T> delete(Connection connection, ID id) throws SQLException;

}
