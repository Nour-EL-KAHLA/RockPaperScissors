package org.example;


import org.apache.log4j.BasicConfigurator;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class Server {
     public static void main(String[] args) throws XmlRpcException, IOException {
         BasicConfigurator.configure();
         WebServer webServer = new WebServer(8081);

         XmlRpcServer xmlRpcServer =
                 webServer.getXmlRpcServer();

         PropertyHandlerMapping phm = new
                 PropertyHandlerMapping();
         phm.addHandler("Round", RoundServiceImpl.class);
         xmlRpcServer.setHandlerMapping(phm);
         webServer.start();
         System.out.println("Server is running...");

    }
}
