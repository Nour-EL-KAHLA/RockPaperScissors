package org.example;


import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

public interface RoundService extends Remote, Serializable {
    public  String connect(String uuid) ;
    ArrayList<String> playRound(String playerChoice,String uuid) ;

    String getGameResult(int stat,String uuid) ;
 public List<String> history(String uuid)  ;

    OutputStream getOutputStream();
}
