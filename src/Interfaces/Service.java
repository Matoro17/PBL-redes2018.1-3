package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import Model.Produto;




public interface Service extends Remote{
	
    void addListener(ServicoListener listener) throws RemoteException;
    
    Boolean vendaProduto(int idProduto) throws RemoteException;
    
    void adicaoProduto(int idProduto, int Quatidade) throws RemoteException;
	
	void pressed(String name) throws RemoteException;
	
	HashMap<Integer, Produto> getEstoque() throws RemoteException;
	
}
