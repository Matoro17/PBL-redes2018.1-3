package Cliente;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import Interfaces.ServicoListener;
import Model.Produto;
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

	private HashMap<Integer, Produto> estoque;
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
		title = new Label();
		estoque = new HashMap<>();
		/*
		 * try { carregarArquivo(); } catch (FileNotFoundException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		String nomeServico = "MeuServico";
		int porta = 12345;

		ServicoListener clienteA = new SampleController();
		ServicoListener clienteAdistribuido;
		try {
			clienteAdistribuido = (ServicoListener) UnicastRemoteObject.exportObject(clienteA, 0);
			ciclo = new Cicle(nomeServico, porta, clienteAdistribuido);
			new Thread(ciclo).start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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

	@Override
	public void adicaoProduto(int id, Produto produto) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void atualizarBdtext() {
		File db = new File("db.txt");
		try {

			FileWriter fileW = new FileWriter(db);
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write("");
			buffW.flush();
			for (Map.Entry<Integer, Produto> entry : estoque.entrySet()) {
				Integer key = entry.getKey();
				Produto value = entry.getValue();

				buffW.write(key + ";" + value.getQuantidade() + ";" + value.getNome() + ";" + value.getPreco() + "\n");

			}

			buffW.close();
		} catch (IOException io) {
		}
	}

	@Override
	public void atualizarQuantidadeLocal(int idProduto, int i) {
		estoque.get(idProduto).setQuantidade(estoque.get(idProduto).getQuantidade() + i);
		atualizarBdtext();

	}
}
