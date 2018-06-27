package model.network;

import model.Product;
import model.Register;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Purchase extends Remote {
    Product[] getProducts() throws RemoteException;
    boolean buyProduct(Register register) throws RemoteException;
}
