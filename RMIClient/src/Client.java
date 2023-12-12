import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Client {
        public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
            try {
                int Score =0;
                String uuid = UUID.randomUUID().toString();
                RoundService stub = (RoundService) Naming.lookup("rmi://localhost:5000/Strong/operations");
                stub.connect(uuid);
                System.out.println("Let's Play  \uD83D\uDDFF \uD83D\uDCC4 ✂  ! \n \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1");

                Scanner scanner = new Scanner(System.in);
                int j = 1;
                int h=1;
                while (j==1){
                    h=1;

                    String[] values = {"rock","paper","scissors","quit","history"};

               for(int i =0;i<3;i++){
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
                       stub.history(uuid).forEach(x->{
                           System.out.println(x);
                       });

                       Score=0;
                       break;
                   }

                   ArrayList<String> myresult = (ArrayList<String>) stub.playRound(playerChoice,uuid);

                   System.out.println(myresult.get(0));
                   Score = Score + Integer.parseInt(myresult.get(1));
                   System.out.println(Score);
               } if(j==1 && h==1){
                    String gameResult = stub.getGameResult(Score,uuid);
                    System.out.println(gameResult);
                        Score=0;
                    System.out.println("Let's Play another round \uD83D\uDDFF \uD83D\uDCC4 ✂  ! \n \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1 \uD83C\uDFC1");
                }}

            }
            catch (Exception e) {
               System.out.println(e);
            }
        }
}
