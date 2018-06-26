import controller.MegaServer;
import controller.DataServer;
import model.data.BaseDados;
import model.network.Connection;
import model.network.Purchase;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

    /*
     * Inicío do servidor, onde é instanciado o banco de dados, e com ele instanciado o DataServer e criado o registro na porta 1099
     * E criado o commercial server utilizando o banco de dados
     */
    public static void main(String[] args) {
        try {
            BaseDados db = new BaseDados();
            DataServer ds = new DataServer(db);
            MegaServer cs = new MegaServer(db);

            Connection connection = (Connection) UnicastRemoteObject.exportObject(ds, 0);
            Purchase purchase = (Purchase) UnicastRemoteObject.exportObject(cs, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Connection", connection);
            registry.bind("Purchase", purchase);

            System.out.println("Servers ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
