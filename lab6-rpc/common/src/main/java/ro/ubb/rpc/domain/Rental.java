package ro.ubb.rpc.domain;

import java.util.HashSet;
import java.util.Set;

public class Rental extends BaseEntity<Long> {
    private Long movieId, clientId;

    public Rental(){

    }

    //made changes to type of movieID and clientID
    //from Integer->Long

    public Rental(Long movieId, Long clientId) {
        this.movieId = movieId;
        this.clientId = clientId;
    }


    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "movieId=" + movieId +
                ", clientId=" + clientId +
                "} " + super.toString();
    }



    public static String stringFromRental(Rental rental){
        String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(rental.getId().toString()+separator);
        sb.append(rental.getMovieId().toString()+separator);
        sb.append(rental.getClientId().toString());

        return sb.toString();
    }

    public static String stringFromSetRentals(Set<Rental> rentals){
        return rentals.stream().map(Rental::stringFromRental).reduce("",(f,s)->f+";"+s);
    }

    public static Set<Rental> rentalSetFromString(String str){
        String[] rent = str.split(";");
        Set<Rental> rentals= new HashSet<>();
        for (String r:rent) if (r.length()> 2) rentals.add(Rental.rentalFromString(r));
        return rentals;
    }
    public static Rental rentalFromString(String str){
        Rental rental = new Rental();
        String[] args = str.split(",");
        rental.setId(Long.parseLong(args[0]));
        rental.setMovieId(Long.parseLong(args[1]));
        rental.setClientId(Long.parseLong(args[2]));

        return rental;
    }
}
