package org.example;



import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import java.util.ArrayList;
public class Client {
    public static void main(String[] args) throws XmlRpcException, MalformedURLException {
      try{
          int Score =0;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:8081/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        String uuid = UUID.randomUUID().toString();
        String srti = (String) client.execute("Round.connect", new Object[]{uuid});
        System.out.println("Let's Play  \uD83D\uDDFF \uD83D\uDCC4 ✂  ! \n \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1");
        Scanner scanner = new Scanner(System.in);
        int j = 1;
        int h=1;

        while (j==1){
            String[] values = {"rock","paper","scissors","quit","history"};
            for(int i =0;i<3;i++){
                h=1;

                System.out.println("Enter your choice: rock, paper, or scissors Or you can send quit to quit or history to check your history");
                String playerChoice = scanner.next().toLowerCase();
                while (!Arrays.stream(values).anyMatch(playerChoice::equals) ){
                    System.out.println("Please enter your choice: rock, paper, or scissors Or you can send quit to quit or history to check your history");
                    playerChoice = scanner.next().toLowerCase();
                }
                if (playerChoice.equals("quit")){
                    Score=0;
                    j=0;
                    break;
                }
                if (playerChoice.equals("history")){
                    h=0;
                    System.out.println("History for user : "+ uuid+"\n");

                    Object histo = client.execute("Round.history", new Object[]{uuid });
                    List<String> hist = convertToArrayList(histo);
                    hist.forEach(x->{
                        System.out.println(x);
                    });
                    Score=0;
                    break;
                }


                Object myresult =  client.execute("Round.playRound",new Object[]{playerChoice,uuid});
                ArrayList<String> myResult = convertToArrayList(myresult);
                System.out.println(myResult.get(0));
                Score = Score + Integer.parseInt(myResult.get(1));
                System.out.println(Score);}
            if(j==1 && h==1){
                String gameResult = (String) client.execute("Round.getGameResult",new Object[]{Score,uuid});
                System.out.println(gameResult);
                Score=0;
                System.out.println("Let's Play another round \uD83D\uDDFF \uD83D\uDCC4 ✂  ! \n \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1");
            }

    }
      }
      catch (Exception e) {
          System.out.println(e);
      }}

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