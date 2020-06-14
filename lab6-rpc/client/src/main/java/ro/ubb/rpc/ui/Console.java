package ro.ubb.rpc.ui;

import ro.ubb.rpc.domain.Client;
import ro.ubb.rpc.domain.Movie;
import ro.ubb.rpc.domain.Rental;
import ro.ubb.rpc.service.CommonClientService;
import ro.ubb.rpc.service.CommonMovieService;
import ro.ubb.rpc.service.CommonRentalService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Console {
    private CommonMovieService commonMovieService;
    private CommonRentalService commonRentalService;
    private CommonClientService commonClientService;

    public Console(CommonMovieService commonMovieService, CommonRentalService commonRentalService, CommonClientService commonClientService) {
        this.commonMovieService = commonMovieService;
        this.commonRentalService = commonRentalService;
        this.commonClientService = commonClientService;
    }

    private void printMenu(){
        System.out.println("What do u wanna do: ");
        System.out.println("Press 1 to add movie.");
        System.out.println("Press 2 to add client.");
        System.out.println("Press 3 to add rental.");
        System.out.println("Press 4 to delete movie.");
        System.out.println("Press 5 to delete client.");
        System.out.println("Press 6 to delete rental.");
        System.out.println("Press 7 to update movie.");
        System.out.println("Press 8 to update client.");
        System.out.println("Press 9 to update rental.");
        System.out.println("Press 10 to print all movies.");
        System.out.println("Press 11 to print all clients.");
        System.out.println("Press 12 to print all rentals.");
        System.out.println("Press 0 to exit");
    }


    public void runConsole(){
        Scanner scanner = new Scanner(System.in);
        printMenu();
        boolean running = true;
        while (running){
            try {
                int input = scanner.nextInt();
                switch (input) { //TODO
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        addClient();
                        break;
                    case 3:
                        rentMovie();
                        break;
                    case 4:
                        deleteMovie();
                        break;
                    case 5:
                        deleteClient();
                        break;
                    case 6:
                        deleteRental();
                        break;

                    case 7:
                        updateMovie();
                        break;
                    case 8:
                        updateClient();
                        break;
                    case 9:
                        updateRental();
                        break;
                    case 10:
                        getAllMovies();
                        break;
                    case 11:
                        getAllClients();
                        break;
                    case 12:
                        getAllRentals();
                        break;
                    case 0:
                        running = false;
                        break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            printMenu();
        }
        System.out.println("seeya!");
    }



    private void addMovie()  {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Id: ");
        Long id = scanner.nextLong();
        System.out.println("Title: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Director: ");
        String director = scanner.nextLine();
        System.out.println("ImdbRating: ");
        Integer imdbRating = scanner.nextInt();
        System.out.println("ReleaseYear: ");
        Integer releaseYear = scanner.nextInt();

        Movie movie = new Movie(title, director, imdbRating, releaseYear);
        movie.setId(id);
        commonMovieService.addMovie(movie);
    }
    private void deleteMovie() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the id of the movie you want to delete: ");
        Long id = scanner.nextLong();
        commonRentalService.deleteRentalsOfMovie(id); //here for checking if it actually works after setting the handlers
        commonMovieService.deleteMovie(id);

    }
    private void updateMovie() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = scanner.nextLong();
        System.out.println("Title: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Director: ");
        String director = scanner.nextLine();
        System.out.println("ImdbRating: ");
        Integer imdbRating = scanner.nextInt();
        System.out.println("ReleaseYear: ");
        Integer releaseYear = scanner.nextInt();
        Movie movie=new Movie(title,director,imdbRating,releaseYear);
        movie.setId(id);
        commonMovieService.updateMovie(movie);

    }
    private void addClient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = scanner.nextLong();
        System.out.println("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Age: ");
        Integer age = scanner.nextInt();
        Client client=new Client(name,address,age);
        client.setId(id);
        commonClientService.addClient(client);

    }

    private void updateClient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = scanner.nextLong();
        System.out.println("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Age: ");
        Integer age = scanner.nextInt();
        Client client=new Client(name,address,age);
        client.setId(id);
        commonClientService.updateClient(client);

    }
    private void deleteClient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the id of the client you want to delete: ");
        Long id = scanner.nextLong();
        commonRentalService.deleteRentalsofClient(id);
        commonClientService.deleteClient(id);
    }

    private void rentMovie() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the id: ");
        Long id = scanner.nextLong();
        System.out.println("Enter the id of the movie you want to rent: ");
        Long movieId = scanner.nextLong();
        System.out.println("Enter your client id: ");
        Long clientId = scanner.nextLong();
        Rental rental=new Rental(movieId,clientId);
        rental.setId(id);
        commonRentalService.rentMovie(rental);

    }
    private void deleteRental() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the id of the rental you want to delete: ");
        Long id = scanner.nextLong();
        commonRentalService.deleteRental(id);

    }

    private void updateRental() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the id: ");
        Long id = scanner.nextLong();
        System.out.println("Enter the id of the movie you want to rent: ");
        Long movieId = scanner.nextLong();
        System.out.println("Enter your client id: ");
        Long clientId = scanner.nextLong();
        Rental rental=new Rental(movieId,clientId);
        rental.setId(id);
        commonRentalService.updateRental(rental);
    }

    private void getAllMovies() throws SQLException {
        commonMovieService.getAll().thenAcceptAsync(movies->movies.forEach(System.out::println));
    }
    private void getAllClients() throws SQLException {
        commonClientService.getAllClients().thenAcceptAsync(clients->clients.forEach(System.out::println));
    }

    private void getAllRentals() throws SQLException {
        commonRentalService.getAllRentals().thenAcceptAsync(rentals->rentals.forEach(System.out::println));
    }

}
