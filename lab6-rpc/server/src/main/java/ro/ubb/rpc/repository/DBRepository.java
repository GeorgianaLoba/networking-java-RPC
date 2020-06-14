package ro.ubb.rpc.repository;


import ro.ubb.rpc.domain.BaseEntity;
import ro.ubb.rpc.domain.exceptions.ValidatorException;
import ro.ubb.rpc.domain.validators.Validator;
import ro.ubb.rpc.repository.adapters.Adapter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;

public class DBRepository<ID extends Serializable, T extends BaseEntity<ID>> implements InterfaceRepository<ID, T> {
    private Validator<T> validator;
    private Connection connection;
    private Adapter<ID, T> adapter;

    public DBRepository(Validator<T> validator, Connection connection, Adapter<ID, T> adapter) {
        this.validator = validator;
        this.connection = connection;
        this.adapter = adapter;
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("The ID must not be null");
        }
        try {
            return adapter.findOne(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<T> findALL() {
        try {
            return new HashSet<>(adapter.findAll(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException{
        if (entity == null) {
            throw new IllegalArgumentException("The entity must not be null");
        }
        validator.validate(entity);
        try {
            return adapter.add(connection, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("The ID must not be null");
        }
        try {
            return adapter.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("The entity must not be null");
        }
        validator.validate(entity);
        try {
            return adapter.update(connection, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
