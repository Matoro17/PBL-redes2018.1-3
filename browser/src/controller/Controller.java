package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Produto;
import model.Logger;
import model.network.Purchase;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller {
    @FXML private TextField address, amount;
    @FXML private Label message;
    @FXML private ListView<Produto> list;
    @FXML private Button search, buy;
    private Produto[] produtos;

    public void search() {
        Purchase purchase = connect();

        if (purchase != null) {
            try {
                produtos = purchase.getProducts();
                list.setItems(FXCollections.observableArrayList(produtos));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void buy() {
        if (!amount.getText().isEmpty() && amount.getText().matches("^[1-9]+") &&
                list.getSelectionModel().getSelectedIndex() >= 0) {

            int id = produtos[list.getSelectionModel().getSelectedIndex()].getLogger().getId();
            int value = Integer.parseInt(amount.getText());
            Logger logger = new Logger(id, value);
            Purchase purchase = connect();

            if (purchase != null) {
                try {
                    if (purchase.buyProduct(logger)) {
                        search();
                        message.setText("Success");
                    } else {
                        message.setText("Error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Purchase connect() {
        list.getItems().clear();
        message.setText("");

        try {
            if (!address.getText().isEmpty()) {
                InetAddress host = InetAddress.getByName(address.getText());

                if (host.isReachable(500)) {
                    Registry registry = LocateRegistry.getRegistry(host.getHostAddress());
                    return (Purchase) registry.lookup("Purchase");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        message.setText("Servidor não encontrado");
        return null;
    }
}
