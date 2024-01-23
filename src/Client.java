import Pojos.Request.LogInRequest;
import Pojos.Operation;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8081);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            Object request = null;
            Operation operation = Operation.valueOf(userInput.readLine());
            if(Operation.LOGIN.equals(operation) || Operation.CREATE_ACCOUNT.equals(operation)) {
                request= new LogInRequest(userInput.readLine(), userInput.readLine());
            }
            System.out.println(operation+" input "+request);


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(operation);
            objectOutputStream.writeObject(request);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("received result "+objectInputStream.readObject());

            objectInputStream.close();
            objectOutputStream.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
