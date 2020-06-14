package ro.ubb.rpc.domain.validators;


import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.exceptions.ValidatorException;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        if (entity.getName().equals("")) throw new ValidatorException("Name cannot be null");
        if (entity.getAge()<18) throw new ValidatorException("Client must be over 18 years old");
    }
}
