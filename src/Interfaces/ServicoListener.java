package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoListener extends Remote {

    void calculoEfetuado(String name) throws RemoteException;
    //void atualizarTxt(String arquivo, int quantidade);
    //void atualizar(String dados);
    
}