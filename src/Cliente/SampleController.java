package Cliente;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import Interfaces.Service;
import Interfaces.ServicoListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SampleController implements ServicoListener, Initializable {
	@FXML
	private Button botao;


	@Override
	public void calculoEfetuado(double resultado) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		botao.setOnAction(event -> {
			try {
	            String nomeServico = "MeuServico";
	            int porta = 12345;

	            ServicoListener clienteA = new SampleController();
	            ServicoListener clienteAdistribuido = (ServicoListener) UnicastRemoteObject.exportObject(clienteA, 0);

	            Registry registry = LocateRegistry.getRegistry(porta);
	            Service servicoRemoto = (Service) registry.lookup(nomeServico);
	            servicoRemoto.addListener(clienteAdistribuido);

	            double valor = 70;
	            System.out.println("Cliente A enviando: " + valor);
	            servicoRemoto.pressed();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		});
	}
}
