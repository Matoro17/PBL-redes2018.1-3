package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface Service extends Remote{
	
    void addListener(ServicoListener listener) throws RemoteException;

	
	void pressed() throws RemoteException;
	
}
