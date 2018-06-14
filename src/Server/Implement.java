package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Interfaces.Service;
import Interfaces.ServicoListener;
import Model.Produto;

public class Implement implements Service{
	
	private HashMap<Integer, Produto> estoque;
    private final List<ServicoListener> listeners = new ArrayList<>();
    
    public Implement (HashMap<Integer, Produto> esto) {
    	this.estoque = esto;
    }
    
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
		if(estoque.get(idProduto).getQuantidade()>0) {
			estoque.get(idProduto).setQuantidade(estoque.get(idProduto).getQuantidade()-1);
			atualizar(idProduto, -1);
		}
		return false;
	}

	private void atualizar(int idProduto, int i) {
		for (ServicoListener listener : listeners) {
            listener.atualizarQuantidadeLocal(idProduto, i);
        }
	}

	@Override
	public void adicaoProduto(int idProduto, int Quatidade) throws RemoteException {
		// TODO Auto-generated method stub
		
	}



}
