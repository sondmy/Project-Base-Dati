package it.unibo.zoo.view;

import it.unibo.zoo.model.entity.TipoBiglietto;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Vista per l'acquisto dei biglietti.
 */
public class BigliettiView {

    private final VBox root;
    private final TableView<TipoBiglietto> table;
    private final ComboBox<String> comboBiglietto;
    private final Spinner<Integer> spinnerQuantita;
    private final TextField txtNomeGruppo;
    private final Spinner<Integer> spinnerNumPersone;
    private final Label lblNumPersone;
    private final Button btnConferma;
    private final Label lblMessaggio;

    @SuppressWarnings("unchecked")
    public BigliettiView() {
        root = new VBox(16);
        root.setPadding(new Insets(32, 32, 32, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        /* ── Titolo ──────────────────────────────────────── */
        final Label title = new Label("Acquista il tuo biglietto");
        title.setStyle(StyleHelper.STYLE_TITLE);

        /* ── Tabella ─────────────────────────────────────── */
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setPrefHeight(220);

        final TableColumn<TipoBiglietto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        final TableColumn<TipoBiglietto, String> colDesc = new TableColumn<>("Descrizione");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        final TableColumn<TipoBiglietto, Double> colPrezzo = new TableColumn<>("Prezzo");
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colPrezzo.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final Double item, final boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : String.format("\u20AC%.2f", item));
            }
        });

        final TableColumn<TipoBiglietto, Boolean> colAttivo = new TableColumn<>("Attivo");
        colAttivo.setCellValueFactory(new PropertyValueFactory<>("attivo"));
        colAttivo.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final Boolean item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    final Label badge = new Label(item ? "Sì" : "No");
                    badge.setStyle(StyleHelper.badge(item ? StyleHelper.GREEN : StyleHelper.RED));
                    setGraphic(badge);
                    setText(null);
                }
            }
        });

        table.getColumns().addAll(colNome, colDesc, colPrezzo, colAttivo);

        /* ── Form acquisto ───────────────────────────────── */
        final VBox formBox = new VBox(12);
        formBox.setStyle(StyleHelper.STYLE_CARD);
        formBox.setPadding(new Insets(20));

        final Label formTitle = new Label("Compila il modulo di acquisto");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        comboBiglietto = new ComboBox<>();
        comboBiglietto.setPromptText("Seleziona tipo biglietto...");
        comboBiglietto.setMaxWidth(Double.MAX_VALUE);

        final Label lblQuantita = new Label("Quantità:");
        lblQuantita.setStyle(StyleHelper.STYLE_LABEL);
        spinnerQuantita = new Spinner<>(1, 99, 1);
        spinnerQuantita.setPrefWidth(100);
        spinnerQuantita.setEditable(true);

        final HBox quantitaRow = new HBox(12, lblQuantita, spinnerQuantita);
        quantitaRow.setAlignment(Pos.CENTER_LEFT);

        final Label lblGruppo = new Label("Nome gruppo (opzionale):");
        lblGruppo.setStyle(StyleHelper.STYLE_LABEL);
        txtNomeGruppo = new TextField();
        txtNomeGruppo.setStyle(StyleHelper.STYLE_TEXT_FIELD);
        txtNomeGruppo.setPromptText("Es. Scuola Leopardi");

        lblNumPersone = new Label("Numero persone nel gruppo:");
        lblNumPersone.setStyle(StyleHelper.STYLE_LABEL);
        lblNumPersone.setVisible(false);
        lblNumPersone.setManaged(false);

        spinnerNumPersone = new Spinner<>(1, 200, 1);
        spinnerNumPersone.setPrefWidth(100);
        spinnerNumPersone.setEditable(true);
        spinnerNumPersone.setVisible(false);
        spinnerNumPersone.setManaged(false);

        final HBox numPersoneRow = new HBox(12, lblNumPersone, spinnerNumPersone);
        numPersoneRow.setAlignment(Pos.CENTER_LEFT);

        btnConferma = new Button("Conferma acquisto");
        btnConferma.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnConferma.setOnMouseEntered(e -> btnConferma.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnConferma.setOnMouseExited(e -> btnConferma.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        lblMessaggio = new Label();
        lblMessaggio.setVisible(false);

        formBox.getChildren().addAll(
                formTitle,
                comboBiglietto,
                quantitaRow,
                lblGruppo, txtNomeGruppo,
                numPersoneRow,
                btnConferma,
                lblMessaggio
        );

        root.getChildren().addAll(title, table, formBox);
    }

    /* ── Metodi pubblici per il Controller ────────────── */

    public VBox getRoot() { return root; }

    public TableView<TipoBiglietto> getTable() { return table; }

    public ComboBox<String> getComboBiglietto() { return comboBiglietto; }

    public Spinner<Integer> getSpinnerQuantita() { return spinnerQuantita; }

    public TextField getTxtNomeGruppo() { return txtNomeGruppo; }

    public Spinner<Integer> getSpinnerNumPersone() { return spinnerNumPersone; }

    public Label getLblNumPersone() { return lblNumPersone; }

    public Button getBtnConferma() { return btnConferma; }

    /**
     * Aggiorna la tabella con la lista di tipi biglietto.
     */
    public void setTipiBiglietto(final List<TipoBiglietto> tipi) {
        table.setItems(FXCollections.observableArrayList(tipi));
        comboBiglietto.getItems().clear();
        for (final TipoBiglietto t : tipi) {
            if (t.isAttivo()) {
                comboBiglietto.getItems().add(t.getNome());
            }
        }
    }

    /**
     * Mostra il campo numero persone.
     */
    public void setNumPersoneVisible(final boolean visible) {
        lblNumPersone.setVisible(visible);
        lblNumPersone.setManaged(visible);
        spinnerNumPersone.setVisible(visible);
        spinnerNumPersone.setManaged(visible);
    }

    /**
     * Mostra un messaggio di successo.
     */
    public void showSuccess(final String msg) {
        lblMessaggio.setText(msg);
        lblMessaggio.setStyle(StyleHelper.STYLE_SUCCESS_LABEL);
        lblMessaggio.setVisible(true);
    }

    /**
     * Mostra un messaggio di errore.
     */
    public void showError(final String msg) {
        lblMessaggio.setText(msg);
        lblMessaggio.setStyle(StyleHelper.STYLE_ERROR_LABEL);
        lblMessaggio.setVisible(true);
    }

    /**
     * Nasconde il messaggio.
     */
    public void hideMessage() {
        lblMessaggio.setVisible(false);
    }
}
