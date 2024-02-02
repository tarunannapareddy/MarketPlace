import pojos.Item;
import pojos.Operation;
import pojos.Request.LogInRequest;
import pojos.Request.RateItemRequest;
import pojos.Request.SearchItemRequest;
import pojos.Request.UpdateCartRequest;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class BuyerClient {
    public static void buy(String userName, String password, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException, InterruptedException {

        //login
        Object request = new LogInRequest(userName, password);
        objectOutputStream.writeObject(Operation.LOGIN);
        objectOutputStream.writeObject(request);
        int id = (int) objectInputStream.readObject();
        System.out.println("userLoggedIn " + id);

        //search
        request = new SearchItemRequest(3, new String[]{"steel"});
        objectOutputStream.writeObject(Operation.SEARCH_ITEM);
        objectOutputStream.writeObject(request);
        List<Item> itemList = (List<Item>) objectInputStream.readObject();
        System.out.println("received Items " + itemList.size());


        for(String item : itemList.stream().map(item-> item.getItemId()).collect(Collectors.toList())){
            request = new RateItemRequest(id, item, true);
            objectOutputStream.writeObject(Operation.RATE_ITEM);
            objectOutputStream.writeObject(request);
            boolean status = (boolean) objectInputStream.readObject();
            System.out.println("update cart status for account "+id+" item  "+ item +" is "+status);
        }

        //Update Cart
        for(String item : itemList.stream().map(item-> item.getItemId()).collect(Collectors.toList())){
            request = new UpdateCartRequest(id, item, 1);
            objectOutputStream.writeObject(Operation.UPDATE_CART);
            objectOutputStream.writeObject(request);
            boolean status = (boolean) objectInputStream.readObject();
            System.out.println("update cart status for account "+id+" item  "+ item +" is "+status);
        }

        //Reset Cart
        request = new UpdateCartRequest(id, true);
        objectOutputStream.writeObject(Operation.UPDATE_CART);
        objectOutputStream.writeObject(request);
        boolean status = (boolean) objectInputStream.readObject();
        System.out.println("Reset cart status for account "+id+" is "+status);

    }

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            Socket socket = new Socket("localhost", 8082);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int count =0;

            while(count <1){
                buy("test_buyer", "test123", objectOutputStream, objectInputStream);

                count++;
            }

            objectInputStream.close();
            objectOutputStream.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
