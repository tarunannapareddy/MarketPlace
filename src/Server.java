import handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int count =1;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            System.out.println("Server is waiting for client connections...");

            while(count>0) {
                executorService.execute(new ClientHandler(serverSocket));
                count--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}