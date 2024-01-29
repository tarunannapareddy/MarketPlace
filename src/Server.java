import handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int THREAD_POOL_COUNT =5;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            System.out.println("Server is waiting for client connections...");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}