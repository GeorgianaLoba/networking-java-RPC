package ro.ubb.rpc;

import ro.ubb.rpc.service.*;
import ro.ubb.rpc.ui.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ConnectionClient connectionClient=new ConnectionClient();
        CommonMovieService commonMovieService = new ClientMovieService(executorService, connectionClient);
        CommonRentalService commonRentalService=new ClientRentalService(executorService,connectionClient);
        CommonClientService commonClientService=new ClientClientService(executorService,connectionClient);
        Console console = new Console(commonMovieService,commonRentalService,commonClientService);
        console.runConsole();

        executorService.shutdown();
        System.out.println("A client has left...");
    }
}
