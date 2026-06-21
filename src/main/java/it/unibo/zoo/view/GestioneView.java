package it.unibo.zoo.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ScrollPane;
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

    
    public static class DipendenteRow {
        private final String codiceFiscale;
        private final String nome;
        private final String cognome;
        private final String dataNascita;
        private final String mansione;

        public DipendenteRow(final String cf, final String nome, final String cognome, final String dataNascita, final String mansione) {
            this.codiceFiscale = cf; this.nome = nome; this.cognome = cognome; this.dataNascita = dataNascita; this.mansione = mansione;
        }

        public String getCodiceFiscale() { return codiceFiscale; }
        public String getNome() { return nome; }
        public String getCognome() { return cognome; }
        public String getDataNascita() { return dataNascita; }
        public String getMansione() { return mansione; }
    }

    public static class SpesaRow {
        private final String data;
        private final String descrizione;
        private final String importo;

        public SpesaRow(final String data, final String descrizione, final String importo) {
            this.data = data;
            this.descrizione = descrizione;
            this.importo = importo;
        }

        public String getData() { return data; }
        public String getDescrizione() { return descrizione; }
        public String getImporto() { return importo; }
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
    private final Button btnNuovaVisita;
    private final VBox panelNuovaVisita;
    private final ComboBox<String> comboVisitaAnimale;
    private final ComboBox<String> comboVisitaVet;
    private final javafx.scene.control.TextField txtDiagnosi;
    private final javafx.scene.control.DatePicker dateVisita;
    private final Button btnSalvaVisita;
    private final Label lblVisitaMsg;

    // Tab 4 — Turni
    private final TableView<TurnoRow> tableTurni;
    private final Button btnNuovoTurno;
    private final VBox panelNuovoTurno;
    private final ComboBox<String> comboTurnoDip;
    private final ComboBox<String> comboTurnoArea;
    private final ComboBox<String> comboTurnoOraInizio;
    private final ComboBox<String> comboTurnoOraFine;
    private final Button btnSalvaTurno;
    private final Label lblTurnoMsg;

    // Tab 5 — Personale
    private final TableView<DipendenteRow> tablePersonale;
    private final Button btnNuovoDipendente;
    private final VBox panelNuovoDipendente;
    private final javafx.scene.control.TextField txtDipCf;
    private final javafx.scene.control.TextField txtDipNome;
    private final javafx.scene.control.TextField txtDipCognome;
    private final javafx.scene.control.DatePicker dateDipNascita;
    private final ComboBox<String> comboDipMansione;
    private final Button btnSalvaDipendente;
    private final Label lblDipMsg;

    // Tab 6 — Spese
    private final TableView<SpesaRow> tableSpese;
    private final DatePicker dateSpesaInizio;
    private final DatePicker dateSpesaFine;
    private final Button btnFiltraSpese;
    private final Button btnNuovaSpesa;
    private final VBox panelNuovaSpesa;
    private final TextField txtSpesaImporto;
    private final TextField txtSpesaDescrizione;
    private final ComboBox<String> comboSpesaFornitore;
    private final Button btnSalvaSpesa;
    private final Label lblSpesaMsg;

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

        btnNuovaVisita = new Button("Nuova visita");
        btnNuovaVisita.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovaVisita = new VBox(12);
        panelNuovaVisita.setStyle(StyleHelper.STYLE_CARD);
        panelNuovaVisita.setPadding(new Insets(16));
        panelNuovaVisita.setVisible(false);
        panelNuovaVisita.setManaged(false);

        comboVisitaAnimale = new ComboBox<>(); comboVisitaAnimale.setPromptText("Seleziona animale...");
        comboVisitaVet = new ComboBox<>(); comboVisitaVet.setPromptText("Seleziona veterinario...");
        txtDiagnosi = new javafx.scene.control.TextField(); txtDiagnosi.setPromptText("Diagnosi...");
        dateVisita = new javafx.scene.control.DatePicker(); dateVisita.setPromptText("Data visita...");
        btnSalvaVisita = new Button("Salva");
        btnSalvaVisita.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblVisitaMsg = new Label(); lblVisitaMsg.setVisible(false);

        panelNuovaVisita.getChildren().addAll(new Label("Nuova Visita"), comboVisitaAnimale, comboVisitaVet, txtDiagnosi, dateVisita, btnSalvaVisita, lblVisitaMsg);

        visiteContent.getChildren().addAll(tableVisite, btnNuovaVisita, panelNuovaVisita);
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

        btnNuovoTurno = new Button("Nuovo turno");
        btnNuovoTurno.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovoTurno = new VBox(12);
        panelNuovoTurno.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoTurno.setPadding(new Insets(16));
        panelNuovoTurno.setVisible(false);
        panelNuovoTurno.setManaged(false);

        comboTurnoDip = new ComboBox<>(); comboTurnoDip.setPromptText("Seleziona dipendente...");
        comboTurnoArea = new ComboBox<>(); comboTurnoArea.setPromptText("Seleziona area...");
        comboTurnoOraInizio = new ComboBox<>(); comboTurnoOraInizio.setPromptText("Ora inizio (HH:mm)...");
        comboTurnoOraFine = new ComboBox<>(); comboTurnoOraFine.setPromptText("Ora fine (HH:mm)...");
        
        for(int i=8; i<=20; i++) {
            comboTurnoOraInizio.getItems().add(String.format("%02d:00", i));
            comboTurnoOraFine.getItems().add(String.format("%02d:00", i));
        }

        btnSalvaTurno = new Button("Salva");
        btnSalvaTurno.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblTurnoMsg = new Label(); lblTurnoMsg.setVisible(false);

        panelNuovoTurno.getChildren().addAll(new Label("Nuovo Turno"), comboTurnoDip, comboTurnoArea, comboTurnoOraInizio, comboTurnoOraFine, btnSalvaTurno, lblTurnoMsg);

        turniContent.getChildren().addAll(tableTurni, btnNuovoTurno, panelNuovoTurno);
        tabTurni.setContent(turniContent);


        /* ═══ TAB 5 — Personale ══════════════════════════ */
        final Tab tabPersonale = new Tab("\uD83D\uDC65 Personale");
        final VBox personaleContent = new VBox(16);
        personaleContent.setPadding(new Insets(20));

        tablePersonale = new TableView<>();
        tablePersonale.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<DipendenteRow, String> colDipCf = new TableColumn<>("CF");
        colDipCf.setCellValueFactory(new PropertyValueFactory<>("codiceFiscale"));

        final TableColumn<DipendenteRow, String> colDipNome = new TableColumn<>("Nome");
        colDipNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        final TableColumn<DipendenteRow, String> colDipCognome = new TableColumn<>("Cognome");
        colDipCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        final TableColumn<DipendenteRow, String> colDipDataN = new TableColumn<>("Data Nascita");
        colDipDataN.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));

        final TableColumn<DipendenteRow, String> colDipMans = new TableColumn<>("Mansione");
        colDipMans.setCellValueFactory(new PropertyValueFactory<>("mansione"));

        tablePersonale.getColumns().addAll(colDipCf, colDipNome, colDipCognome, colDipDataN, colDipMans);

        btnNuovoDipendente = new Button("Assumi Dipendente");
        btnNuovoDipendente.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovoDipendente = new VBox(12);
        panelNuovoDipendente.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoDipendente.setPadding(new Insets(16));
        panelNuovoDipendente.setVisible(false);
        panelNuovoDipendente.setManaged(false);

        txtDipCf = new javafx.scene.control.TextField(); txtDipCf.setPromptText("Codice Fiscale...");
        txtDipNome = new javafx.scene.control.TextField(); txtDipNome.setPromptText("Nome...");
        txtDipCognome = new javafx.scene.control.TextField(); txtDipCognome.setPromptText("Cognome...");
        dateDipNascita = new javafx.scene.control.DatePicker(); dateDipNascita.setPromptText("Data Nascita...");
        comboDipMansione = new ComboBox<>(); comboDipMansione.setPromptText("Seleziona Mansione...");
        
        btnSalvaDipendente = new Button("Salva");
        btnSalvaDipendente.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblDipMsg = new Label(); lblDipMsg.setVisible(false);

        panelNuovoDipendente.getChildren().addAll(new Label("Nuovo Dipendente"), txtDipCf, txtDipNome, txtDipCognome, dateDipNascita, comboDipMansione, btnSalvaDipendente, lblDipMsg);

        personaleContent.getChildren().addAll(tablePersonale, btnNuovoDipendente, panelNuovoDipendente);
        tabPersonale.setContent(personaleContent);

        /* ═══ TAB 6 — Spese ══════════════════════════════ */
        final Tab tabSpese = new Tab("\uD83D\uDCB8 Spese");
        final VBox speseContent = new VBox(16);
        speseContent.setPadding(new Insets(20));

        // Filtro per intervallo di tempo
        final Label lblFiltroSpese = new Label("Filtra per periodo");
        lblFiltroSpese.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        dateSpesaInizio = new DatePicker();
        dateSpesaInizio.setPromptText("Data Inizio...");
        dateSpesaInizio.setPrefWidth(160);

        dateSpesaFine = new DatePicker();
        dateSpesaFine.setPromptText("Data Fine...");
        dateSpesaFine.setPrefWidth(160);

        btnFiltraSpese = new Button("Filtra");
        btnFiltraSpese.setStyle(StyleHelper.STYLE_BTN_OUTLINE);
        btnFiltraSpese.setOnMouseEntered(e -> btnFiltraSpese.setStyle(StyleHelper.STYLE_BTN_OUTLINE_HOVER));
        btnFiltraSpese.setOnMouseExited(e -> btnFiltraSpese.setStyle(StyleHelper.STYLE_BTN_OUTLINE));

        final Button btnResetFiltro = new Button("Reset");
        btnResetFiltro.setStyle(StyleHelper.STYLE_BTN_OUTLINE);
        btnResetFiltro.setOnMouseEntered(e -> btnResetFiltro.setStyle(StyleHelper.STYLE_BTN_OUTLINE_HOVER));
        btnResetFiltro.setOnMouseExited(e -> btnResetFiltro.setStyle(StyleHelper.STYLE_BTN_OUTLINE));
        btnResetFiltro.setOnAction(e -> {
            dateSpesaInizio.setValue(null);
            dateSpesaFine.setValue(null);
        });

        final HBox filtroRow = new HBox(12, new Label("Da:"), dateSpesaInizio, new Label("A:"), dateSpesaFine, btnFiltraSpese, btnResetFiltro);
        filtroRow.setAlignment(Pos.CENTER_LEFT);
        filtroRow.setStyle(StyleHelper.STYLE_CARD);
        filtroRow.setPadding(new Insets(12));
        for (javafx.scene.Node n : filtroRow.getChildren()) {
            if (n instanceof Label) {
                n.setStyle(StyleHelper.STYLE_LABEL);
            }
        }

        // Tabella spese
        final Label lblElencoSpese = new Label("Elenco spese");
        lblElencoSpese.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        tableSpese = new TableView<>();
        tableSpese.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableSpese.setPrefHeight(300);

        final TableColumn<SpesaRow, String> colSData = new TableColumn<>("Data");
        colSData.setCellValueFactory(new PropertyValueFactory<>("data"));

        final TableColumn<SpesaRow, String> colSDesc = new TableColumn<>("Descrizione");
        colSDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        final TableColumn<SpesaRow, String> colSImporto = new TableColumn<>("Importo");
        colSImporto.setCellValueFactory(new PropertyValueFactory<>("importo"));

        tableSpese.getColumns().addAll(colSData, colSDesc, colSImporto);

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

        speseContent.getChildren().addAll(lblFiltroSpese, filtroRow, lblElencoSpese, tableSpese, btnNuovaSpesa, panelNuovaSpesa);

        final ScrollPane speseScroll = new ScrollPane(speseContent);
        speseScroll.setFitToWidth(true);
        speseScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        tabSpese.setContent(speseScroll);

        /* ── Assembla TabPane ────────────────────────────── */
        tabPane.getTabs().addAll(tabSaldo, tabSpese, tabOrdini, tabVisite, tabTurni, tabPersonale);


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
    public Button getBtnNuovaVisita() { return btnNuovaVisita; }
    public VBox getPanelNuovaVisita() { return panelNuovaVisita; }
    public ComboBox<String> getComboVisitaAnimale() { return comboVisitaAnimale; }
    public ComboBox<String> getComboVisitaVet() { return comboVisitaVet; }
    public javafx.scene.control.TextField getTxtDiagnosi() { return txtDiagnosi; }
    public javafx.scene.control.DatePicker getDateVisita() { return dateVisita; }
    public Button getBtnSalvaVisita() { return btnSalvaVisita; }
    public void showVisitaMsg(final String msg, final boolean success) {
        lblVisitaMsg.setText(msg);
        lblVisitaMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblVisitaMsg.setVisible(true);
    }
    public void setPanelNuovaVisitaVisible(final boolean visible) {
        panelNuovaVisita.setVisible(visible); panelNuovaVisita.setManaged(visible);
    }

    /* Tab 4 — Turni */
    public void setTurni(final List<TurnoRow> rows) {
        tableTurni.setItems(FXCollections.observableArrayList(rows));
    }
    public Button getBtnNuovoTurno() { return btnNuovoTurno; }
    public VBox getPanelNuovoTurno() { return panelNuovoTurno; }
    public ComboBox<String> getComboTurnoDip() { return comboTurnoDip; }
    public ComboBox<String> getComboTurnoArea() { return comboTurnoArea; }
    public ComboBox<String> getComboTurnoOraInizio() { return comboTurnoOraInizio; }
    public ComboBox<String> getComboTurnoOraFine() { return comboTurnoOraFine; }
    public Button getBtnSalvaTurno() { return btnSalvaTurno; }
    public void showTurnoMsg(final String msg, final boolean success) {
        lblTurnoMsg.setText(msg);
        lblTurnoMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblTurnoMsg.setVisible(true);
    }
    public void setPanelNuovoTurnoVisible(final boolean visible) {
        panelNuovoTurno.setVisible(visible); panelNuovoTurno.setManaged(visible);
    }

    /* Tab 5 — Personale */
    public void setPersonale(final List<DipendenteRow> rows) {
        tablePersonale.setItems(FXCollections.observableArrayList(rows));
    }
    public Button getBtnNuovoDipendente() { return btnNuovoDipendente; }
    public VBox getPanelNuovoDipendente() { return panelNuovoDipendente; }
    public javafx.scene.control.TextField getTxtDipCf() { return txtDipCf; }
    public javafx.scene.control.TextField getTxtDipNome() { return txtDipNome; }
    public javafx.scene.control.TextField getTxtDipCognome() { return txtDipCognome; }
    public javafx.scene.control.DatePicker getDateDipNascita() { return dateDipNascita; }
    public ComboBox<String> getComboDipMansione() { return comboDipMansione; }
    public Button getBtnSalvaDipendente() { return btnSalvaDipendente; }
    public void showDipendenteMsg(final String msg, final boolean success) {
        lblDipMsg.setText(msg);
        lblDipMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblDipMsg.setVisible(true);
    }
    public void setPanelNuovoDipendenteVisible(final boolean visible) {
        panelNuovoDipendente.setVisible(visible); panelNuovoDipendente.setManaged(visible);
    }

    /* Tab 6 — Spese */
    public void setSpese(final List<SpesaRow> rows) {
        tableSpese.setItems(FXCollections.observableArrayList(rows));
    }
    public DatePicker getDateSpesaInizio() { return dateSpesaInizio; }
    public DatePicker getDateSpesaFine() { return dateSpesaFine; }
    public Button getBtnFiltraSpese() { return btnFiltraSpese; }
    public Button getBtnNuovaSpesa() { return btnNuovaSpesa; }
    public VBox getPanelNuovaSpesa() { return panelNuovaSpesa; }
    public TextField getTxtSpesaImporto() { return txtSpesaImporto; }
    public TextField getTxtSpesaDescrizione() { return txtSpesaDescrizione; }
    public Button getBtnSalvaSpesa() { return btnSalvaSpesa; }
    public void showSpesaMsg(final String msg, final boolean success) {
        lblSpesaMsg.setText(msg);
        lblSpesaMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblSpesaMsg.setVisible(true);
    }
    public void setPanelNuovaSpesaVisible(final boolean visible) {
        panelNuovaSpesa.setVisible(visible); panelNuovaSpesa.setManaged(visible);
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
