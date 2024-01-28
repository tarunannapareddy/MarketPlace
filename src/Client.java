import pojos.Item;
import pojos.Request.*;
import pojos.Operation;
import pojos.UserType;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8081);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object request = null;
            while(true) {
                Operation operation = Operation.valueOf(userInput.readLine());
                if (Operation.CREATE_ACCOUNT.equals(operation)) {
                    request = new CreateAccountRequest("test54", "test123", UserType.BUYER, "tarun_test");
                    //request = new CreateAccountRequest("test", "test123", UserType.SELLER, "tarun_seller");
                }else if (Operation.LOGIN.equals(operation)) {
                    request = new LogInRequest("test54", "test123");
                } else if (Operation.SELLER_RATING.equals(operation)) {
                    request = 10;
                } else if (Operation.SELLER_ADD_ITEM.equals(operation)) {
                    request = new Item("steelbottle", 3, "NEW", 15, 22.5, 10, new String[]{"steel", "test", "working"});
                } else if (Operation.SELLER_UPDATE_ITEM.equals(operation)) {
                    request = new Item(userInput.readLine(), 21.2);
                } else if (Operation.SELLER_REMOVE_ITEM.equals(operation)) {
                    request = new RemoveItemRequest(userInput.readLine(), 2);
                } else if (Operation.RATE_ITEM.equals(operation)) {
                    request = new RateItemRequest(userInput.readLine(), true);
                } else if(Operation.SEARCH_ITEM.equals(operation)){
                    request = new SearchItemRequest(3, new String []{"xyz", "steel"});
                } else if(Operation.SELLER_GET_ITEMS.equals(operation)){
                    request = 10;
                }else {
                    break;
                }
                System.out.println(operation + " input " + request);
                objectOutputStream.writeObject(operation);
                objectOutputStream.writeObject(request);
                Object result = objectInputStream.readObject();
                System.out.println("received result " + result);
            }
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
