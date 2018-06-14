package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Interfaces.Service;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;





public class Server extends Application {
	@FXML
	private Label label;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("serverxml.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		try {
            String nomeServico = "MeuServico";
            int porta = 12345;

            Service servico = new Implement();
            Service servicoDistribuido = (Service) UnicastRemoteObject.exportObject(servico, 0);

            Registry registry = LocateRegistry.createRegistry(porta);
            registry.bind(nomeServico, servicoDistribuido);
            System.out.printf("Servico disponivel: %s%n", nomeServico);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
