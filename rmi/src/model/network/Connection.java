package model.network;

import model.Logger;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    boolean updateRegister(Logger logger) throws RemoteException;
}
