package model.network;

import model.Produto;
import model.Logger;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Purchase extends Remote {
    Produto[] getProducts() throws RemoteException;
    boolean buyProduct(Logger logger) throws RemoteException;
}
