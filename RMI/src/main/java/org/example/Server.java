package org.example;

import org.example.RoundServiceImpl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements Serializable {

public Server(){}
        public static void main(String[] args) throws RemoteException, MalformedURLException {
            try {
                RoundServiceImpl distanceObject = new RoundServiceImpl();
                LocateRegistry.createRegistry(5002);
                Naming.rebind("rmi://localhost:5002/Strong/operations", distanceObject);
                System.out.println("Server is ready.");
            } catch (Exception var2) {
                System.out.println(var2);
            }

        }
    }

