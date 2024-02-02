package dbConnector;

import pojos.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectorSocket {
    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String USER = "postgres";
    private static final String PASSWORD = "tarun1997";

    private static DBConnection customerDBConnection;

    private static DBConnection productDBConnection;

    public synchronized static DBConnection getCustomerConnection() {
        if(customerDBConnection == null){
            try (Socket socket = new Socket(HOST, PORT)){
                OutputStream customerDBConnection = socket.getOutputStream();
                BufferedReader customerReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                sendStartupMessage(customerDBConnection, "customer", USER, PASSWORD);
                return new DBConnection(customerDBConnection, customerReader);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return customerDBConnection;
    }

    public synchronized static DBConnection getProductConnection() {
        if(productDBConnection == null){
            try (Socket socket = new Socket(HOST, PORT)){
                OutputStream productDBConnection = socket.getOutputStream();
                BufferedReader productReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                sendStartupMessage(productDBConnection, "product", USER, PASSWORD);
                return new DBConnection(productDBConnection, productReader);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return productDBConnection;
    }


    private static void sendStartupMessage(OutputStream out, String database, String user, String password) throws IOException {
        String startupMessage = "user=" + user + "\0" + "database=" + database + "\0" + "password=" + password + "\0";
        out.write(startupMessage.getBytes());
    }

}
