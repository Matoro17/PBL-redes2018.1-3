package controller;

import model.Product;
import model.Register;
import model.data.DataBase;
import model.network.Purchase;
import model.network.Sentinel;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.stream.IntStream;

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

    @Override
    public boolean buyProduct(Register register) throws RemoteException {
        if (db.updateProduct(register)) {
            Arrays.stream(sentinels).forEach(sentinel -> sentinel.setRegister(register));
            return true;
        }

        return false;
    }
}
