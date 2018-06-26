package controller;

import model.Product;
import model.Logger;
import model.data.BaseDados;
import model.network.Purchase;
import model.network.Turret;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MegaServer implements Purchase {
    private BaseDados db;
    private Turret[] turrets;

    public MegaServer(BaseDados db) {
        this.db = db;

        InetAddress[] addresses = db.getAddresses();
        turrets = new Turret[addresses.length];

        IntStream.range(0, turrets.length).forEach(index -> {
            turrets[index] = new Turret(addresses[index]);
            turrets[index].start();
        });
    }

    @Override
    public Product[] getProducts() throws RemoteException {
        return db.getProducts();
    }

    @Override
    public boolean buyProduct(Logger logger) throws RemoteException {
        if (db.updateProduct(logger)) {
            Arrays.stream(turrets).forEach(turret -> turret.setRegister(logger));
            return true;
        }

        return false;
    }
}
