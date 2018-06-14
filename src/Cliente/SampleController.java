package Cliente;


import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import Interfaces.Service;
import Interfaces.ServicoListener;
import Server.Implement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SampleController implements ServicoListener, Initializable {
	@FXML
	private Button botao;
	@FXML
	private Label title;
	
	private Cicle ciclo;

	@Override
	public void calculoEfetuado(String name) throws RemoteException {
		System.out.println(name);
		
	}
	
	@FXML
	private void text(ActionEvent event) {
		System.out.println("Clickei viado");
		try {
            String nome = "Jorge";
            System.out.println("Cliente A enviando: " + nome);
            ciclo.getServicoRemoto().pressed(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String nomeServico = "MeuServico";
	    int porta = 12345;
	
	    ServicoListener clienteA = new SampleController();
	    ServicoListener clienteAdistribuido;
		try {
			clienteAdistribuido = (ServicoListener) UnicastRemoteObject.exportObject(clienteA, 0);
			ciclo = new Cicle(nomeServico, porta,clienteAdistribuido);
			new Thread(ciclo).start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    

	}
}


