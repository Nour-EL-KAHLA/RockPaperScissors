package org.example;




import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RoundServiceImpl implements RoundService {




    public static HashMap<String, List<String>> map = new HashMap<String,List<String>>();

    public RoundServiceImpl() {

    }


    @Override
    public  String connect(String uuid) throws RemoteException{
        System.out.println(uuid);

        List<String> l = new ArrayList<String>();
        l.add("Client: "+uuid+"\n");
        map.put(uuid,l);

        return uuid;
    }
    @Override
    public ArrayList<String> playRound(String playerChoice,String uuid)throws RemoteException  {


        String[] choices = {"rock", "paper", "scissors"};
        ArrayList<String> roundresult = new ArrayList<String>();

        String computerChoice = choices[new Random().nextInt(choices.length)];

        if (playerChoice.equals(computerChoice)) {
            roundresult.add("Computer chose "+ computerChoice +", It's a tie!"  );
            roundresult.add("0");

        } else if (
                (playerChoice.equals("rock") && computerChoice.equals("scissors")) ||(playerChoice.equals("paper") && computerChoice.equals("rock")) ||(playerChoice.equals("scissors") && computerChoice.equals("paper"))
        ) {

            roundresult.add("Computer chose "+ computerChoice+ ", You win this round!");
            roundresult.add("1");
        } else {

            roundresult.add("Computer chose "+ computerChoice+", Computer wins this round!");
            roundresult.add("10");
        }

        List<String> l = map.get(uuid);


        l.add("[Round](Your choice: "+playerChoice+" VS Computer choice: "+computerChoice+")");
        map.put(uuid,l);


        return  roundresult;
    }


    @Override
    public String getGameResult(int stat,String uuid)  throws RemoteException{

        List<String> list = map.get(uuid);
        String l="";
      System.out.println(l);
        if ( stat ==2 || stat ==3 || stat == 1 ||stat == 12) {


            l= "Congratulations! You win the game!";
            list.add("[Winner]:(You)\n");
            map.put(uuid,list);

        }

        else if (stat >= 20 || stat == 10) {

            l = "Sorry! Computer wins the game. ";
            list.add("[Winner]:(Computer)\n");
            map.put(uuid,list);
        }
        else {
            l= "Sorry! It's a tie! ";
            list.add("[Winner]:(Tie)\n");
            map.put(uuid,list);
        }

      return  l;

}

    @Override
    public List<String> history(String uuid) throws RemoteException{

            return map.get(uuid);

    }
    @Override
    public OutputStream getOutputStream() throws RemoteException {

        return new ByteArrayOutputStream();
    }
}
