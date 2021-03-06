package com.progetto.farmacia.ordini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che modella la boundary {@code SchermataErroreQuantita}
 */
public class SchermataErroreQuantita extends Application {

    private static VerificaCorrettezzaOrdineControl control;
    /**
     * Permette di visualizzare la {@code SchermataErroreQuantita}
     * @param stage stage della schermata di errore
     * @throws IOException se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schermataErroreQuantita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        double stageWidth = 410;
        double stageHeight = 360;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        subStage.setTitle("Problema Quantità");
        subStage.setScene(scene);
        subStage.setWidth(stageWidth);
        subStage.setHeight(stageHeight);
        subStage.setMinWidth(stageWidth);
        subStage.setMinHeight(stageHeight);
        subStage.initOwner(stage); //imposto come proprietario del Riepilogo la Lista Spedizioni
        subStage.initModality(Modality.WINDOW_MODAL);  //blocco il focus sulla schermata di Riepilogo
        subStage.show();
        subStage.setOnCloseRequest(event -> { Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Per uscire dal programma effettua il logout.");
            alert.setTitle("Chiusura Programma");
            alert.setHeaderText("AVVISO");
            event.consume();
            alert.showAndWait();
        });
    }

    /**
     * Istanzia un oggetto di tipo {@code SchermataErroreQuantita}
     */
    public SchermataErroreQuantita(){
        super();
    }

    /**
     * Istanzia un oggetto di tipo {@code SchermataErroreQuantita} data in input la control che si occupa di verificare
     * la correttezza di un ordine
     * @param control
     */
    @SuppressWarnings("JavadocDeclaration")
    public SchermataErroreQuantita(VerificaCorrettezzaOrdineControl control) {
        this.setControl(control);
    }

    private void setControl(VerificaCorrettezzaOrdineControl control) {
        if(control == null) {
            throw new NullPointerException("Control = null");
        }
        SchermataErroreQuantita.control = control;
    }

    @FXML
    private void annulla(ActionEvent event) {
        SchermataErroreQuantita.control.clickSuAnnullaOrdine(event);
    }

    @FXML
    private void conferma(ActionEvent event) {
       SchermataErroreQuantita.control.clickSuConferma(event);
    }
}
