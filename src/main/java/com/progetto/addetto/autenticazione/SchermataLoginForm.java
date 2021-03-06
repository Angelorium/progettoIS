package com.progetto.addetto.autenticazione;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe che implementa la boundary {@code SchermataLoginForm}
 */
public class SchermataLoginForm extends Application {

    @FXML
    private TextField id;
    @FXML
    private PasswordField password;
    @FXML
    //verifica le credenziali inserite
    private void login(ActionEvent event) {
        AutenticazioneAddettoControl autAddettoCtrl = new AutenticazioneAddettoControl(this.id, this.password, event);
        autAddettoCtrl.start();
    }

    /**
     * Permette di mostrare la schermata di login
     * @param stage stage della schermata
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataLoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        double stageWidth = 600;
        double stageHeight = 400;

        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stageWidth) / 2);
        stage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        stage.setTitle("Effettua l'accesso");
        stage.setScene(scene);
        stage.setMinWidth(stageWidth);
        stage.setMinHeight(stageHeight);
        stage.show();
    }

    /**
     * Permette di avviare la procedura di login
     * @param args
     */
    @SuppressWarnings("JavadocDeclaration")
    public static void main(String[] args) {
        launch();
    }
}
