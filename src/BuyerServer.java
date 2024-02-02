import handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyerServer {

    public static void main(String[] args) {
        int THREAD_POOL_COUNT =200;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
            System.out.println("Buyer Server is waiting for client connections...");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Buyer Client connected.");
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
