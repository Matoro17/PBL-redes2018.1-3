package controller;

import model.Product;
import model.Register;
import model.data.DataBase;
import model.network.Purchase;
import model.network.Sentinel;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.stream.IntStream;
/*
    Classe que administra as compras entre servidores
 */
public class CommercialServer implements Purchase {
    private DataBase db;
    private Sentinel[] sentinels;

    public CommercialServer(DataBase db) {
        this.db = db;

        InetAddress[] addresses = db.getAddresses();
        sentinels = new Sentinel[addresses.length];

        IntStream.range(0, sentinels.length).forEach(index -> {
            sentinels[index] = new Sentinel(addresses[index]);
            sentinels[index].start();
        });
    }

    @Override
    public Product[] getProducts() throws RemoteException {
        return db.getProducts();
    }
    /*
        Método para compra de produtos e notificação de outros servidores na rede que alguma compra fora efetuada
     */
    @Override
    public boolean buyProduct(Register register) throws RemoteException {
        try {
            if (db.updateProduct(register)) {
                Arrays.stream(sentinels).forEach(sentinel -> sentinel.setRegister(register));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
