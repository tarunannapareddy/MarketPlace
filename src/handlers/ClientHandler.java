package handlers;

import mappers.RequestHandlerMapper;
import pojos.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private ServerSocket serverSocket;

    public ClientHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void run() {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            while(true) {
                System.out.println("executing request");
                Operation operation = (Operation) objectInputStream.readObject();
                if(Operation.LOGOUT.equals(operation)){
                    break;
                }
                Object request = objectInputStream.readObject();
                System.out.println(operation + " received request " + request);
                objectOutputStream.writeObject(RequestHandlerMapper.getRequestHandler(operation).handle(request));
            }
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
