package Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import Interfaces.Service;
import Model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ServerController implements Initializable {
	private HashMap<Integer, Produto> estoque;
	@FXML
	private Label label;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try { 
			carregarArquivo(); 
		} catch (FileNotFoundException e1) {
		 System.out.println("arquivo não carregado"); e1.printStackTrace(); 
		 }
		 
		try {
			String nomeServico = "MeuServico";
			int porta = 12345;

			Service servico = new Implement(this.estoque);
			Service servicoDistribuido = (Service) UnicastRemoteObject.exportObject(servico, 0);

			Registry registry = LocateRegistry.createRegistry(porta);
			registry.bind(nomeServico, servicoDistribuido);
			System.out.printf("Servico disponivel: %s%n", nomeServico);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void carregarArquivo() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("db.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] linha = line.split(";");
			Produto temp = new Produto(Integer.parseInt(linha[0]), Integer.parseInt(linha[1]), linha[2],
					Float.parseFloat(linha[3]));
			estoque.put(Integer.parseInt(linha[0]), temp);
		}
	}
}
