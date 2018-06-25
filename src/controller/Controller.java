package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.Compra;
import model.Produto;
import model.Servidor;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private ListView<Produto> produtos;
    @FXML private TextField id, quantidade;
    @FXML private Label mensagem;
    private Servidor servidor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servidor = new Servidor();

        Thread rmi = new Thread(() -> {
            try {
                Compra compra = (Compra) UnicastRemoteObject.exportObject(servidor, 0);
                Registry registry = LocateRegistry.createRegistry(1099);
                registry.rebind("Compra", compra);
            } catch (RemoteException e) { e.printStackTrace(); }
        });

        rmi.setDaemon(true);
        rmi.start();

        Timeline tela = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            produtos.getItems().clear();
            produtos.setItems(FXCollections.observableArrayList(servidor.listarProdutos()));
        }));

        tela.setCycleCount(Timeline.INDEFINITE);
        tela.play();
    }

    public void comprar() {
        try {
            int i = Integer.parseInt(id.getText());
            int q = Integer.parseInt(quantidade.getText());

            if (servidor.realizarComprar(i, q))
                mensagem.setText("Compra efetuada com sucesso");
            else
                mensagem.setText("Falha ao realizar a compra");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mensagem.setText("Dados inválidos");
        }
    }
}
