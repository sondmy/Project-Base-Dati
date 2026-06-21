package it.unibo.zoo.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Interfaccia utente per la gestione delle donazioni.
 */
public class DonazioniView {
    
    private final VBox root;
    private final TextField txtImporto;
    private final TextField txtDescrizione;
    private final Button btnSalvaDonazione;
    private final Label lblDonazioneMsg;
    private final VBox panelNuovaDonazione;
    
    public DonazioniView() {
        root = new VBox(16);
        root.setPadding(new Insets(32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        final Label title = new Label("Donazioni anonime");
        title.setStyle(StyleHelper.STYLE_TITLE);

        panelNuovaDonazione = new VBox(12);
        panelNuovaDonazione.setStyle(StyleHelper.STYLE_CARD);
        panelNuovaDonazione.setPadding(new Insets(16));
        panelNuovaDonazione.setVisible(true);
        panelNuovaDonazione.setManaged(true);

        final Label lblImporto = new Label("Importo (\u20AC):");
        lblImporto.setStyle(StyleHelper.STYLE_LABEL);
        txtImporto = new TextField();
        txtImporto.setPromptText("Es. 50.00");
        txtImporto.setMaxWidth(200);

        final Label lblDescrizione = new Label("Descrizione:");
        lblDescrizione.setStyle(StyleHelper.STYLE_LABEL);
        txtDescrizione = new TextField();
        txtDescrizione.setPromptText("se vuoi lasciarci un messaggio");
        txtDescrizione.setMaxWidth(Double.MAX_VALUE);

        btnSalvaDonazione = new Button("Salva donazione");
        btnSalvaDonazione.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        lblDonazioneMsg = new Label();
        lblDonazioneMsg.setVisible(false);

        panelNuovaDonazione.getChildren().addAll(
            lblImporto,
            txtImporto,
            lblDescrizione,
            txtDescrizione,
            btnSalvaDonazione,
            lblDonazioneMsg
        );

        root.getChildren().addAll(title, panelNuovaDonazione);
    }
    
    public VBox getRoot() { return root; }
    public TextField getTxtImporto() { return txtImporto; }
    public TextField getTxtDescrizione() { return txtDescrizione; }
    public Button getBtnSalvaDonazione() { return btnSalvaDonazione; }
    
    public void showMessage(String msg, boolean success) {
        lblDonazioneMsg.setText(msg);
        lblDonazioneMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblDonazioneMsg.setVisible(true);
    }
}