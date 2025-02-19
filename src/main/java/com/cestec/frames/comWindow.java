package com.cestec.frames;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


public class comWindow {
    
    @FXML
    private AnchorPane frameAnchorPane;
    private Stage stage;
    
    @FXML
    private WebView wvComWindow;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
			// Obtém o Stage a partir de qualquer nó
			stage = (Stage) frameAnchorPane.getScene().getWindow();
			
			stage.setMaximized(true);
			stage.setTitle("Sistem Enterpreise - Comercial");
		});

        WebEngine engine = wvComWindow.getEngine();
        engine.load(getClass().getResource("/com/cestec/principal/assets/web/comWindow.html").toExternalForm());
        engine.setUserStyleSheetLocation(getClass().getResource("/com/cestec/principal/assets/web/padrao.css").toExternalForm());
        
        // Aguarda o carregamento da página e injeta o JavaScript Bridge
        engine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("comwindowj", this);  // EColoca a classe atual no js, dai da para chamar os metodos java no js como se fosse um static
            }
        });
    }

    

}
