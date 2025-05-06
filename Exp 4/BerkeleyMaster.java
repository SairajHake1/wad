import java.io.*;
import java.net.*;
import java.util.*;

public class BerkeleyMaster {
    private static final int PORT = 9876;
    private static final int NUM_CLIENTS = 2; // Adjust based on how many clients you run

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        List<Long> offsets = new ArrayList<>();
        List<ObjectOutputStream> clientStreams = new ArrayList<>();
        long masterTime = System.currentTimeMillis();

        System.out.println("Master time: " + new Date(masterTime));

        // Receive time from all clients
        for (int i = 0; i < NUM_CLIENTS; i++) {
            Socket clientSocket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            long clientTime = (Long) in.readObject();
            long offset = clientTime - masterTime;
            offsets.add(offset);
            clientStreams.add(out);

            System.out.println("Received client time: " + new Date(clientTime) + " | Offset: " + offset + " ms");
        }

        // Include master's own offset (0)
        offsets.add(0L);

        // Compute average offset
        long sum = 0;
        for (long offset : offsets) {
            sum += offset;
        }
        long avgOffset = sum / offsets.size();
        System.out.println("Average offset: " + avgOffset + " ms");

        // Send adjustment to clients
        for (ObjectOutputStream out : clientStreams) {
            out.writeObject(avgOffset);
        }

        // Adjust master's own clock
        Date adjustedMaster = new Date(masterTime + avgOffset);
        System.out.println("Adjusted master time: " + adjustedMaster);

        serverSocket.close();
    }
}
