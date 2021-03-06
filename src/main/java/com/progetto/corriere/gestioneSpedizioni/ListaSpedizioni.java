package com.progetto.corriere.gestioneSpedizioni;

import com.progetto.corriere.SchermataPrincipaleCorriere;
import com.progetto.addetto.segnalazioni.EntryListaSegnalazioni;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe che gestisce e mostra a schermo la lista delle spedizioni
 */
public class ListaSpedizioni extends Application implements Initializable {

    @FXML
    private Text usernameLabel;
    @FXML
    private TableView<EntryListaSpedizioni> lista;
    @FXML
    private TableColumn<EntryListaSegnalazioni, Integer> idOrdine;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> nomeFarmacia;
    @FXML
    private TableColumn<EntryListaSegnalazioni, String> indirizzoConsegna;
    @FXML
    private TableColumn<EntryListaSegnalazioni, FlowPane> strumenti;

    private static ArrayList<EntryListaSpedizioni> spedizioni;
    private static CreaListaSpedizioniControl creaListaSpedizioniControl;

    /**
     * Costruisce un oggetto di tipo {@code ListaSpedizioni}
     */
    public ListaSpedizioni(){ super(); }

    /**
     * Costruisce un oggetto di tipo {@code ListaSpedizioni}
     * @param spedizioni spedizioni da mostrare a schermo
     * @param creaListaSpedizioniControl control che gestisce la creazione della lista spedizioni tramite il quale è possibile tornare alla schermata precedente
     */
    public ListaSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni, CreaListaSpedizioniControl creaListaSpedizioniControl) {
        super();
        setSpedizioni(spedizioni);
        setCreaListaSpedizioniControl(creaListaSpedizioniControl);
    }

    /**
     * Permette di settare la lista delle spedizioni da mostrare
     * @param spedizioni lista delle spedizioni da mostrare
     */
    public void setSpedizioni(ArrayList<EntryListaSpedizioni> spedizioni) {
        if (spedizioni == null) {
            throw new NullPointerException("spedizioni = null");
        }
        ListaSpedizioni.spedizioni = spedizioni;
    }

    /**
     * Ritorna la lista delle spedizioni
     * @return oggetto di tipo {@code ArrayList<EntryListaSpedizioni>} contenente la lista delle spedizioni
     */
    public static ArrayList<EntryListaSpedizioni> getSpedizioni(){
        return spedizioni;
    }

    /**
     * Permette di settare la control che gestisce la lista delle spedizioni
     * @param creaListaSpedizioniControl control che gestisce la lista delle spedizioni
     */
    public void setCreaListaSpedizioniControl(CreaListaSpedizioniControl creaListaSpedizioniControl) {
        if (creaListaSpedizioniControl == null) {
            throw new NullPointerException("control = null");
        }
        ListaSpedizioni.creaListaSpedizioniControl = creaListaSpedizioniControl;
    }

    /**
     * Permette di mostrare la schermata con la lista delle spedizioni
     * @param stage stage della schermata
     * @throws IOException lanciata se il caricamento del file {@code fxml} non è andato a buon fine
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listaSpedizioni.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        double stageWidth = 800;
        double stageHeight = 400;

        Stage subStage = new Stage();
        //centra la schermata
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        subStage.setX((screenBounds.getWidth() - stageWidth) / 2);
        subStage.setY((screenBounds.getHeight() - stageHeight) / 2);

        //mostra la schermata di login
        subStage.setTitle("Lista Spedizioni");
        subStage.setScene(scene);
        subStage.setMinWidth(stageWidth + 50);
        subStage.setMinHeight(stageHeight);
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
     * Metodo che inizializza la lista delle spdizioni inserendo i dati relativi alle spedizioni
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usernameLabel.setText(SchermataPrincipaleCorriere.getCorriere().getNominativo());
        this.idOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        this.nomeFarmacia.setCellValueFactory(new PropertyValueFactory<>("nomeFarmacia"));
        this.indirizzoConsegna.setCellValueFactory(new PropertyValueFactory<>("indirizzoConsegna"));
        this.strumenti.setCellValueFactory(new PropertyValueFactory<>("strumenti"));

        if (ListaSpedizioni.spedizioni!=null) {
            for(EntryListaSpedizioni spedizione : ListaSpedizioni.spedizioni) {
                this.lista.getItems().add(spedizione);
                Button pulsanteConsegna = (Button)spedizione.getStrumenti().getChildren().get(0);
                pulsanteConsegna.setOnAction(event -> confermaConsegna(event, spedizione));
            }
        }
    }

    /**
     * Metodo che aggiorna la lista delle spedizioni in base alle spedizioni da mostrare
     */
    public void update(){
        this.lista.getItems().clear();
        if (ListaSpedizioni.spedizioni!=null) {
            for(EntryListaSpedizioni spedizione : ListaSpedizioni.spedizioni) {
                this.lista.getItems().add(spedizione);
                Button pulsanteConsegna = (Button)spedizione.getStrumenti().getChildren().get(0);
                pulsanteConsegna.setOnAction(event -> confermaConsegna(event, spedizione));
            }
        }
    }

    /**
     * Metodo che crea la control relativa alla consegna delle spedizioni
     * @param event evento associato alla pressione del tasto consegna
     * @param spedizione oggeto di tipo {@code EntrListaSpedizioni} relativo alla spedizione da consegnare
     */
    @FXML
    public void confermaConsegna(ActionEvent event,EntryListaSpedizioni spedizione) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ConfermaRicezioneSpedizioneControl confermaRicezioneSpedizioneControl = new ConfermaRicezioneSpedizioneControl(stage, spedizione, this);
        confermaRicezioneSpedizioneControl.start();
    }

    /*
     Permette di tornare indietro e visualizzare la SchermataPrincipaleCorriere
     */
    @FXML
    private void indietro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // prendo lo stage corrente
        creaListaSpedizioniControl.clickSuIndietro(stage);
    }

}
