package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compra extends Remote {
    boolean diminuirEstoque(int id, int quantidade) throws RemoteException;
    void aumentarEstoque(int id, int quantidade) throws RemoteException;
}
