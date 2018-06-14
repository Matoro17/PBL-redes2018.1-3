package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;




public interface Service extends Remote{
	
    void addListener(ServicoListener listener) throws RemoteException;
    
    Boolean vendaProduto(int idProduto) throws RemoteException;
    
    void adicaoProduto(int idProduto, int Quatidade) throws RemoteException;
	
	void pressed(String name) throws RemoteException;
	
}
