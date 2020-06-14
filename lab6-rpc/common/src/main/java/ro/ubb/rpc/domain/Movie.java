package ro.ubb.rpc.domain;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

public class Movie extends BaseEntity<Long> {
    private String title, director;
    public Integer imdbRating;
    private Integer releaseYear;

    public Movie(){
    }

    public Movie(String title, String director, Integer imdbRating, Integer releaseYear) {
        this.title = title;
        this.director = director;
        this.imdbRating = imdbRating;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Integer imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


    public static String stringFromMovie(Movie movie){
        String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(movie.getId().toString()+separator);
        sb.append(movie.getTitle()+separator);
        sb.append(movie.getDirector()+separator);
        sb.append(movie.getImdbRating().toString()+separator);
        sb.append(movie.getReleaseYear().toString());
        return sb.toString();
    }

    public static Movie movieFromString(String str){
        Movie movie = new Movie();
        String[] args = str.split(",");
        movie.setId(Long.parseLong(args[0]));
        movie.setTitle(args[1]);
        movie.setDirector(args[2]);
        movie.setImdbRating(Integer.parseInt(args[3]));
        movie.setReleaseYear(Integer.parseInt(args[4]));
        return movie;
    }


    public static String stringFromSetMovies(Set<Movie> movies){
        return movies.stream().map(Movie::stringFromMovie).reduce("",(f,s)->f+";"+s);
    }

    public static Set<Movie> movieSetFromString(String str){
        String[] mov = str.split(";");
        Set<Movie> movies= new HashSet<>();
        for (String m: mov) if (m.length()> 2) movies.add(Movie.movieFromString(m));
        return movies;
    }

    @Override
    public String toString() {
        return "Movie{" + "title= " + this.title + ", director= " + this.director +
                 ", imdbRating= " + this.imdbRating + ", releaseYear=" + this.releaseYear
                + "} " + super.toString();

    }
}
