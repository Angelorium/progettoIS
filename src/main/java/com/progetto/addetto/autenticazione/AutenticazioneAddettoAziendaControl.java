package com.progetto.addetto.autenticazione;
import com.progetto.dbInterface.InterfacciaAutenticazione;
import com.progetto.entity.AddettoAzienda;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Control che gestisce l'autenticazione della farmacia
 */
public class AutenticazioneAddettoAziendaControl {

    /**
     * istanzia l'oggetto dati in input l'id dell'addetto e la password
     * @param idAddetto id dell'addetto
     * @param password password inserita dall'utente
     * @param event evento che rappresenta il click del tasto login
     * @exception IOException se non è possibile caricare il file fxml della schermata dell'errore
     */
    public AutenticazioneAddettoAziendaControl(TextField idAddetto, PasswordField password, ActionEvent event) throws IOException {
        String pwd = this.creaDigest(password.getText());
        try {
            int id = Integer.parseInt(idAddetto.getText());
            this.verificaCredenziali(this.getCredenziali(id,pwd));
        } catch (NumberFormatException e) { //id dell'addetto inserito in un formato non corretto
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazioneAddetto errAut = new ErroreAutenticazioneAddetto(0);
            errAut.start(stage);
        } catch (CredentialException e){ //credenziali non corrette
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //ottiene stage corrente
            ErroreAutenticazioneAddetto errAut = new ErroreAutenticazioneAddetto(1);
            errAut.start(stage);
        }
    }

    private String creaDigest(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;  //la stringa ha 64 caratteri
    }

    private AddettoAzienda getCredenziali (int idAddetto, String password) throws CredentialException {
        InterfacciaAutenticazione intAut = new InterfacciaAutenticazione();
        return intAut.getCredenziali(idAddetto, password);
    }

    private void verificaCredenziali(AddettoAzienda addetto) throws CredentialException{
        if(addetto != null){
            try {
                AddettoAzienda addettoClone = addetto.clone();
                //chiudi schermata di autenticazione
                SchermataPrincipaleAddettoAzienda schermataPrincipaleFarmacia = new SchermataPrincipaleAddettoAzienda(farm);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new CredentialException("Credenziali non corrette");
        }
    }
}
