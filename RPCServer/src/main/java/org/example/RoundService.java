package org.example;


import java.util.ArrayList;
import java.util.List;

public interface RoundService {
    public  String connect(String uuid) ;
    ArrayList<String> playRound(String playerChoice,String uuid) ;

    String getGameResult(int stat,String uuid) ;
 public List<String> history(String uuid);
}
