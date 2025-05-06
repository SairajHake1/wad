import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorWSClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Accept input from user
        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();
        System.out.print("Enter operation (add, sub, mul, div): ");
        String operation = scanner.next();

        try {
            // Construct the URL for the POST request
            String serviceUrl = "http://localhost:8080/CalculatorWSApplication/ArithmeticOperations";

            // Establish the connection
            URL url = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  // Use POST method
            conn.setDoOutput(true);  // Allow sending data in the request body

            // Prepare the data to send in the body of the POST request
            String data = "No1=" + num1 + "&No2=" + num2 + "&operation=" + operation;

            // Send data
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the input stream and read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Extract the result from the HTML response
            String result = response.toString();
            String operationResult = result.split("<h2>Result:</h2>")[1].split("</body>")[0].replaceAll("<[^>]*>", "").trim();

            // Print the result
            System.out.println("Result: " + operationResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
