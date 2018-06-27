package model.network;

import model.Register;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    boolean updateRegister(Register register) throws RemoteException;
}
