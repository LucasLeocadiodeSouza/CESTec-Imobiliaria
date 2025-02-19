package com.cestec.principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/cestec/principal/view/comWindow.xml"));

			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show(); 
			
			//esse comando que coloca a cena no canto direito da tela tem que ser executado apos o primaryStage.show() para o programa calcular seu tamanho certo antes
			Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	        // Calcula a posição para o canto inferior direito
	        double x = screenSize.getWidth()  - primaryStage.getWidth();
	        double y = screenSize.getHeight() - primaryStage.getHeight(); 

	        primaryStage.setX(x);
	        primaryStage.setY(y);
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}