package it.unibo.zoo.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Interfaccia utente per la gestione delle donazioni.
 */
public class DonazioniView {
    
    private final VBox root;
    private final Button btnNuovaDonazione;
    private final Button btnSalvaDonazione;
    private final Label lblDonazioneMsg;
    
    public DonazioniView() {
        root = new VBox();
        // Bottone aggiungi spesa
        btnNuovaSpesa = new Button("Aggiungi Spesa");
        btnNuovaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnNuovaSpesa.setOnMouseEntered(e -> btnNuovaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnNuovaSpesa.setOnMouseExited(e -> btnNuovaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        // Pannello nuova spesa
        panelNuovaSpesa = new VBox(12);
        panelNuovaSpesa.setStyle(StyleHelper.STYLE_CARD);
        panelNuovaSpesa.setPadding(new Insets(16));
        panelNuovaSpesa.setVisible(false);
        panelNuovaSpesa.setManaged(false);

        final Label lblNewSpesa = new Label("Nuova Spesa");
        lblNewSpesa.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        final Label lblSpesaImporto = new Label("Importo (€):");
        lblSpesaImporto.setStyle(StyleHelper.STYLE_LABEL);
        txtSpesaImporto = new TextField();
        txtSpesaImporto.setPromptText("Es. 49.90");
        txtSpesaImporto.setMaxWidth(200);

        final Label lblSpesaDesc = new Label("Descrizione:");
        lblSpesaDesc.setStyle(StyleHelper.STYLE_LABEL);
        txtSpesaDescrizione = new TextField();
        txtSpesaDescrizione.setPromptText("Descrizione della spesa...");
        txtSpesaDescrizione.setMaxWidth(Double.MAX_VALUE);

        final Label lblSpesaForn = new Label("Fornitore:");
        lblSpesaForn.setStyle(StyleHelper.STYLE_LABEL);
        comboSpesaFornitore = new ComboBox<>();
        comboSpesaFornitore.setPromptText("Seleziona fornitore...");
        comboSpesaFornitore.setMaxWidth(Double.MAX_VALUE);

        final Label lblInfoData = new Label("\u2139 La data verrà assegnata automaticamente alla data odierna.");
        lblInfoData.setStyle("-fx-font-size: 12px; -fx-text-fill: " + StyleHelper.TEXT_MUTED + "; -fx-font-style: italic;");

        btnSalvaSpesa = new Button("Salva");
        btnSalvaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnSalvaSpesa.setOnMouseEntered(e -> btnSalvaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnSalvaSpesa.setOnMouseExited(e -> btnSalvaSpesa.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        lblSpesaMsg = new Label();
        lblSpesaMsg.setVisible(false);

        panelNuovaSpesa.getChildren().addAll(lblNewSpesa, lblSpesaImporto, txtSpesaImporto, lblSpesaDesc, txtSpesaDescrizione, lblSpesaForn, comboSpesaFornitore, lblInfoData, btnSalvaSpesa, lblSpesaMsg);

        donazioniContent.getChildren().addAll(lblElencoDonazioni, tableDonazioni, btnNuovaDonazione, panelNuovaDonazione);

        final ScrollPane donazioniScroll = new ScrollPane(donazioniContent);
        donazioniScroll.setFitToWidth(true);
        donazioniScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        tabDonazioni.setContent(donazioniScroll);
    }
    
    public VBox getRoot() {
        return root;
    }
}