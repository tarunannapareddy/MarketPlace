import pojos.Item;
import pojos.Operation;
import pojos.Request.*;
import pojos.UserType;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SellerTest {

    public static List<String> selectedCategories = Arrays.asList("phone","mobile","tv", "ac", "earphones","laptop","monitor","desktop" );

    static class SellerThread extends Thread {
        private final String sellerName;
        private final String password;
        private final int iterations;
        private final AtomicLong[] totalTimes;

        public SellerThread(String sellerName, String password, int iterations, AtomicLong[] totalTimes) {
            this.sellerName = sellerName;
            this.password = password;
            this.iterations = iterations;
            this.totalTimes = totalTimes;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket("localhost", 8083);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

                sell(sellerName, password, objectOutputStream, objectInputStream, iterations, totalTimes);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sell(String userName, String password, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, int iterations, AtomicLong[] totalTimes) throws IOException, ClassNotFoundException, InterruptedException {
        Random random = new Random();

        long totalLoginTime = 0;
        long totalRatingTime = 0;
        long totalAddItemTime = 0;
        long totalUpdateItemTime = 0;
        long totalRemoveItemTime = 0;

        Object request = new CreateAccountRequest(userName, password, UserType.SELLER, userName);
        objectOutputStream.writeObject(Operation.CREATE_ACCOUNT);
        objectOutputStream.writeObject(request);
        System.out.println("create account status for user"+userName+"is "+objectInputStream.readObject());

        for (int i = 0; i < iterations; i++) {
            //login
            long startTime = System.currentTimeMillis();
            request = new LogInRequest(userName, password);
            objectOutputStream.writeObject(Operation.LOGIN);
            objectOutputStream.writeObject(request);
            int id = (int) objectInputStream.readObject();
            long endTime = System.currentTimeMillis();
            totalLoginTime += (endTime - startTime);
            System.out.println("userLoggedIn " + id);

            Thread.sleep(100);
            // get Rating
            startTime = System.currentTimeMillis();
            request = id;
            objectOutputStream.writeObject(Operation.SELLER_RATING);
            objectOutputStream.writeObject(request);
            Object rating = objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalRatingTime += (endTime - startTime);
            System.out.println("received Rating for Seller" + id + " is " + rating);

            Thread.sleep(100);
            // add Item
            String category = selectedCategories.get(random.nextInt(selectedCategories.size()));
            String name = category+random.nextInt();
            startTime = System.currentTimeMillis();
            request = new Item(name, 3, "NEW", 15, (double)random.nextInt(1000), id, new String[]{category});
            objectOutputStream.writeObject(Operation.SELLER_ADD_ITEM);
            objectOutputStream.writeObject(request);
            String itemId = (String) objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalAddItemTime += (endTime - startTime);
            System.out.println("Seller add Item for " + id + " is " + itemId);

            Thread.sleep(100);
            // update Item
            startTime = System.currentTimeMillis();
            request = new Item(itemId, id, (double)random.nextInt(1000));
            objectOutputStream.writeObject(Operation.SELLER_UPDATE_ITEM);
            objectOutputStream.writeObject(request);
            boolean status = (boolean) objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalUpdateItemTime += (endTime - startTime);
            System.out.println("Seller update Item for " + id + " status is " + status);

            Thread.sleep(100);
            // remove Item
            startTime = System.currentTimeMillis();
            request = new RemoveItemRequest(itemId, id, 2);
            objectOutputStream.writeObject(Operation.SELLER_REMOVE_ITEM);
            objectOutputStream.writeObject(request);
            Object deleteStatus =  objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalRemoveItemTime += (endTime - startTime);
            System.out.println("Seller remove Item for " + id + " status is " + deleteStatus);
            Thread.sleep(1000);
        }

        // Calculate and print average times
        totalTimes[0].addAndGet(totalRatingTime);
        totalTimes[1].addAndGet(totalAddItemTime);
        totalTimes[2].addAndGet(totalUpdateItemTime);
        totalTimes[3].addAndGet(totalRemoveItemTime);

        System.out.println("Average SELLER_RATING time: " + ((double)totalRatingTime / iterations) + " milliseconds");
        System.out.println("Average SELLER_ADD_ITEM time: " + ((double)totalAddItemTime / iterations) + " milliseconds");
        System.out.println("Average SELLER_UPDATE_ITEM time: " + ((double)totalUpdateItemTime / iterations) + " milliseconds");
        System.out.println("Average SELLER_REMOVE_ITEM time: " + ((double)totalRemoveItemTime / iterations) + " milliseconds");
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        int numThreads = 100;
        int iterationsPerThread = 10;
        Thread[] threads = new Thread[numThreads];
        AtomicLong[] totalTimes = new AtomicLong[4]; // One for each seller operation

        for (int i = 0; i < 4; i++) {
            totalTimes[i] = new AtomicLong(0);
        }

        Thread.sleep(5000);
        for (int i = 0; i < numThreads; i++) {
            String userName = "seller_"+i;
            String password = "seller_"+i;
            threads[i] = new SellerThread(userName, password, iterationsPerThread, totalTimes);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Calculate and print average times

        System.out.println("Total Average SELLER_RATING time: " + ((double) totalTimes[0].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average SELLER_ADD_ITEM time: " + ((double) totalTimes[1].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average SELLER_UPDATE_ITEM time: " + ((double) totalTimes[2].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average SELLER_REMOVE_ITEM time: " + ((double) totalTimes[3].get() / (numThreads * iterationsPerThread)) + " milliseconds");
    }
}
