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
	public void pressed() throws RemoteException {
		System.out.println("apertado");		
	}

}
