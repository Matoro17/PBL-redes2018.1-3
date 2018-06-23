package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Model.Produto;

public interface ServicoListener extends Remote {

    void calculoEfetuado(String name) throws RemoteException;
    void adicaoProduto(int id, Produto produto) throws RemoteException;
    //void atualizarTxt(String arquivo, int quantidade);
    //void atualizar(String dados);
	void atualizarQuantidadeLocal(int idProduto, int i) throws RemoteException;
    
}