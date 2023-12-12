import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) throws RemoteException, MalformedURLException {

            try {
                RoundServiceImpl distanceObject = new  RoundServiceImpl();
                LocateRegistry.createRegistry(5000);


        Naming.rebind("rmi://localhost:5000/Strong/operations",  distanceObject);
                System.out.println("Server is ready.");
            }
            catch (Exception e) {
                System.out.println(e);
            }

}}