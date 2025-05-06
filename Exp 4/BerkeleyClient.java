import java.io.*;
import java.net.*;
import java.util.*;

public class BerkeleyClient {
    private static final String SERVER_IP = "localhost"; // Change if master is on a different machine
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        long localTime = System.currentTimeMillis();
        System.out.println("Client time before adjustment: " + new Date(localTime));
        out.writeObject(localTime);

        long offset = (Long) in.readObject();
        Date adjustedTime = new Date(localTime + offset);
        System.out.println("Client adjusted time: " + adjustedTime);

        socket.close();
    }
}
