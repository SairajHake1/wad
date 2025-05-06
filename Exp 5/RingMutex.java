import java.util.*;

public class RingMutex {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the number of nodes in the ring: ");
        int n = scan.nextInt();

        int token = 0;  // Token starts at process 0
        int choice = 0;

        System.out.print("Ring order: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + (i == n - 1 ? " → " + 0 : " → "));
        }
        System.out.println();

        do {
            System.out.print("\nEnter process ID requesting to enter Critical Section (0 to " + (n - 1) + "): ");
            int request = scan.nextInt();

            if (request < 0 || request >= n) {
                System.out.println("Invalid process ID! Try again.");
                continue;
            }

            System.out.print("Passing token: ");
            int i = token;
            while (i != request) {
                System.out.print(i + " → ");
                i = (i + 1) % n;
            }
            System.out.println(request);

            // Process enters CS
            System.out.println("Process " + request + " enters the Critical Section.");
            System.out.println("Process " + request + " exits the Critical Section.");

            // Token now with the requesting process
            token = request;

            // Ask to continue
            System.out.print("Do you want another process to request CS? (1 = Yes, 0 = No): ");
            choice = scan.nextInt();

        } while (choice == 1);

        System.out.println("Simulation ended.");
    }
}
