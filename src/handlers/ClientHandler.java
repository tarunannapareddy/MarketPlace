package handlers;

import mappers.RequestHandlerMapper;
import pojos.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private boolean loggedIn;

    private ClientSessionTimer clientSessionTimer;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        loggedIn = false;
        clientSessionTimer = new ClientSessionTimer();
    }
    public void run() {
        try {
            System.out.println("Client connected to the thread "+Thread.currentThread().getId());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientSessionTimer.startSessionTimeoutTimer(objectOutputStream);
            while(true) {
                Operation operation = (Operation) objectInputStream.readObject();
                System.out.println("executing request Operation "+operation);
                clientSessionTimer.resetSessionTimeoutTimer(objectOutputStream, clientSocket);
                if (!loggedIn && !Operation.LOGIN.equals(operation)) {
                    objectOutputStream.writeObject("Please LogIn to the System");
                    break;
                } else if(Operation.LOGIN.equals(operation)){
                    loggedIn = true;
                }
                if(Operation.LOGOUT.equals(operation)){
                    break;
                }
                Object request = objectInputStream.readObject();
                System.out.println(operation + " received request " + request);
                objectOutputStream.writeObject(RequestHandlerMapper.getRequestHandler(operation).handle(request));
            }
            objectOutputStream.writeObject("closing the connection");
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
