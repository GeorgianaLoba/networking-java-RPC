package ro.ubb.rpc.domain.validators;


import ro.ubb.rpc.domain.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
