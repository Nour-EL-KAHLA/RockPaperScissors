package org.example;


import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RoundService {
    public  String connect(String uuid) throws RemoteException ;
    ArrayList<String> playRound(String playerChoice,String uuid) throws RemoteException;

    String getGameResult(int stat,String uuid)throws RemoteException ;
 public List<String> history(String uuid) throws RemoteException;
    OutputStream getOutputStream()throws RemoteException;
}
