package model.network;

import model.Product;
import model.Logger;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Purchase extends Remote {
    Product[] getProducts() throws RemoteException;
    boolean buyProduct(Logger logger) throws RemoteException;
}
