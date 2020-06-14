package ro.ubb.rpc.domain.validators;

import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.domain.exceptions.ValidatorException;

public class RentalValidator implements Validator<Rental> {
    @Override
    public void validate(Rental entity) throws ValidatorException {
        //TODO: nothing to validate for now
    }
}
