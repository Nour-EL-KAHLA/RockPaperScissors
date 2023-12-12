import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RoundService extends Remote {
    public void connect(String uuid) throws RemoteException;
    ArrayList<String> playRound(String playerChoice,String uuid) throws RemoteException;

    String getGameResult(int stat,String uuid) throws RemoteException;
 public List<String> history(String uuid) throws RemoteException ;
}
