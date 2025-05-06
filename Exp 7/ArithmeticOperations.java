//package com.calc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ArithmeticOperations")
public class ArithmeticOperations extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try {
            int no1 = Integer.parseInt(request.getParameter("No1"));
            int no2 = Integer.parseInt(request.getParameter("No2"));
            String operation = request.getParameter("operation");

            pw.println("<html><head><title>Result</title></head><body>");
            pw.println("<h2>Result:</h2>");

            switch (operation) {
                case "add":
                    pw.println("Addition: " + (no1 + no2));
                    break;
                case "sub":
                    pw.println("Subtraction: " + (no1 - no2));
                    break;
                case "mul":
                    pw.println("Multiplication: " + (no1 * no2));
                    break;
                case "div":
                    if (no2 != 0)
                        pw.println("Division: " + (no1 / no2));
                    else
                        pw.println("Division: Cannot divide by zero");
                    break;
                default:
                    pw.println("Invalid operation selected.");
            }

            pw.println("</body></html>");
        } catch (NumberFormatException e) {
            pw.println("<html><body><h2>Error: Please enter valid numbers!</h2></body></html>");
        }
    }
}
