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

public class BuyerTest {

    public static List<String> selectedCategories = Arrays.asList("phone","mobile","tv", "ac", "earphones","laptop","monitor","desktop" );
    static class BuyerThread extends Thread {
        private final String userName;
        private final String password;
        private final int iterations;
        private final AtomicLong[] totalTimes;

        public BuyerThread(String userName, String password, int iterations, AtomicLong[] totalTimes) {
            this.userName = userName;
            this.password = password;
            this.iterations = iterations;
            this.totalTimes = totalTimes;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket("localhost", 8082);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

                buy(userName, password, objectOutputStream, objectInputStream, iterations, totalTimes);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void buy(String userName, String password, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, int iterations, AtomicLong[] totalTimes) throws IOException, ClassNotFoundException, InterruptedException {
        Random random = new Random();

        long totalLoginTime = 0;
        long totalSearchTime = 0;
        long totalRateItemTime = 0;
        long totalUpdateCartTime = 0;
        long totalResetCartTime = 0;

        Object request = new CreateAccountRequest(userName, password, UserType.BUYER, userName);
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

            //search
            String category = selectedCategories.get(random.nextInt(selectedCategories.size()));
            startTime = System.currentTimeMillis();
            request = new SearchItemRequest(3, new String[]{category});
            objectOutputStream.writeObject(Operation.SEARCH_ITEM);
            objectOutputStream.writeObject(request);
            List<Item> itemList = (List<Item>) objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalSearchTime += (endTime - startTime);
            System.out.println("received Items " + itemList.size());
            Thread.sleep(100);

            if (!itemList.isEmpty()) {
                // Randomly select an item for RateItem and UpdateCart operations
                Item randomItem = itemList.get(random.nextInt(itemList.size()));

                // RateItem
                startTime = System.currentTimeMillis();
                request = new RateItemRequest(id, randomItem.getItemId(), Math.random() < 0.5);
                objectOutputStream.writeObject(Operation.RATE_ITEM);
                objectOutputStream.writeObject(request);
                boolean rateItemStatus = (boolean) objectInputStream.readObject();
                endTime = System.currentTimeMillis();
                totalRateItemTime += (endTime - startTime);
                System.out.println("RateItem status for account " + id + " item " + randomItem.getItemId() + " is " + rateItemStatus);
                Thread.sleep(100);

                // Update Cart
                startTime = System.currentTimeMillis();
                request = new UpdateCartRequest(id, randomItem.getItemId(), 1);
                objectOutputStream.writeObject(Operation.UPDATE_CART);
                objectOutputStream.writeObject(request);
                boolean updateCartStatus = (boolean) objectInputStream.readObject();
                endTime = System.currentTimeMillis();
                totalUpdateCartTime += (endTime - startTime);
                System.out.println("UpdateCart status for account " + id + " item " + randomItem.getItemId() + " is " + updateCartStatus);
            }
            Thread.sleep(100);
            // Reset Cart
            startTime = System.currentTimeMillis();
            request = new UpdateCartRequest(id, true);
            objectOutputStream.writeObject(Operation.UPDATE_CART);
            objectOutputStream.writeObject(request);
            boolean resetStatus = (boolean) objectInputStream.readObject();
            endTime = System.currentTimeMillis();
            totalResetCartTime += (endTime - startTime);
            System.out.println("Reset cart status for account " + id + " is " + resetStatus);
            Thread.sleep(1000);
        }

        totalTimes[0].addAndGet(totalLoginTime);
        totalTimes[1].addAndGet(totalSearchTime);
        totalTimes[2].addAndGet(totalRateItemTime);
        totalTimes[3].addAndGet(totalUpdateCartTime);
        totalTimes[4].addAndGet(totalResetCartTime);

        // Calculate and print average times
        System.out.println("Average LOGIN time: " + ((double)totalLoginTime / iterations) + " milliseconds");
        System.out.println("Average SEARCH_ITEM time: " + ((double)totalSearchTime / iterations) + " milliseconds");
        System.out.println("Average RATE_ITEM time: " + ((double)totalRateItemTime / iterations) + " milliseconds");
        System.out.println("Average UPDATE_CART time: " + ((double)totalUpdateCartTime / iterations) + " milliseconds");
        System.out.println("Average RESET_CART time: " + ((double)totalResetCartTime / iterations) + " milliseconds");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        int numThreads = 100;
        int iterationsPerThread = 10;
        Thread[] threads = new Thread[numThreads];
        AtomicLong[] totalTimes = new AtomicLong[5]; // One for each operation

        for (int i = 0; i < 5; i++) {
            totalTimes[i] = new AtomicLong(0);
        }

        for (int i = 0; i < numThreads; i++) {
            String userName = "buyer_"+i;
            String password = "buyer_"+i;
            threads[i] = new BuyerThread(userName, password, iterationsPerThread, totalTimes);
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

        System.out.println("Total Average LOGIN time: " + ((double) totalTimes[0].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average SEARCH_ITEM time: " + ((double) totalTimes[1].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average RATE_ITEM time: " + ((double) totalTimes[2].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average UPDATE_CART time: " + ((double) totalTimes[3].get() / (numThreads * iterationsPerThread)) + " milliseconds");
        System.out.println("Total Average RESET_CART time: " + ((double) totalTimes[4].get() / (numThreads * iterationsPerThread)) + " milliseconds");
    }
}
