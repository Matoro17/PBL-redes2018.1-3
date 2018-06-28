package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Product;
import model.Register;
import model.network.Purchase;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/*
    Classe controladora da interface do cliente
 */
public class Controller {
    @FXML private TextField address, amount;
    @FXML private Label message;
    @FXML private ListView<Product> list;
    @FXML private Button search, buy;
    private Product[] products;
    /*
        Metodo para checagem se o determinado endereço de IP existe para ser acessado
     */
    public void search() {
        Purchase purchase = connect();

        if (purchase != null) {
            try {
                products = purchase.getProducts();
                list.setItems(FXCollections.observableArrayList(products));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*
        Metodo de compra, no qual pega a quantidade digitada e o produto selecionado e é feito uma tentativa de autenticar a compra nos servidores
     */
    public void buy() {
        if (!amount.getText().isEmpty() && amount.getText().matches("^[1-9]+") &&
                list.getSelectionModel().getSelectedIndex() >= 0) {

            int id = products[list.getSelectionModel().getSelectedIndex()].getRegister().getId();
            int value = Integer.parseInt(amount.getText());
            Register register = new Register(id, value);
            Purchase purchase = connect();

            if (purchase != null) {
                try {
                    if (purchase.buyProduct(register)) {
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
    /*
        Metodo para se conectar a um servidor
     */
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

        message.setText("Server not found");
        return null;
    }
}
