import pojos.Item;
import pojos.Request.*;
import pojos.Operation;
import pojos.UserType;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {

    private static BlockingQueue<Object> responseQueue = new LinkedBlockingQueue<>();

    private static boolean requestSent = false;
    private static Thread getUserInputThread(BufferedReader userInput, ObjectOutputStream objectOutputStream){
        Thread userInputThread = new Thread(() -> {
            try {
                Object request;
                while (true) {
                    Operation operation = Operation.valueOf(userInput.readLine());
                    if (Operation.CREATE_ACCOUNT.equals(operation)) {
                        request = new CreateAccountRequest("test54", "test123", UserType.BUYER, "tarun_test");
                    } else if (Operation.LOGIN.equals(operation)) {
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
                        request = new RateItemRequest(11, userInput.readLine(), true);
                    } else if (Operation.SEARCH_ITEM.equals(operation)) {
                        request = new SearchItemRequest(3, new String[]{"xyz", "steel", "plastic"});
                    } else if (Operation.SELLER_GET_ITEMS.equals(operation)) {
                        request = 10;
                    } else if (Operation.UPDATE_CART.equals(operation)) {
                        if (userInput.readLine().equals("update")) {
                            request = new UpdateCartRequest(11, userInput.readLine(), 100);
                        } else {
                            request = new UpdateCartRequest(11, true);
                        }
                    } else {
                        break;
                    }

                    System.out.println(operation + " input " + request);
                    objectOutputStream.writeObject(operation);
                    objectOutputStream.writeObject(request);
                    requestSent = true;
                    Object result = responseQueue.take();
                    System.out.println("Received result " + result);
                    requestSent = false;
                }
                objectOutputStream.close();
                userInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return userInputThread;
    }

    private static Thread getNotificationThread(ObjectInputStream objectInputStream){
        Thread serverNotificationThread = new Thread(() -> {
            try {
                while (true) {
                    Object result = objectInputStream.readObject();
                    if( requestSent) {
                        responseQueue.put(result);
                    }else{
                        System.out.println("Received message from Server "+ result);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return serverNotificationThread;
    }
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8081);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Thread userInputThread = getUserInputThread(userInput,objectOutputStream);
            Thread serverNotificationThread = getNotificationThread(objectInputStream);

            userInputThread.start();
            serverNotificationThread.start();

            userInputThread.join();
            serverNotificationThread.join();

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
