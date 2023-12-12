package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Gateway {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            ServerSocket gatewaySocket = new ServerSocket(5001);
            System.out.println("Gateway listening on port 9090...");

            while (true) {
                Socket clientSocket = gatewaySocket.accept();
                executorService.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            // Connect to the server
            Socket serverSocket = new Socket("localhost", 5004);

            // Create separate threads for handling input and output
            executorService.submit(() -> {
                try {
                    forwardData(clientSocket.getInputStream(), serverSocket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            executorService.submit(() -> {
                try {
                    forwardData(serverSocket.getInputStream(), clientSocket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forwardData(InputStream input, OutputStream output) {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
