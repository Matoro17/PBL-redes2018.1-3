package Cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Interfaces.Service;
import Interfaces.ServicoListener;

public class Cicle implements Runnable{
	String nomeServico;
    int porta;
    ServicoListener clienteAdistribuido;
    Service servicoRemoto;
    
    public Cicle(String nomeServico2, int porta2, ServicoListener clienteAdistribuido2) {
    	this.nomeServico = nomeServico2;
    	this.porta = porta2;
    	this.clienteAdistribuido = clienteAdistribuido2;
	}

    
    public Service getServicoRemoto() {
    	return servicoRemoto;
    }
    
	@Override
	public void run() {
		try {
			Registry registry = LocateRegistry.getRegistry(porta);
	        servicoRemoto = (Service) registry.lookup(nomeServico);
	        servicoRemoto.addListener(clienteAdistribuido);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
