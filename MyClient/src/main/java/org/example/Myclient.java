package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;


public class Myclient {
    public static void main(String[] args) throws IOException {
        try {
            String serverAddress = "127.0.0.1";
            Socket socket = new Socket(serverAddress, 5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String[] values = {"rock", "paper", "scissors", "quit", "history"};
            String[] options = {"1", "2", "3"};


            System.out.println(reader.readLine());

           String serverchoice = userInput.readLine();
           while (! serverchoice.toLowerCase().equals("rmi")&& ! serverchoice.toLowerCase().equals("rpc")){
               serverchoice = userInput.readLine();
           }
                   writer.println(serverchoice);
                   writer.flush();


            while (true) {

                System.out.println(reader.readLine());
                String playeroption = userInput.readLine().toLowerCase();
                while (!Arrays.stream(options).anyMatch(playeroption::equals)) {
                    System.out.println("Please enter a number");
                    playeroption = userInput.readLine().toLowerCase();
                }

                if (playeroption.equals("3")) {
                    System.out.println("Session finished");

                    socket.isClosed();
                    break;

                }

                writer.println(playeroption);
                writer.flush();
                if (playeroption.equals("2")) {

                    String line;

                    while ((line = reader.readLine() )!= null){
                        System.out.println(line);
                        if (line.trim().isEmpty()){
                            break;
                        }
                    }



                }
                if (playeroption.equals("1")) {
                    System.out.println(reader.readLine());


                    for (int i = 0; i < 3; i++) {


                        String playerChoice = userInput.readLine().toLowerCase();
                        while (!Arrays.stream(values).anyMatch(playerChoice::equals)) {
                            System.out.println("Please enter your choice: rock, paper, or scissors");
                            playerChoice = userInput.readLine().toLowerCase();
                        }
                        writer.println(playerChoice);
                        writer.flush();
                        System.out.println(reader.readLine());
                    }


                    System.out.println(reader.readLine());

                }


            }
            socket.isClosed();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }}