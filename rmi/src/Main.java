import controller.CommercialServer;
import controller.DataServer;
import model.data.DataBase;
import model.network.Connection;
import model.network.Purchase;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            DataBase db = new DataBase();
            DataServer ds = new DataServer(db);
            CommercialServer cs = new CommercialServer(db);

            Connection connection = (Connection) UnicastRemoteObject.exportObject(ds, 0);
            Purchase purchase = (Purchase) UnicastRemoteObject.exportObject(cs, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Connection", connection);
            registry.bind("Purchase", purchase);

            System.out.println("Servidor on " + InetAddress.getLocalHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
