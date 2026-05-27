package it.unibo.zoo.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Pannello di gestione con 4 tab: Saldo, Ordini, Animali in cura, Turni.
 */
public class GestioneView {

    /* ── DTO per le righe delle tabelle ───────────────── */

    public static class TransazioneRow {
        private final String data;
        private final String tipo;
        private final String importo;
        private final String categoria;
        private final String descrizione;

        public TransazioneRow(final String data, final String tipo, final String importo,
                              final String categoria, final String descrizione) {
            this.data = data;
            this.tipo = tipo;
            this.importo = importo;
            this.categoria = categoria;
            this.descrizione = descrizione;
        }

        public String getData() { return data; }
        public String getTipo() { return tipo; }
        public String getImporto() { return importo; }
        public String getCategoria() { return categoria; }
        public String getDescrizione() { return descrizione; }
    }

    public static class OrdineRow {
        private final String data;
        private final String fornitore;
        private final String tipoCibo;
        private final String quantitaKg;
        private final String stato;

        public OrdineRow(final String data, final String fornitore, final String tipoCibo,
                         final String quantitaKg, final String stato) {
            this.data = data;
            this.fornitore = fornitore;
            this.tipoCibo = tipoCibo;
            this.quantitaKg = quantitaKg;
            this.stato = stato;
        }

        public String getData() { return data; }
        public String getFornitore() { return fornitore; }
        public String getTipoCibo() { return tipoCibo; }
        public String getQuantitaKg() { return quantitaKg; }
        public String getStato() { return stato; }
    }

    public static class VisitaRow {
        private final String animale;
        private final String specie;
        private final String diagnosi;
        private final String dataVisita;
        private final String veterinario;
        private final String stato;

        public VisitaRow(final String animale, final String specie, final String diagnosi,
                         final String dataVisita, final String veterinario, final String stato) {
            this.animale = animale;
            this.specie = specie;
            this.diagnosi = diagnosi;
            this.dataVisita = dataVisita;
            this.veterinario = veterinario;
            this.stato = stato;
        }

        public String getAnimale() { return animale; }
        public String getSpecie() { return specie; }
        public String getDiagnosi() { return diagnosi; }
        public String getDataVisita() { return dataVisita; }
        public String getVeterinario() { return veterinario; }
        public String getStato() { return stato; }
    }

    public static class TurnoRow {
        private final String dipendente;
        private final String mansione;
        private final String area;
        private final String oraInizio;
        private final String oraFine;

        public TurnoRow(final String dipendente, final String mansione, final String area,
                        final String oraInizio, final String oraFine) {
            this.dipendente = dipendente;
            this.mansione = mansione;
            this.area = area;
            this.oraInizio = oraInizio;
            this.oraFine = oraFine;
        }

        public String getDipendente() { return dipendente; }
        public String getMansione() { return mansione; }
        public String getArea() { return area; }
        public String getOraInizio() { return oraInizio; }
        public String getOraFine() { return oraFine; }
    }

    /* ── Campi UI ─────────────────────────────────────── */

    private final VBox root;

    // Tab 1 — Saldo
    private final Label lblEntrate;
    private final Label lblUscite;
    private final Label lblSaldo;
    private final TableView<TransazioneRow> tableTransazioni;

    // Tab 2 — Ordini
    private final TableView<OrdineRow> tableOrdini;
    private final VBox panelNuovoOrdine;
    private final ComboBox<String> comboFornitore;
    private final ComboBox<String> comboTipoCibo;
    private final Spinner<Integer> spinnerQta;
    private final Button btnSalvaOrdine;
    private final Label lblOrdineMsg;
    private final Button btnNuovoOrdine;

    // Tab 3 — Animali in cura
    private final TableView<VisitaRow> tableVisite;

    // Tab 4 — Turni
    private final TableView<TurnoRow> tableTurni;

    @SuppressWarnings("unchecked")
    public GestioneView() {
        root = new VBox(16);
        root.setPadding(new Insets(32, 32, 32, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        final Label title = new Label("Pannello di gestione");
        title.setStyle(StyleHelper.STYLE_TITLE);

        final TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        /* ═══ TAB 1 — Saldo ══════════════════════════════ */
        final Tab tabSaldo = new Tab("\uD83D\uDCB0 Saldo");
        final VBox saldoContent = new VBox(16);
        saldoContent.setPadding(new Insets(20));

        lblEntrate = createMetricLabel("Totale Entrate", "\u20AC0,00", StyleHelper.GREEN);
        lblUscite  = createMetricLabel("Totale Uscite", "\u20AC0,00", StyleHelper.RED);
        lblSaldo   = createMetricLabel("Saldo Netto", "\u20AC0,00", StyleHelper.PRIMARY);

        final HBox metricsBox = new HBox(16, lblEntrate.getParent(), lblUscite.getParent(), lblSaldo.getParent());
        metricsBox.setAlignment(Pos.CENTER_LEFT);

        final Label lblUltimeTransazioni = new Label("Ultime transazioni");
        lblUltimeTransazioni.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        tableTransazioni = new TableView<>();
        tableTransazioni.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableTransazioni.setPrefHeight(200);

        final TableColumn<TransazioneRow, String> colTData = new TableColumn<>("Data");
        colTData.setCellValueFactory(new PropertyValueFactory<>("data"));

        final TableColumn<TransazioneRow, String> colTTipo = new TableColumn<>("Tipo");
        colTTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTTipo.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final String item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    final Label badge = new Label(item);
                    badge.setStyle(StyleHelper.badge("E".equals(item) ? StyleHelper.GREEN : StyleHelper.RED));
                    setGraphic(badge);
                }
                setText(null);
            }
        });
        colTTipo.setMaxWidth(70);

        final TableColumn<TransazioneRow, String> colTImporto = new TableColumn<>("Importo");
        colTImporto.setCellValueFactory(new PropertyValueFactory<>("importo"));

        final TableColumn<TransazioneRow, String> colTCat = new TableColumn<>("Categoria");
        colTCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        final TableColumn<TransazioneRow, String> colTDesc = new TableColumn<>("Descrizione");
        colTDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        tableTransazioni.getColumns().addAll(colTData, colTTipo, colTImporto, colTCat, colTDesc);

        saldoContent.getChildren().addAll(metricsBox, lblUltimeTransazioni, tableTransazioni);
        tabSaldo.setContent(saldoContent);

        /* ═══ TAB 2 — Ordini giornalieri ═════════════════ */
        final Tab tabOrdini = new Tab("\uD83D\uDCE6 Ordini giornalieri");
        final VBox ordiniContent = new VBox(16);
        ordiniContent.setPadding(new Insets(20));

        tableOrdini = new TableView<>();
        tableOrdini.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableOrdini.setPrefHeight(200);

        final TableColumn<OrdineRow, String> colOData = new TableColumn<>("Data");
        colOData.setCellValueFactory(new PropertyValueFactory<>("data"));

        final TableColumn<OrdineRow, String> colOForn = new TableColumn<>("Fornitore");
        colOForn.setCellValueFactory(new PropertyValueFactory<>("fornitore"));

        final TableColumn<OrdineRow, String> colOCibo = new TableColumn<>("Tipo Cibo");
        colOCibo.setCellValueFactory(new PropertyValueFactory<>("tipoCibo"));

        final TableColumn<OrdineRow, String> colOQta = new TableColumn<>("Quantità kg");
        colOQta.setCellValueFactory(new PropertyValueFactory<>("quantitaKg"));

        final TableColumn<OrdineRow, String> colOStato = new TableColumn<>("Stato");
        colOStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colOStato.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final String item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    final Label badge = new Label(item);
                    badge.setStyle(StyleHelper.badge(
                            "Pagato".equals(item) ? StyleHelper.GREEN : StyleHelper.YELLOW));
                    if ("Da pagare".equals(item)) {
                        badge.setStyle(StyleHelper.badge(StyleHelper.YELLOW)
                                .replace(StyleHelper.WHITE, StyleHelper.TEXT_MAIN));
                    }
                    setGraphic(badge);
                }
                setText(null);
            }
        });

        tableOrdini.getColumns().addAll(colOData, colOForn, colOCibo, colOQta, colOStato);

        btnNuovoOrdine = new Button("Nuovo ordine");
        btnNuovoOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnNuovoOrdine.setOnMouseEntered(e -> btnNuovoOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnNuovoOrdine.setOnMouseExited(e -> btnNuovoOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        // Sotto-pannello nuovo ordine
        panelNuovoOrdine = new VBox(12);
        panelNuovoOrdine.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoOrdine.setPadding(new Insets(16));
        panelNuovoOrdine.setVisible(false);
        panelNuovoOrdine.setManaged(false);

        final Label lblNewOrd = new Label("Nuovo ordine");
        lblNewOrd.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        comboFornitore = new ComboBox<>();
        comboFornitore.setPromptText("Seleziona fornitore...");
        comboFornitore.setMaxWidth(Double.MAX_VALUE);

        comboTipoCibo = new ComboBox<>();
        comboTipoCibo.setPromptText("Seleziona tipo cibo...");
        comboTipoCibo.setMaxWidth(Double.MAX_VALUE);

        final Label lblQta = new Label("Quantità (kg):");
        lblQta.setStyle(StyleHelper.STYLE_LABEL);
        spinnerQta = new Spinner<>(1, 1000, 10);
        spinnerQta.setPrefWidth(120);
        spinnerQta.setEditable(true);

        final HBox qtaRow = new HBox(12, lblQta, spinnerQta);
        qtaRow.setAlignment(Pos.CENTER_LEFT);

        btnSalvaOrdine = new Button("Salva");
        btnSalvaOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnSalvaOrdine.setOnMouseEntered(e -> btnSalvaOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnSalvaOrdine.setOnMouseExited(e -> btnSalvaOrdine.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        lblOrdineMsg = new Label();
        lblOrdineMsg.setVisible(false);

        panelNuovoOrdine.getChildren().addAll(lblNewOrd, comboFornitore, comboTipoCibo, qtaRow, btnSalvaOrdine, lblOrdineMsg);

        ordiniContent.getChildren().addAll(tableOrdini, btnNuovoOrdine, panelNuovoOrdine);
        tabOrdini.setContent(ordiniContent);

        /* ═══ TAB 3 — Animali in cura ════════════════════ */
        final Tab tabVisite = new Tab("\uD83C\uDFE5 Animali in cura");
        final VBox visiteContent = new VBox(16);
        visiteContent.setPadding(new Insets(20));

        tableVisite = new TableView<>();
        tableVisite.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<VisitaRow, String> colVAnimale = new TableColumn<>("Animale");
        colVAnimale.setCellValueFactory(new PropertyValueFactory<>("animale"));

        final TableColumn<VisitaRow, String> colVSpecie = new TableColumn<>("Specie");
        colVSpecie.setCellValueFactory(new PropertyValueFactory<>("specie"));

        final TableColumn<VisitaRow, String> colVDiag = new TableColumn<>("Diagnosi");
        colVDiag.setCellValueFactory(new PropertyValueFactory<>("diagnosi"));

        final TableColumn<VisitaRow, String> colVData = new TableColumn<>("Data visita");
        colVData.setCellValueFactory(new PropertyValueFactory<>("dataVisita"));

        final TableColumn<VisitaRow, String> colVVet = new TableColumn<>("Veterinario");
        colVVet.setCellValueFactory(new PropertyValueFactory<>("veterinario"));

        final TableColumn<VisitaRow, String> colVStato = new TableColumn<>("Stato");
        colVStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colVStato.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final String item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    final Label badge = new Label(item);
                    badge.setStyle(StyleHelper.badge(
                            "Concluso".equals(item) ? StyleHelper.GREEN : StyleHelper.ORANGE));
                    setGraphic(badge);
                }
                setText(null);
            }
        });

        tableVisite.getColumns().addAll(colVAnimale, colVSpecie, colVDiag, colVData, colVVet, colVStato);

        visiteContent.getChildren().add(tableVisite);
        tabVisite.setContent(visiteContent);

        /* ═══ TAB 4 — Turni del giorno ═══════════════════ */
        final Tab tabTurni = new Tab("\uD83D\uDC77 Turni del giorno");
        final VBox turniContent = new VBox(16);
        turniContent.setPadding(new Insets(20));

        tableTurni = new TableView<>();
        tableTurni.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<TurnoRow, String> colTDip = new TableColumn<>("Dipendente");
        colTDip.setCellValueFactory(new PropertyValueFactory<>("dipendente"));

        final TableColumn<TurnoRow, String> colTMans = new TableColumn<>("Mansione");
        colTMans.setCellValueFactory(new PropertyValueFactory<>("mansione"));

        final TableColumn<TurnoRow, String> colTArea = new TableColumn<>("Area");
        colTArea.setCellValueFactory(new PropertyValueFactory<>("area"));

        final TableColumn<TurnoRow, String> colTInizio = new TableColumn<>("Ora inizio");
        colTInizio.setCellValueFactory(new PropertyValueFactory<>("oraInizio"));

        final TableColumn<TurnoRow, String> colTFine = new TableColumn<>("Ora fine");
        colTFine.setCellValueFactory(new PropertyValueFactory<>("oraFine"));

        tableTurni.getColumns().addAll(colTDip, colTMans, colTArea, colTInizio, colTFine);

        turniContent.getChildren().add(tableTurni);
        tabTurni.setContent(turniContent);

        /* ── Assembla TabPane ────────────────────────────── */
        tabPane.getTabs().addAll(tabSaldo, tabOrdini, tabVisite, tabTurni);

        root.getChildren().addAll(title, tabPane);
    }

    /* ── Metodi pubblici ─────────────────────────────── */

    public VBox getRoot() { return root; }

    /* Tab 1 — Saldo */
    public void setEntrate(final String value) { lblEntrate.setText(value); }
    public void setUscite(final String value)  { lblUscite.setText(value); }
    public void setSaldo(final String value)   { lblSaldo.setText(value); }

    public void setTransazioni(final List<TransazioneRow> rows) {
        tableTransazioni.setItems(FXCollections.observableArrayList(rows));
    }

    /* Tab 2 — Ordini */
    public void setOrdini(final List<OrdineRow> rows) {
        tableOrdini.setItems(FXCollections.observableArrayList(rows));
    }

    public Button getBtnNuovoOrdine()       { return btnNuovoOrdine; }
    public VBox getPanelNuovoOrdine()       { return panelNuovoOrdine; }
    public ComboBox<String> getComboFornitore() { return comboFornitore; }
    public ComboBox<String> getComboTipoCibo()  { return comboTipoCibo; }
    public Spinner<Integer> getSpinnerQta()     { return spinnerQta; }
    public Button getBtnSalvaOrdine()       { return btnSalvaOrdine; }

    public void showOrdineMsg(final String msg, final boolean success) {
        lblOrdineMsg.setText(msg);
        lblOrdineMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblOrdineMsg.setVisible(true);
    }

    public void setPanelNuovoOrdineVisible(final boolean visible) {
        panelNuovoOrdine.setVisible(visible);
        panelNuovoOrdine.setManaged(visible);
    }

    /* Tab 3 — Visite */
    public void setVisite(final List<VisitaRow> rows) {
        tableVisite.setItems(FXCollections.observableArrayList(rows));
    }

    /* Tab 4 — Turni */
    public void setTurni(final List<TurnoRow> rows) {
        tableTurni.setItems(FXCollections.observableArrayList(rows));
    }

    /* ── Utility interna ─────────────────────────────── */

    private Label createMetricLabel(final String labelText, final String value, final String color) {
        final VBox card = new VBox(4);
        card.setStyle(StyleHelper.STYLE_METRIC_CARD);
        card.setAlignment(Pos.CENTER_LEFT);

        final Label lbl = new Label(labelText);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + StyleHelper.TEXT_MUTED + ";");

        final Label val = new Label(value);
        val.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        card.getChildren().addAll(lbl, val);
        return val;
    }
}
