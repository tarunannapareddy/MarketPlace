package Handlers;

import Mappers.RequestHandlerMapper;
import Pojos.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Operation operation = (Operation) objectInputStream.readObject();
            Object request = objectInputStream.readObject();
            System.out.println(operation+" received request "+request);
            objectOutputStream.writeObject(RequestHandlerMapper.getRequestHandler(operation).handle(request));

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
