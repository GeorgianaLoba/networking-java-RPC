package ro.ubb.rpc.domain.validators;


import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.domain.exceptions.ValidatorException;

public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        if (entity.getTitle().equals("")) throw new ValidatorException("Title cannot be null.");
        if (entity.getDirector().equals("")) throw new ValidatorException("Director cannot be null.");
        if (entity.getImdbRating()<5) throw new ValidatorException("This movie is too bad; we don't want to keep it.");
    }
}
