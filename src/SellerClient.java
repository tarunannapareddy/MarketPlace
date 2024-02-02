import pojos.Item;
import pojos.Operation;
import pojos.Request.*;

import java.io.*;
import java.net.Socket;

public class SellerClient {

    private static void sell(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        //login
        Object request = new LogInRequest("test", "test123");
        objectOutputStream.writeObject(Operation.LOGIN);
        objectOutputStream.writeObject(request);
        int id = (int) objectInputStream.readObject();
        System.out.println("userLoggedIn " + id);

        // get Rating
        request = id;
        objectOutputStream.writeObject(Operation.SELLER_RATING);
        objectOutputStream.writeObject(request);
        Object rating = objectInputStream.readObject();
        System.out.println("received Rating for Seller" + id + " is " + rating);

        // add Item
        request = new Item("steelbottle", 3, "NEW", 15, 22.5, id, new String[]{"steel", "test", "working"});
        objectOutputStream.writeObject(Operation.SELLER_ADD_ITEM);
        objectOutputStream.writeObject(request);
        String itemId = (String) objectInputStream.readObject();
        System.out.println("Seller add Item for " + id + " is " + itemId);

        // update Item
        request = new Item(itemId, id, 21.2);
        objectOutputStream.writeObject(Operation.SELLER_UPDATE_ITEM);
        objectOutputStream.writeObject(request);
        boolean status = (boolean) objectInputStream.readObject();
        System.out.println("Seller update Item for " + id + " status is " + status);

        // remove Item
        request = new RemoveItemRequest(itemId, id, 2);
        objectOutputStream.writeObject(Operation.SELLER_REMOVE_ITEM);
        objectOutputStream.writeObject(request);
        status = (boolean) objectInputStream.readObject();
        System.out.println("Seller update Item for " + id + " status is " + status);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket socket = new Socket("localhost", 8083);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int count = 0;

            while (count < 1) {
                sell(objectOutputStream, objectInputStream);
                count++;
            }

            objectInputStream.close();
            objectOutputStream.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
