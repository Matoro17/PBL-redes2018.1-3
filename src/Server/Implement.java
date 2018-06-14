package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Service;
import Interfaces.ServicoListener;

public class Implement implements Service{

    private final List<ServicoListener> listeners = new ArrayList<>();
    
    @Override
    public void addListener(ServicoListener listener) throws RemoteException {
        listeners.add(listener);
    }
	
    @Override
	public void pressed(String name) throws RemoteException {
		for (ServicoListener listener : listeners) {
            try {
                listener.calculoEfetuado(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }	
	}

	@Override
	public Boolean vendaProduto(int idProduto) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adicaoProduto(int idProduto, int Quatidade) throws RemoteException {
		// TODO Auto-generated method stub
		
	}



}
