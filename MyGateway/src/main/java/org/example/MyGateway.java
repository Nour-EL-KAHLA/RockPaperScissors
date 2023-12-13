package org.example;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyGateway {
    public static void main(String[] args) throws IOException {
        final int MAX_CLIENTS = 4;
        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);



        try  {
            serverSocket = new ServerSocket(5001);
            System.out.println("Gateway is waiting for connections");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                executorService.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {

        try {

            String uuid = UUID.randomUUID().toString();
            RoundService stub = null;
            try {
                System.out.println("nou");
                stub = (RoundService) Naming.lookup("rmi://localhost:5005/Strong/operations");

                System.out.println("nou");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            try {
                config.setServerURL(new URL("http://localhost:5004/xmlrpc"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            stub.connect(uuid);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.println("Do you want to play with RMI server and RPC server");
            String Serverchoice = null;
            try {
                Serverchoice = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int Score =0;
            int h=1;
            int j =1;
            String gameResult="";
            ArrayList<String> myResult;
            while (j==1) {
                for (int i = 0; i < 3; i++) {
                    writer.println("Enter your choice: rock, paper, or scissors Or you can send quit to quit or history to check your history");
                    String roundchoice = reader.readLine();

                    if (roundchoice.equals("history")) {
                        String history = "";
                        if (Serverchoice.toLowerCase() == "rmi") {
                            h = 0;
                            stub.history(uuid).forEach(x -> {
                                history.concat(x);
                            });
                        } else {
                            Object histo = null;
                            try {
                                histo = client.execute("Round.history", new Object[]{uuid});
                            } catch (XmlRpcException e) {
                                throw new RuntimeException(e);
                            }
                            List<String> hist = convertToArrayList(histo);
                            hist.forEach(x -> {
                                history.concat(x);
                            });
                        }
                        writer.println(history);
                        Score = 0;
                        break;
                    }

                    if (Serverchoice.toLowerCase() == "rmi") {

                        myResult = (ArrayList<String>) stub.playRound(roundchoice, uuid);


                    } else {
                        Object myresult = null;
                        try {
                            myresult = client.execute("Round.playRound", new Object[]{roundchoice, uuid});
                        } catch (XmlRpcException e) {
                            throw new RuntimeException(e);
                        }
                        myResult = convertToArrayList(myresult);
                    }

                    writer.println(myResult.get(0));
                    Score = Score + Integer.parseInt(myResult.get(1));


                    if (j == 1 && h == 1) {
                        if (Serverchoice.toLowerCase() == "rmi") {
                            gameResult = stub.getGameResult(Score, uuid);

                        } else {
                            try {
                                gameResult = (String) client.execute("Round.getGameResult", new Object[]{Score, uuid});
                            } catch (XmlRpcException e) {
                                throw new RuntimeException(e);
                            }
                        }


                        writer.println(gameResult);
                        Score = 0;
                        System.out.println("Let's Play another round \uD83D\uDDFF \uD83D\uDCC4 ✂  ! \n \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1");
                    }
                }


            }} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static ArrayList<String> convertToArrayList(Object result) {
        if (result instanceof ArrayList) {
            return (ArrayList<String>) result;
        } else if (result instanceof Object[]) {
            // Convert array to ArrayList
            List<String> resultList = new ArrayList<>();
            for (Object item : (Object[]) result) {
                resultList.add(String.valueOf(item));
            }
            return new ArrayList<>(resultList);
        } else {
            throw new IllegalArgumentException("Unexpected result type: " + result.getClass().getName());
        }
    }




    }
