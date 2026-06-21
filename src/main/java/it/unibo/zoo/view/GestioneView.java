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

        public OrdineRow(final String data, final String fornitore, final String tipoCibo,
                         final String quantitaKg) {
            this.data = data;
            this.fornitore = fornitore;
            this.tipoCibo = tipoCibo;
            this.quantitaKg = quantitaKg;
        }

        public String getData() { return data; }
        public String getFornitore() { return fornitore; }
        public String getTipoCibo() { return tipoCibo; }
        public String getQuantitaKg() { return quantitaKg; }
    }

    public static class VisitaRow {
        private final String idVisita;
        private final String idAnimale;
        private final String idVet;
        private final String animale;
        private final String specie;
        private final String diagnosi;
        private final String peso;
        private final String note;
        private final String dataVisita;
        private final String dataFine;
        private final String veterinario;

        public VisitaRow(final String idVisita, final String idAnimale, final String idVet, final String animale, final String specie, 
                         final String diagnosi, final String peso, final String note, final String dataVisita, 
                         final String dataFine, final String veterinario) {
            this.idVisita = idVisita;
            this.idAnimale = idAnimale;
            this.idVet = idVet;
            this.animale = animale;
            this.specie = specie;
            this.diagnosi = diagnosi;
            this.peso = peso;
            this.note = note;
            this.dataVisita = dataVisita;
            this.dataFine = dataFine;
            this.veterinario = veterinario;
        }

        public String getIdVisita() { return idVisita; }
        public String getIdAnimale() { return idAnimale; }
        public String getIdVet() { return idVet; }
        public String getAnimale() { return animale; }
        public String getSpecie() { return specie; }
        public String getDiagnosi() { return diagnosi; }
        public String getPeso() { return peso; }
        public String getNote() { return note; }
        public String getDataVisita() { return dataVisita; }
        public String getDataFine() { return dataFine; }
        public String getVeterinario() { return veterinario; }
    }

    public static class TurnoRow {
        private final String idTurno;
        private final String idDipendente;
        private final String idArea;
        private final String dipendente;
        private final String mansione;
        private final String area;
        private final String oraInizio;
        private final String oraFine;

        public TurnoRow(final String idTurno, final String idDipendente, final String idArea, 
                        final String dipendente, final String mansione, final String area,
                        final String oraInizio, final String oraFine) {
            this.idTurno = idTurno;
            this.idDipendente = idDipendente;
            this.idArea = idArea;
            this.dipendente = dipendente;
            this.mansione = mansione;
            this.area = area;
            this.oraInizio = oraInizio;
            this.oraFine = oraFine;
        }

        public String getIdTurno() { return idTurno; }
        public String getIdDipendente() { return idDipendente; }
        public String getIdArea() { return idArea; }

        public String getDipendente() { return dipendente; }
        public String getMansione() { return mansione; }
        public String getArea() { return area; }
        public String getOraInizio() { return oraInizio; }
        public String getOraFine() { return oraFine; }
    }

    
    public static class DipendenteRow {
        private final String idDipendente;
        private final String codiceFiscale;
        private final String nome;
        private final String cognome;
        private final String dataNascita;
        private final String mansione;

        public DipendenteRow(final String idDipendente, final String cf, final String nome, final String cognome, final String dataNascita, final String mansione) {
            this.idDipendente = idDipendente; this.codiceFiscale = cf; this.nome = nome; this.cognome = cognome; this.dataNascita = dataNascita; this.mansione = mansione;
        }

        public String getIdDipendente() { return idDipendente; }

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

    public static class AnimaleRow {
        private final String idAnimale;
        private final String nome;
        private final String sesso;
        private final String dataNascita;
        private final String dataArrivo;
        private final String dataUscita;
        private final String specie;
        private final String vivo;

        public AnimaleRow(final String idAnimale, final String nome, final String sesso, final String dataNascita, final String dataArrivo, final String dataUscita, final String specie, final String vivo) {
            this.idAnimale = idAnimale; this.nome = nome; this.sesso = sesso; this.dataNascita = dataNascita; this.dataArrivo = dataArrivo; this.dataUscita = dataUscita; this.specie = specie; this.vivo = vivo;
        }

        public String getIdAnimale() { return idAnimale; }
        public String getNome() { return nome; }
        public String getSesso() { return sesso; }
        public String getDataNascita() { return dataNascita; }
        public String getDataArrivo() { return dataArrivo; }
        public String getDataUscita() { return dataUscita; }
        public String getSpecie() { return specie; }
        public String getVivo() { return vivo; }
    }

    public static class AreaRow {
        private final String idArea;
        private final String nome;
        private final String metratura;
        private final String tipoArea;

        public AreaRow(final String idArea, final String nome, final String metratura, final String tipoArea) {
            this.idArea = idArea; this.nome = nome; this.metratura = metratura; this.tipoArea = tipoArea;
        }

        public String getIdArea() { return idArea; }
        public String getNome() { return nome; }
        public String getMetratura() { return metratura; }
        public String getTipoArea() { return tipoArea; }
    }

    public static class RecintoRow {
        private final String idRecinto;
        private final String nome;
        private final String area;
        private final String capienza;
        private final String tipo;

        public RecintoRow(final String idRecinto, final String nome, final String area, final String capienza, final String tipo) {
            this.idRecinto = idRecinto; this.nome = nome; this.area = area; this.capienza = capienza; this.tipo = tipo;
        }

        public String getIdRecinto() { return idRecinto; }
        public String getNome() { return nome; }
        public String getArea() { return area; }
        public String getCapienza() { return capienza; }
        public String getTipo() { return tipo; }
    }

    public static class TipiBigliettiRow {
        private final String idBiglietto;
        private final String nome;
        private final String prezzo;

        public TipiBigliettiRow(final String idBiglietto, final String nome, final String prezzo) {
            this.idBiglietto = idBiglietto;
            this.nome = nome;
            this.prezzo = prezzo;
        }

        public String getIdBiglietto() { return idBiglietto; }
        public String getNome() { return nome; }
        public String getPrezzo() { return prezzo; }
    }

    public static class StatoEsistenzaRow {
        private final String idStato;
        private final String nome;
        private final String descrizione;

        public StatoEsistenzaRow(final String idStato, final String nome, final String descrizione) {
            this.idStato = idStato; this.nome = nome; this.descrizione = descrizione;
        }

        public String getIdStato() { return idStato; }
        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
    }

    public static class HabitatRow {
        private final String idHabitat;
        private final String nome;
        private final String descrizione;

        public HabitatRow(final String idHabitat, final String nome, final String descrizione) {
            this.idHabitat = idHabitat; this.nome = nome; this.descrizione = descrizione;
        }

        public String getIdHabitat() { return idHabitat; }
        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
    }

    public static class FamigliaSpecieRow {
        private final String idFamiglia;
        private final String nome;
        private final String descrizione;

        public FamigliaSpecieRow(final String idFamiglia, final String nome, final String descrizione) {
            this.idFamiglia = idFamiglia; this.nome = nome; this.descrizione = descrizione;
        }

        public String getIdFamiglia() { return idFamiglia; }
        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
    }

    public static class TipoAreaRow {
        private final String idTipoArea;
        private final String nome;
        private final String descrizione;
        public TipoAreaRow(String idTipoArea, String nome, String descrizione) {
            this.idTipoArea = idTipoArea; this.nome = nome; this.descrizione = descrizione;
        }
        public String getIdTipoArea() { return idTipoArea; }
        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
    }

    public static class TipoRecintoRow {
        private final String idTipoRecinto;
        private final String nome;
        private final String descrizione;
        public TipoRecintoRow(String idTipoRecinto, String nome, String descrizione) {
            this.idTipoRecinto = idTipoRecinto; this.nome = nome; this.descrizione = descrizione;
        }
        public String getIdTipoRecinto() { return idTipoRecinto; }
        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
    }

    /* ── Campi UI ─────────────────────────────────────── */

    private final VBox root;
    private final TabPane tabPane;
    private final Tab tabSaldo;
    private final Tab tabStatistiche;
    private final Tab tabSpese;
    private final Tab tabOrdini;
    private final Tab tabVisite;
    private final Tab tabTurni;
    private final Tab tabPersonale;
    private final Tab tabAnimali;
    private final Tab tabAree;
    private final Tab tabRecinti;
    private final Tab tabTipiBiglietti;

    // Tab TipiBiglietti
    private final TableView<TipiBigliettiRow> tableTipiBiglietti;
    private final Button btnNuovoTipoBiglietto;
    private final Button btnSalvaTipoBiglietto;
    private final Button btnEliminaTipoBiglietto;
    private final VBox panelNuovoTipoBiglietto;
    private final TextField txtTipoBigliettoNome;
    private final TextField txtTipoBigliettoPrezzo;
    private final Label lblTipiBigliettiMsg;

    // Tab 1 — Saldo
    private final Label lblEntrate;
    private final Label lblUscite;
    private final Label lblSaldo;
    private final TableView<TransazioneRow> tableTransazioni;
    private final DatePicker dateSaldoInizio;
    private final DatePicker dateSaldoFine;
    private final Button btnCalcolaRicavo;
    private final Label lblRicavoTotale;

    // Tab 1b — Statistiche
    private final ComboBox<String> comboStatPeriodo;
    private final javafx.scene.chart.BarChart<String, Number> chartStatBiglietti;
    private final Label lblStatTopBiglietto;
    private final Label lblStatTotBiglietti;

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
    private final TextField txtDiagnosi;
    private final TextField txtVisitaPeso;
    private final TextField txtVisitaNote;
    private final DatePicker dateVisita;
    private final DatePicker dateVisitaFine;
    private final Button btnSalvaVisita;
    private final Label lblVisitaMsg;

    // Tab 4 — Turni
    private final TableView<TurnoRow> tableTurni;
    private final Button btnNuovoTurno;
    private final Button btnEliminaTurno;
    private final javafx.scene.control.ListView<String> listTopTurni;
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
    private final TextField txtDipCf;
    private final TextField txtDipNome;
    private final TextField txtDipCognome;
    private final DatePicker dateDipNascita;
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

    // Tab 7 - Animali
    private final TableView<AnimaleRow> tableAnimali;
    private final Button btnNuovoAnimale;
    private final VBox panelNuovoAnimale;
    private final TextField txtAnimaleNome;
    private final ComboBox<String> comboAnimaleSesso;
    private final DatePicker dateAnimaleNascita;
    private final DatePicker dateAnimaleArrivo;
    private final DatePicker dateAnimaleUscita;
    private final ComboBox<String> comboAnimaleVivo;
    private final ComboBox<String> comboAnimaleSpecie;
    private final Button btnSalvaAnimale;
    private final Label lblAnimaleMsg;

    // Tab 8 - Aree
    private final TableView<AreaRow> tableAree;
    private final Button btnNuovaArea;
    private final VBox panelNuovaArea;
    private final TextField txtAreaNome;
    private final Spinner<Integer> spinnerAreaMetratura;
    private final ComboBox<String> comboAreaTipo;
    private final Button btnSalvaArea;
    private final Label lblAreaMsg;

    // Tipo Area
    private final TableView<TipoAreaRow> tableTipoArea;
    private final Button btnAggiungiTipoArea;
    private final Button btnRimuoviTipoArea;
    private final TextField txtTipoAreaNome;
    private final TextField txtTipoAreaDesc;
    private final Label lblTipoAreaMsg;

    // Tipo Recinto
    private final TableView<TipoRecintoRow> tableTipoRecinto;
    private final Button btnAggiungiTipoRecinto;
    private final Button btnRimuoviTipoRecinto;
    private final TextField txtTipoRecintoNome;
    private final TextField txtTipoRecintoDesc;
    private final Label lblTipoRecintoMsg;

    // Tab 9 - Recinti
    private final TableView<RecintoRow> tableRecinti;
    private final Button btnNuovoRecinto;
    private final VBox panelNuovoRecinto;
    private final TextField txtRecintoNome;
    private final ComboBox<String> comboRecintoArea;
    private final ComboBox<String> comboRecintoTipo;
    private final Spinner<Integer> spinnerRecintoCapienza;
    private final Button btnSalvaRecinto;
    private final Label lblRecintoMsg;
    private final Label lblTopRecintoAnimali;

    // Tab Classificazione
    private final Tab tabClassificazione;

    // Stato Esistenza
    private final TableView<StatoEsistenzaRow> tableStatoEsistenza;
    private final Button btnAggiungiStato;
    private final Button btnRimuoviStato;
    private final TextField txtStatoNome;
    private final TextField txtStatoDesc;
    private final Label lblStatoMsg;

    // Habitat
    private final TableView<HabitatRow> tableHabitat;
    private final Button btnAggiungiHabitat;
    private final Button btnRimuoviHabitat;
    private final TextField txtHabitatNome;
    private final TextField txtHabitatDesc;
    private final Label lblHabitatMsg;

    // Famiglia Specie
    private final TableView<FamigliaSpecieRow> tableFamigliaSpecie;
    private final Button btnAggiungiFamiglia;
    private final Button btnRimuoviFamiglia;
    private final TextField txtFamigliaNome;
    private final TextField txtFamigliaDesc;
    private final Label lblFamigliaMsg;

    @SuppressWarnings("unchecked")
    public GestioneView() {
        root = new VBox(16);
        root.setPadding(new Insets(32, 32, 32, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        final Label title = new Label("Pannello di gestione");
        title.setStyle(StyleHelper.STYLE_TITLE);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        /* ═══ TAB 1 — Saldo ══════════════════════════════ */
        tabSaldo = new Tab("\uD83D\uDCB0 Saldo");
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

        final javafx.scene.layout.HBox dateRangeBox = new javafx.scene.layout.HBox(12);
        dateRangeBox.setAlignment(Pos.CENTER_LEFT);
        dateRangeBox.setPadding(new Insets(10, 0, 0, 0));
        
        dateSaldoInizio = new DatePicker();
        dateSaldoInizio.setPromptText("Data Inizio");
        dateSaldoFine = new DatePicker();
        dateSaldoFine.setPromptText("Data Fine");
        
        btnCalcolaRicavo = new Button("Calcola Ricavo");
        btnCalcolaRicavo.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        
        lblRicavoTotale = new Label("Ricavo nel periodo: -");
        lblRicavoTotale.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.PRIMARY + ";");
        
        dateRangeBox.getChildren().addAll(dateSaldoInizio, dateSaldoFine, btnCalcolaRicavo, lblRicavoTotale);

        saldoContent.getChildren().addAll(metricsBox, lblUltimeTransazioni, tableTransazioni, dateRangeBox);
        tabSaldo.setContent(saldoContent);

        /* ═══ TAB 1b - Statistiche ═════════════════════════════ */
        tabStatistiche = new Tab("\uD83D\uDCCA Statistiche");
        final VBox statContent = new VBox(16);
        statContent.setPadding(new Insets(20));

        final javafx.scene.layout.HBox statCtrlRow = new javafx.scene.layout.HBox(12);
        statCtrlRow.setAlignment(Pos.CENTER_LEFT);
        comboStatPeriodo = new ComboBox<>();
        comboStatPeriodo.getItems().addAll("Giorno", "Settimana", "Mese");
        comboStatPeriodo.setValue("Giorno");
        statCtrlRow.getChildren().addAll(new Label("Raggruppa per:"), comboStatPeriodo);

        final javafx.scene.chart.CategoryAxis xAxis = new javafx.scene.chart.CategoryAxis();
        xAxis.setLabel("Periodo");
        final javafx.scene.chart.NumberAxis yAxis = new javafx.scene.chart.NumberAxis();
        yAxis.setLabel("Biglietti Venduti");
        chartStatBiglietti = new javafx.scene.chart.BarChart<>(xAxis, yAxis);
        chartStatBiglietti.setTitle("Vendite Biglietti");
        chartStatBiglietti.setLegendVisible(false);

        lblStatTopBiglietto = createMetricLabel("Tipologia più venduta:", "-", StyleHelper.PRIMARY);
        lblStatTotBiglietti = createMetricLabel("Totale Biglietti:", "0", StyleHelper.TEXT_MAIN);
        final javafx.scene.layout.HBox statLabels = new javafx.scene.layout.HBox(20, lblStatTopBiglietto.getParent(), lblStatTotBiglietti.getParent());

        statContent.getChildren().addAll(statCtrlRow, chartStatBiglietti, statLabels);
        tabStatistiche.setContent(statContent);

        /* ═══ TAB Tipi Biglietto ═════════════════════════ */
        tabTipiBiglietti = new Tab("\uD83C\uDFAB Tipi Biglietto");
        final VBox tipiBigliettiContent = new VBox(16);
        tipiBigliettiContent.setPadding(new Insets(20));

        tableTipiBiglietti = new TableView<>();
        tableTipiBiglietti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableTipiBiglietti.setPrefHeight(200);

        final TableColumn<TipiBigliettiRow, String> colTbNome = new TableColumn<>("Nome");
        colTbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<TipiBigliettiRow, String> colTbPrezzo = new TableColumn<>("Prezzo");
        colTbPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

        tableTipiBiglietti.getColumns().addAll(colTbNome, colTbPrezzo);

        panelNuovoTipoBiglietto = new VBox(12);
        panelNuovoTipoBiglietto.setPadding(new Insets(15));
        panelNuovoTipoBiglietto.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoTipoBiglietto.setVisible(false);
        panelNuovoTipoBiglietto.setManaged(false);

        txtTipoBigliettoNome = new TextField();
        txtTipoBigliettoNome.setPromptText("Nome (es. Intero, Ridotto)");
        txtTipoBigliettoPrezzo = new TextField();
        txtTipoBigliettoPrezzo.setPromptText("Prezzo (es. 15.00)");
        
        btnSalvaTipoBiglietto = new Button("Salva Tipo Biglietto");
        btnSalvaTipoBiglietto.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        
        lblTipiBigliettiMsg = new Label();
        lblTipiBigliettiMsg.setManaged(true);
        lblTipiBigliettiMsg.setVisible(true);

        panelNuovoTipoBiglietto.getChildren().addAll(
                new Label("Crea Nuovo Tipo Biglietto"),
                new javafx.scene.layout.HBox(10, txtTipoBigliettoNome, txtTipoBigliettoPrezzo),
                btnSalvaTipoBiglietto
        );

        btnNuovoTipoBiglietto = new Button("Nuovo Tipo");
        btnNuovoTipoBiglietto.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        
        btnEliminaTipoBiglietto = new Button("Elimina Selezionato");
        btnEliminaTipoBiglietto.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        btnEliminaTipoBiglietto.setDisable(true);
        
        final javafx.scene.layout.HBox tipiBigliettiCtrl = new javafx.scene.layout.HBox(12, btnNuovoTipoBiglietto, btnEliminaTipoBiglietto);

        tipiBigliettiContent.getChildren().addAll(tableTipiBiglietti, tipiBigliettiCtrl, panelNuovoTipoBiglietto, lblTipiBigliettiMsg);
        tabTipiBiglietti.setContent(tipiBigliettiContent);

        /* ═══ TAB 2 — Ordini giornalieri ═════════════════ */
        tabOrdini = new Tab("\uD83D\uDCDD Ordini Giornalieri");
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

        tableOrdini.getColumns().addAll(colOData, colOForn, colOCibo, colOQta);

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
        tabVisite = new Tab("\uD83C\uDFE5 Animali in cura");
        final VBox visiteContent = new VBox(16);
        visiteContent.setPadding(new Insets(20));

        tableVisite = new TableView<>();
        tableVisite.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<VisitaRow, String> colVId = new TableColumn<>("ID");
        colVId.setCellValueFactory(new PropertyValueFactory<>("idVisita"));
        colVId.setMaxWidth(50);

        final TableColumn<VisitaRow, String> colVAnimale = new TableColumn<>("Animale");
        colVAnimale.setCellValueFactory(new PropertyValueFactory<>("animale"));

        final TableColumn<VisitaRow, String> colVSpecie = new TableColumn<>("Specie");
        colVSpecie.setCellValueFactory(new PropertyValueFactory<>("specie"));

        final TableColumn<VisitaRow, String> colVDiag = new TableColumn<>("Diagnosi");
        colVDiag.setCellValueFactory(new PropertyValueFactory<>("diagnosi"));

        final TableColumn<VisitaRow, String> colVPeso = new TableColumn<>("Peso");
        colVPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        final TableColumn<VisitaRow, String> colVNote = new TableColumn<>("Note Trattamento");
        colVNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        final TableColumn<VisitaRow, String> colVData = new TableColumn<>("Data inizio");
        colVData.setCellValueFactory(new PropertyValueFactory<>("dataVisita"));

        final TableColumn<VisitaRow, String> colVDataFine = new TableColumn<>("Data fine");
        colVDataFine.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

        final TableColumn<VisitaRow, String> colVVet = new TableColumn<>("Veterinario");
        colVVet.setCellValueFactory(new PropertyValueFactory<>("veterinario"));

        tableVisite.getColumns().addAll(colVId, colVAnimale, colVSpecie, colVDiag, colVPeso, colVNote, colVData, colVDataFine, colVVet);

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
        txtVisitaPeso = new javafx.scene.control.TextField(); txtVisitaPeso.setPromptText("Peso (es. 10.5)...");
        txtVisitaNote = new javafx.scene.control.TextField(); txtVisitaNote.setPromptText("Note trattamento...");
        dateVisita = new javafx.scene.control.DatePicker(); dateVisita.setPromptText("Data inizio...");
        dateVisitaFine = new javafx.scene.control.DatePicker(); dateVisitaFine.setPromptText("Data fine...");
        btnSalvaVisita = new Button("Salva");
        btnSalvaVisita.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblVisitaMsg = new Label(); lblVisitaMsg.setVisible(false);

        panelNuovaVisita.getChildren().addAll(new Label("Nuova Visita"), comboVisitaAnimale, comboVisitaVet, txtDiagnosi, txtVisitaPeso, txtVisitaNote, dateVisita, dateVisitaFine, btnSalvaVisita, lblVisitaMsg);

        visiteContent.getChildren().addAll(tableVisite, btnNuovaVisita, panelNuovaVisita);
        tabVisite.setContent(visiteContent);

        /* ═══ TAB 4 — Turni del giorno ═══════════════════ */
        tabTurni = new Tab("\uD83D\uDC77 Turni del giorno");
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
        
        btnEliminaTurno = new Button("Elimina Turno");
        btnEliminaTurno.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        btnEliminaTurno.setDisable(true);
        
        final javafx.scene.layout.HBox turniActionBox = new javafx.scene.layout.HBox(12, btnNuovoTurno, btnEliminaTurno);

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

        listTopTurni = new javafx.scene.control.ListView<>();
        listTopTurni.setPrefHeight(100);
        final VBox topTurniBox = new VBox(8, new Label("Dipendenti con più turni assegnati:"), listTopTurni);

        turniContent.getChildren().addAll(tableTurni, turniActionBox, panelNuovoTurno, topTurniBox);
        tabTurni.setContent(turniContent);


        /* ═══ TAB 5 — Personale ══════════════════════════ */
        tabPersonale = new Tab("\uD83D\uDC65 Personale");
        final VBox personaleContent = new VBox(16);
        personaleContent.setPadding(new Insets(20));

        tablePersonale = new TableView<>();
        tablePersonale.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<DipendenteRow, String> colDipId = new TableColumn<>("ID");
        colDipId.setCellValueFactory(new PropertyValueFactory<>("idDipendente"));
        colDipId.setMaxWidth(50);

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

        tablePersonale.getColumns().addAll(colDipId, colDipCf, colDipNome, colDipCognome, colDipDataN, colDipMans);

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
        tabSpese = new Tab("\uD83D\uDED2 Spese e Forniture");
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

        /* ═══ TAB 7 - Animali ════════════════════════════ */
        tabAnimali = new Tab("\uD83D\uDC3E Animali");
        final VBox animaliContent = new VBox(16);
        animaliContent.setPadding(new Insets(20));

        tableAnimali = new TableView<>();
        tableAnimali.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<AnimaleRow, String> colAnId = new TableColumn<>("ID");
        colAnId.setCellValueFactory(new PropertyValueFactory<>("idAnimale"));
        colAnId.setMaxWidth(50);

        final TableColumn<AnimaleRow, String> colAnNome = new TableColumn<>("Nome");
        colAnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        final TableColumn<AnimaleRow, String> colAnSesso = new TableColumn<>("Sesso");
        colAnSesso.setCellValueFactory(new PropertyValueFactory<>("sesso"));

        final TableColumn<AnimaleRow, String> colAnDataN = new TableColumn<>("Nascita");
        colAnDataN.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));

        final TableColumn<AnimaleRow, String> colAnDataA = new TableColumn<>("Arrivo");
        colAnDataA.setCellValueFactory(new PropertyValueFactory<>("dataArrivo"));

        final TableColumn<AnimaleRow, String> colAnDataU = new TableColumn<>("Uscita");
        colAnDataU.setCellValueFactory(new PropertyValueFactory<>("dataUscita"));

        final TableColumn<AnimaleRow, String> colAnSpecie = new TableColumn<>("Specie");
        colAnSpecie.setCellValueFactory(new PropertyValueFactory<>("specie"));

        final TableColumn<AnimaleRow, String> colAnVivo = new TableColumn<>("Stato");
        colAnVivo.setCellValueFactory(new PropertyValueFactory<>("vivo"));
        colAnVivo.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final String item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    final Label badge = new Label(item);
                    badge.setStyle(StyleHelper.badge("Vivo".equals(item) ? StyleHelper.GREEN : StyleHelper.RED));
                    setGraphic(badge);
                }
                setText(null);
            }
        });

        tableAnimali.getColumns().addAll(colAnId, colAnNome, colAnSesso, colAnDataN, colAnDataA, colAnDataU, colAnSpecie, colAnVivo);

        btnNuovoAnimale = new Button("Aggiungi / Resetta");
        btnNuovoAnimale.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovoAnimale = new VBox(12);
        panelNuovoAnimale.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoAnimale.setPadding(new Insets(16));
        panelNuovoAnimale.setVisible(false);
        panelNuovoAnimale.setManaged(false);

        txtAnimaleNome = new TextField(); txtAnimaleNome.setPromptText("Nome...");
        comboAnimaleSesso = new ComboBox<>(); comboAnimaleSesso.setPromptText("Sesso...");
        comboAnimaleSesso.getItems().addAll("M", "F", "I");
        dateAnimaleNascita = new DatePicker(); dateAnimaleNascita.setPromptText("Data Nascita...");
        dateAnimaleArrivo = new DatePicker(); dateAnimaleArrivo.setPromptText("Data Arrivo...");
        dateAnimaleUscita = new DatePicker(); dateAnimaleUscita.setPromptText("Data Uscita...");
        comboAnimaleVivo = new ComboBox<>(); comboAnimaleVivo.setPromptText("Stato...");
        comboAnimaleVivo.getItems().addAll("Vivo", "Morto");
        comboAnimaleSpecie = new ComboBox<>(); comboAnimaleSpecie.setPromptText("Seleziona Specie...");
        
        btnSalvaAnimale = new Button("Salva");
        btnSalvaAnimale.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblAnimaleMsg = new Label(); lblAnimaleMsg.setVisible(false);

        panelNuovoAnimale.getChildren().addAll(new Label("Nuovo/Modifica Animale"), txtAnimaleNome, comboAnimaleSesso, dateAnimaleNascita, dateAnimaleArrivo, dateAnimaleUscita, comboAnimaleVivo, comboAnimaleSpecie, btnSalvaAnimale, lblAnimaleMsg);

        animaliContent.getChildren().addAll(tableAnimali, btnNuovoAnimale, panelNuovoAnimale);
        tabAnimali.setContent(animaliContent);

        /* ═══ TAB 7.5 - Classificazione ════════════════════════════ */
        tabClassificazione = new Tab("\uD83D\uDCCB Classificazione");
        final VBox classificazioneContent = new VBox(20);
        classificazioneContent.setPadding(new Insets(20));

        // --- Sezione Stato Esistenza ---
        final Label lblStato = new Label("Stato Esistenza");
        lblStato.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_MAIN + ";");
        
        tableStatoEsistenza = new TableView<>();
        tableStatoEsistenza.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableStatoEsistenza.setPrefHeight(150);
        
        final TableColumn<StatoEsistenzaRow, String> colStatoId = new TableColumn<>("ID");
        colStatoId.setCellValueFactory(new PropertyValueFactory<>("idStato"));
        colStatoId.setMaxWidth(50);
        final TableColumn<StatoEsistenzaRow, String> colStatoNome = new TableColumn<>("Nome");
        colStatoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<StatoEsistenzaRow, String> colStatoDesc = new TableColumn<>("Descrizione");
        colStatoDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        tableStatoEsistenza.getColumns().addAll(colStatoId, colStatoNome, colStatoDesc);
        
        btnAggiungiStato = new Button("Aggiungi");
        btnAggiungiStato.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnRimuoviStato = new Button("Rimuovi");
        btnRimuoviStato.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        txtStatoNome = new TextField(); txtStatoNome.setPromptText("Nome...");
        txtStatoDesc = new TextField(); txtStatoDesc.setPromptText("Descrizione...");
        lblStatoMsg = new Label(); lblStatoMsg.setVisible(false); lblStatoMsg.setManaged(false);
        final HBox formStato = new HBox(8, txtStatoNome, txtStatoDesc, btnAggiungiStato, btnRimuoviStato, lblStatoMsg);
        formStato.setAlignment(Pos.CENTER_LEFT);
        final VBox boxStato = new VBox(8, lblStato, tableStatoEsistenza, formStato);
        
        // --- Sezione Habitat ---
        final Label lblHabitat = new Label("Habitat");
        lblHabitat.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_MAIN + ";");
        
        tableHabitat = new TableView<>();
        tableHabitat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableHabitat.setPrefHeight(150);
        
        final TableColumn<HabitatRow, String> colHabId = new TableColumn<>("ID");
        colHabId.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colHabId.setMaxWidth(50);
        final TableColumn<HabitatRow, String> colHabNome = new TableColumn<>("Nome");
        colHabNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<HabitatRow, String> colHabDesc = new TableColumn<>("Descrizione");
        colHabDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        tableHabitat.getColumns().addAll(colHabId, colHabNome, colHabDesc);
        
        btnAggiungiHabitat = new Button("Aggiungi");
        btnAggiungiHabitat.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnRimuoviHabitat = new Button("Rimuovi");
        btnRimuoviHabitat.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        txtHabitatNome = new TextField(); txtHabitatNome.setPromptText("Nome...");
        txtHabitatDesc = new TextField(); txtHabitatDesc.setPromptText("Descrizione...");
        lblHabitatMsg = new Label(); lblHabitatMsg.setVisible(false); lblHabitatMsg.setManaged(false);
        final HBox formHabitat = new HBox(8, txtHabitatNome, txtHabitatDesc, btnAggiungiHabitat, btnRimuoviHabitat, lblHabitatMsg);
        formHabitat.setAlignment(Pos.CENTER_LEFT);
        final VBox boxHabitat = new VBox(8, lblHabitat, tableHabitat, formHabitat);
        
        // --- Sezione Famiglia Specie ---
        final Label lblFamiglia = new Label("Famiglia Specie");
        lblFamiglia.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_MAIN + ";");
        
        tableFamigliaSpecie = new TableView<>();
        tableFamigliaSpecie.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableFamigliaSpecie.setPrefHeight(150);
        
        final TableColumn<FamigliaSpecieRow, String> colFamId = new TableColumn<>("ID");
        colFamId.setCellValueFactory(new PropertyValueFactory<>("idFamiglia"));
        colFamId.setMaxWidth(50);
        final TableColumn<FamigliaSpecieRow, String> colFamNome = new TableColumn<>("Nome");
        colFamNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<FamigliaSpecieRow, String> colFamDesc = new TableColumn<>("Descrizione");
        colFamDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        tableFamigliaSpecie.getColumns().addAll(colFamId, colFamNome, colFamDesc);
        
        btnAggiungiFamiglia = new Button("Aggiungi");
        btnAggiungiFamiglia.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnRimuoviFamiglia = new Button("Rimuovi");
        btnRimuoviFamiglia.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        txtFamigliaNome = new TextField(); txtFamigliaNome.setPromptText("Nome...");
        txtFamigliaDesc = new TextField(); txtFamigliaDesc.setPromptText("Descrizione...");
        lblFamigliaMsg = new Label(); lblFamigliaMsg.setVisible(false); lblFamigliaMsg.setManaged(false);
        final HBox formFamiglia = new HBox(8, txtFamigliaNome, txtFamigliaDesc, btnAggiungiFamiglia, btnRimuoviFamiglia, lblFamigliaMsg);
        formFamiglia.setAlignment(Pos.CENTER_LEFT);
        final VBox boxFamiglia = new VBox(8, lblFamiglia, tableFamigliaSpecie, formFamiglia);
        
        classificazioneContent.getChildren().addAll(boxStato, boxHabitat, boxFamiglia);
        
        final ScrollPane classificazioneScroll = new ScrollPane(classificazioneContent);
        classificazioneScroll.setFitToWidth(true);
        classificazioneScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        tabClassificazione.setContent(classificazioneScroll);

        /* ═══ TAB 8 - Aree ═══════════════════════════════ */
        tabAree = new Tab("\uD83D\uDDFA Aree");
        final VBox areeContent = new VBox(16);
        areeContent.setPadding(new Insets(20));

        tableAree = new TableView<>();
        tableAree.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<AreaRow, String> colArId = new TableColumn<>("ID");
        colArId.setCellValueFactory(new PropertyValueFactory<>("idArea"));
        colArId.setMaxWidth(50);

        final TableColumn<AreaRow, String> colArNome = new TableColumn<>("Nome");
        colArNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        final TableColumn<AreaRow, String> colArMet = new TableColumn<>("Metratura");
        colArMet.setCellValueFactory(new PropertyValueFactory<>("metratura"));

        final TableColumn<AreaRow, String> colArTipo = new TableColumn<>("Tipo");
        colArTipo.setCellValueFactory(new PropertyValueFactory<>("tipoArea"));

        tableAree.getColumns().addAll(colArId, colArNome, colArMet, colArTipo);

        btnNuovaArea = new Button("Aggiungi Area");
        btnNuovaArea.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovaArea = new VBox(12);
        panelNuovaArea.setStyle(StyleHelper.STYLE_CARD);
        panelNuovaArea.setPadding(new Insets(16));
        panelNuovaArea.setVisible(false);
        panelNuovaArea.setManaged(false);

        txtAreaNome = new TextField(); txtAreaNome.setPromptText("Nome Area...");
        spinnerAreaMetratura = new Spinner<>(10, 100000, 100); spinnerAreaMetratura.setEditable(true);
        comboAreaTipo = new ComboBox<>(); comboAreaTipo.setPromptText("Seleziona Tipo Area...");

        btnSalvaArea = new Button("Salva");
        btnSalvaArea.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblAreaMsg = new Label(); lblAreaMsg.setVisible(false);

        final HBox metraturaRow = new HBox(12, new Label("Metratura (mq):"), spinnerAreaMetratura);
        metraturaRow.setAlignment(Pos.CENTER_LEFT);
        for (javafx.scene.Node n : metraturaRow.getChildren()) {
            if (n instanceof Label) {
                n.setStyle(StyleHelper.STYLE_LABEL);
            }
        }

        panelNuovaArea.getChildren().addAll(new Label("Nuova Area"), txtAreaNome, metraturaRow, comboAreaTipo, btnSalvaArea, lblAreaMsg);

        // --- Sezione Tipo Area ---
        final Label lblTipoArea = new Label("Tipi di Area");
        lblTipoArea.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_MAIN + ";");
        
        tableTipoArea = new TableView<>();
        tableTipoArea.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableTipoArea.setPrefHeight(150);
        
        final TableColumn<TipoAreaRow, String> colTipoAreaId = new TableColumn<>("ID");
        colTipoAreaId.setCellValueFactory(new PropertyValueFactory<>("idTipoArea"));
        colTipoAreaId.setMaxWidth(50);
        final TableColumn<TipoAreaRow, String> colTipoAreaNome = new TableColumn<>("Nome");
        colTipoAreaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<TipoAreaRow, String> colTipoAreaDesc = new TableColumn<>("Descrizione");
        colTipoAreaDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        tableTipoArea.getColumns().addAll(colTipoAreaId, colTipoAreaNome, colTipoAreaDesc);
        
        btnAggiungiTipoArea = new Button("Aggiungi");
        btnAggiungiTipoArea.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnRimuoviTipoArea = new Button("Rimuovi");
        btnRimuoviTipoArea.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        txtTipoAreaNome = new TextField(); txtTipoAreaNome.setPromptText("Nome...");
        txtTipoAreaDesc = new TextField(); txtTipoAreaDesc.setPromptText("Descrizione...");
        lblTipoAreaMsg = new Label(); lblTipoAreaMsg.setVisible(false); lblTipoAreaMsg.setManaged(false);
        final HBox formTipoArea = new HBox(8, txtTipoAreaNome, txtTipoAreaDesc, btnAggiungiTipoArea, btnRimuoviTipoArea, lblTipoAreaMsg);
        formTipoArea.setAlignment(Pos.CENTER_LEFT);
        final VBox boxTipoArea = new VBox(8, lblTipoArea, tableTipoArea, formTipoArea);

        // --- Sezione Tipo Recinto ---
        final Label lblTipoRecinto = new Label("Tipi di Recinto");
        lblTipoRecinto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.TEXT_MAIN + ";");
        
        tableTipoRecinto = new TableView<>();
        tableTipoRecinto.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableTipoRecinto.setPrefHeight(150);
        
        final TableColumn<TipoRecintoRow, String> colTipoRecintoId = new TableColumn<>("ID");
        colTipoRecintoId.setCellValueFactory(new PropertyValueFactory<>("idTipoRecinto"));
        colTipoRecintoId.setMaxWidth(50);
        final TableColumn<TipoRecintoRow, String> colTipoRecintoNome = new TableColumn<>("Nome");
        colTipoRecintoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        final TableColumn<TipoRecintoRow, String> colTipoRecintoDesc = new TableColumn<>("Descrizione");
        colTipoRecintoDesc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        tableTipoRecinto.getColumns().addAll(colTipoRecintoId, colTipoRecintoNome, colTipoRecintoDesc);
        
        btnAggiungiTipoRecinto = new Button("Aggiungi");
        btnAggiungiTipoRecinto.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnRimuoviTipoRecinto = new Button("Rimuovi");
        btnRimuoviTipoRecinto.setStyle("-fx-background-color: " + StyleHelper.RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        txtTipoRecintoNome = new TextField(); txtTipoRecintoNome.setPromptText("Nome...");
        txtTipoRecintoDesc = new TextField(); txtTipoRecintoDesc.setPromptText("Descrizione...");
        lblTipoRecintoMsg = new Label(); lblTipoRecintoMsg.setVisible(false); lblTipoRecintoMsg.setManaged(false);
        final HBox formTipoRecinto = new HBox(8, txtTipoRecintoNome, txtTipoRecintoDesc, btnAggiungiTipoRecinto, btnRimuoviTipoRecinto, lblTipoRecintoMsg);
        formTipoRecinto.setAlignment(Pos.CENTER_LEFT);
        final VBox boxTipoRecinto = new VBox(8, lblTipoRecinto, tableTipoRecinto, formTipoRecinto);

        areeContent.getChildren().addAll(tableAree, btnNuovaArea, panelNuovaArea, boxTipoArea, boxTipoRecinto);
        
        final ScrollPane areeScroll = new ScrollPane(areeContent);
        areeScroll.setFitToWidth(true);
        areeScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        tabAree.setContent(areeScroll);

        /* ═══ TAB 9 - Recinti ═════════════════════════════ */
        tabRecinti = new Tab("\uD83D\uDFEB Recinti");
        final VBox recintiContent = new VBox(16);
        recintiContent.setPadding(new Insets(20));

        tableRecinti = new TableView<>();
        tableRecinti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<RecintoRow, String> colRecId = new TableColumn<>("ID");
        colRecId.setCellValueFactory(new PropertyValueFactory<>("idRecinto"));
        colRecId.setMaxWidth(50);

        final TableColumn<RecintoRow, String> colRecNome = new TableColumn<>("Nome");
        colRecNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        final TableColumn<RecintoRow, String> colRecArea = new TableColumn<>("Area");
        colRecArea.setCellValueFactory(new PropertyValueFactory<>("area"));

        final TableColumn<RecintoRow, String> colRecTipo = new TableColumn<>("Tipo");
        colRecTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        final TableColumn<RecintoRow, String> colRecCapienza = new TableColumn<>("Capienza Massima");
        colRecCapienza.setCellValueFactory(new PropertyValueFactory<>("capienza"));

        tableRecinti.getColumns().addAll(colRecId, colRecNome, colRecArea, colRecTipo, colRecCapienza);

        btnNuovoRecinto = new Button("Aggiungi Recinto");
        btnNuovoRecinto.setStyle(StyleHelper.STYLE_BTN_PRIMARY);

        panelNuovoRecinto = new VBox(12);
        panelNuovoRecinto.setStyle(StyleHelper.STYLE_CARD);
        panelNuovoRecinto.setPadding(new Insets(16));
        panelNuovoRecinto.setVisible(false);
        panelNuovoRecinto.setManaged(false);

        txtRecintoNome = new TextField(); txtRecintoNome.setPromptText("Nome del recinto...");
        comboRecintoArea = new ComboBox<>(); comboRecintoArea.setPromptText("Seleziona Area...");
        comboRecintoTipo = new ComboBox<>(); comboRecintoTipo.setPromptText("Seleziona Tipo Recinto...");
        spinnerRecintoCapienza = new Spinner<>(1, 1000, 10); spinnerRecintoCapienza.setEditable(true);

        btnSalvaRecinto = new Button("Salva");
        btnSalvaRecinto.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        lblRecintoMsg = new Label(); lblRecintoMsg.setVisible(false);

        final HBox nomeRecintoRow = new HBox(12, new Label("Nome Recinto:"), txtRecintoNome);
        nomeRecintoRow.setAlignment(Pos.CENTER_LEFT);
        for (javafx.scene.Node n : nomeRecintoRow.getChildren()) {
            if (n instanceof Label) n.setStyle(StyleHelper.STYLE_LABEL);
        }
        final HBox capienzaRow = new HBox(12, new Label("Capienza Massima:"), spinnerRecintoCapienza);
        capienzaRow.setAlignment(Pos.CENTER_LEFT);
        for (javafx.scene.Node n : capienzaRow.getChildren()) {
            if (n instanceof Label) n.setStyle(StyleHelper.STYLE_LABEL);
        }

        panelNuovoRecinto.getChildren().addAll(new Label("Nuovo Recinto"), nomeRecintoRow, comboRecintoArea, comboRecintoTipo, capienzaRow, btnSalvaRecinto, lblRecintoMsg);

        lblTopRecintoAnimali = new Label("Recinto con più animali: -");
        lblTopRecintoAnimali.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + StyleHelper.PRIMARY + ";");

        recintiContent.getChildren().addAll(tableRecinti, btnNuovoRecinto, panelNuovoRecinto, lblTopRecintoAnimali);
        tabRecinti.setContent(recintiContent);

        /* ── Assembla TabPane ────────────────────────────── */
        tabPane.getTabs().addAll(tabSaldo, tabStatistiche, tabTipiBiglietti, tabSpese, tabOrdini, tabVisite, tabTurni, tabPersonale, tabAnimali, tabClassificazione, tabAree, tabRecinti);


        root.getChildren().addAll(title, tabPane);
    }

    /* ── Metodi pubblici ─────────────────────────────── */

    public VBox getRoot() { return root; }
    
    public void filterTabsByRuolo(String ruolo) {
        tabPane.getTabs().clear();
        if (ruolo == null) return;
        if ("amministratore".equalsIgnoreCase(ruolo) || "admin".equalsIgnoreCase(ruolo)) {
            tabPane.getTabs().addAll(tabSaldo, tabStatistiche, tabTipiBiglietti, tabSpese, tabOrdini, tabVisite, tabTurni, tabPersonale, tabAnimali, tabClassificazione, tabAree, tabRecinti);
        } else if ("guardiano".equalsIgnoreCase(ruolo) || "operatore".equalsIgnoreCase(ruolo)) {
            tabPane.getTabs().add(tabOrdini);
        } else if ("cassiere".equalsIgnoreCase(ruolo)) {
            tabPane.getTabs().addAll(tabStatistiche, tabTipiBiglietti);
        } else if ("veterinario".equalsIgnoreCase(ruolo)) {
            tabPane.getTabs().add(tabVisite);
        }
    }

    /* Tab 1 — Saldo */
    public void setEntrate(final String value) { lblEntrate.setText(value); }
    public void setUscite(final String value)  { lblUscite.setText(value); }
    public void setSaldo(final String value)   { lblSaldo.setText(value); }

    public void setTransazioni(final List<TransazioneRow> rows) {
        tableTransazioni.setItems(FXCollections.observableArrayList(rows));
    }
    public DatePicker getDateSaldoInizio() { return dateSaldoInizio; }
    public DatePicker getDateSaldoFine() { return dateSaldoFine; }
    public Button getBtnCalcolaRicavo() { return btnCalcolaRicavo; }
    public Label getLblRicavoTotale() { return lblRicavoTotale; }

    /* Tab 1b - Statistiche */
    public ComboBox<String> getComboStatPeriodo() { return comboStatPeriodo; }
    public javafx.scene.chart.BarChart<String, Number> getChartStatBiglietti() { return chartStatBiglietti; }
    public Label getLblStatTopBiglietto() { return lblStatTopBiglietto; }
    public Label getLblStatTotBiglietti() { return lblStatTotBiglietti; }

    /* Tab Tipi Biglietto */
    public void setTipiBiglietti(final List<TipiBigliettiRow> rows) {
        tableTipiBiglietti.setItems(FXCollections.observableArrayList(rows));
    }
    public TableView<TipiBigliettiRow> getTableTipiBiglietti() { return tableTipiBiglietti; }
    public Button getBtnNuovoTipoBiglietto() { return btnNuovoTipoBiglietto; }
    public Button getBtnSalvaTipoBiglietto() { return btnSalvaTipoBiglietto; }
    public Button getBtnEliminaTipoBiglietto() { return btnEliminaTipoBiglietto; }
    public TextField getTxtTipoBigliettoNome() { return txtTipoBigliettoNome; }
    public TextField getTxtTipoBigliettoPrezzo() { return txtTipoBigliettoPrezzo; }
    public void setPanelNuovoTipoBigliettoVisible(boolean visible) {
        panelNuovoTipoBiglietto.setVisible(visible);
        panelNuovoTipoBiglietto.setManaged(visible);
    }
    public void showTipiBigliettiMsg(String msg, boolean success) {
        lblTipiBigliettiMsg.setText(msg);
        lblTipiBigliettiMsg.setStyle(StyleHelper.badge(success ? StyleHelper.GREEN : StyleHelper.RED));
        lblTipiBigliettiMsg.setVisible(true);
        lblTipiBigliettiMsg.setManaged(true);
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
    public TableView<VisitaRow> getTableVisite() { return tableVisite; }
    public Button getBtnNuovaVisita() { return btnNuovaVisita; }
    public VBox getPanelNuovaVisita() { return panelNuovaVisita; }
    public ComboBox<String> getComboVisitaAnimale() { return comboVisitaAnimale; }
    public ComboBox<String> getComboVisitaVet() { return comboVisitaVet; }
    public javafx.scene.control.TextField getTxtDiagnosi() { return txtDiagnosi; }
    public javafx.scene.control.TextField getTxtVisitaPeso() { return txtVisitaPeso; }
    public javafx.scene.control.TextField getTxtVisitaNote() { return txtVisitaNote; }
    public javafx.scene.control.DatePicker getDateVisita() { return dateVisita; }
    public javafx.scene.control.DatePicker getDateVisitaFine() { return dateVisitaFine; }
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
    public TableView<TurnoRow> getTableTurni() { return tableTurni; }
    public Button getBtnNuovoTurno() { return btnNuovoTurno; }
    public Button getBtnEliminaTurno() { return btnEliminaTurno; }
    public javafx.scene.control.ListView<String> getListTopTurni() { return listTopTurni; }
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
    public TableView<DipendenteRow> getTablePersonale() { return tablePersonale; }

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


    /* Tab 7 - Animali */
    public void setAnimali(final List<AnimaleRow> rows) { tableAnimali.setItems(FXCollections.observableArrayList(rows)); }
    public Button getBtnNuovoAnimale() { return btnNuovoAnimale; }
    public TableView<AnimaleRow> getTableAnimali() { return tableAnimali; }
    public VBox getPanelNuovoAnimale() { return panelNuovoAnimale; }
    public TextField getTxtAnimaleNome() { return txtAnimaleNome; }
    public ComboBox<String> getComboAnimaleSesso() { return comboAnimaleSesso; }
    public DatePicker getDateAnimaleNascita() { return dateAnimaleNascita; }
    public DatePicker getDateAnimaleArrivo() { return dateAnimaleArrivo; }
    public DatePicker getDateAnimaleUscita() { return dateAnimaleUscita; }
    public ComboBox<String> getComboAnimaleVivo() { return comboAnimaleVivo; }
    public ComboBox<String> getComboAnimaleSpecie() { return comboAnimaleSpecie; }
    public Button getBtnSalvaAnimale() { return btnSalvaAnimale; }
    public void showAnimaleMsg(final String msg, final boolean success) {
        lblAnimaleMsg.setText(msg);
        lblAnimaleMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblAnimaleMsg.setVisible(true);
    }
    public void setPanelNuovoAnimaleVisible(final boolean visible) {
        panelNuovoAnimale.setVisible(visible); panelNuovoAnimale.setManaged(visible);
    }

    /* Tab 8 - Aree */
    public void setAree(final List<AreaRow> rows) { tableAree.setItems(FXCollections.observableArrayList(rows)); }
    public Button getBtnNuovaArea() { return btnNuovaArea; }
    public VBox getPanelNuovaArea() { return panelNuovaArea; }
    public TextField getTxtAreaNome() { return txtAreaNome; }
    public Spinner<Integer> getSpinnerAreaMetratura() { return spinnerAreaMetratura; }
    public ComboBox<String> getComboAreaTipo() { return comboAreaTipo; }
    public Button getBtnSalvaArea() { return btnSalvaArea; }
    public void showAreaMsg(final String msg, final boolean success) {
        lblAreaMsg.setText(msg);
        lblAreaMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblAreaMsg.setVisible(true);
    }
    public void setPanelNuovaAreaVisible(final boolean visible) {
        panelNuovaArea.setVisible(visible); panelNuovaArea.setManaged(visible);
    }

    /* Tab 9 - Recinti */
    public void setRecinti(final List<RecintoRow> rows) { tableRecinti.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<RecintoRow> getTableRecinti() { return tableRecinti; }
    public Button getBtnNuovoRecinto() { return btnNuovoRecinto; }
    public VBox getPanelNuovoRecinto() { return panelNuovoRecinto; }
    public TextField getTxtRecintoNome() { return txtRecintoNome; }
    public ComboBox<String> getComboRecintoArea() { return comboRecintoArea; }
    public ComboBox<String> getComboRecintoTipo() { return comboRecintoTipo; }
    public Spinner<Integer> getSpinnerRecintoCapienza() { return spinnerRecintoCapienza; }
    public Button getBtnSalvaRecinto() { return btnSalvaRecinto; }
    public Label getLblTopRecintoAnimali() { return lblTopRecintoAnimali; }
    public void showRecintoMsg(final String msg, final boolean success) {
        lblRecintoMsg.setText(msg);
        lblRecintoMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblRecintoMsg.setVisible(true);
    }
    public void setPanelNuovoRecintoVisible(final boolean visible) {
        panelNuovoRecinto.setVisible(visible); panelNuovoRecinto.setManaged(visible);
    }

    /* Tab Classificazione */
    public void setStatoEsistenza(final List<StatoEsistenzaRow> rows) { tableStatoEsistenza.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<StatoEsistenzaRow> getTableStatoEsistenza() { return tableStatoEsistenza; }
    public Button getBtnAggiungiStato() { return btnAggiungiStato; }
    public Button getBtnRimuoviStato() { return btnRimuoviStato; }
    public TextField getTxtStatoNome() { return txtStatoNome; }
    public TextField getTxtStatoDesc() { return txtStatoDesc; }
    public void showStatoMsg(final String msg, final boolean success) {
        lblStatoMsg.setText(msg);
        lblStatoMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblStatoMsg.setVisible(true); lblStatoMsg.setManaged(true);
    }

    public void setHabitat(final List<HabitatRow> rows) { tableHabitat.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<HabitatRow> getTableHabitat() { return tableHabitat; }
    public Button getBtnAggiungiHabitat() { return btnAggiungiHabitat; }
    public Button getBtnRimuoviHabitat() { return btnRimuoviHabitat; }
    public TextField getTxtHabitatNome() { return txtHabitatNome; }
    public TextField getTxtHabitatDesc() { return txtHabitatDesc; }
    public void showHabitatMsg(final String msg, final boolean success) {
        lblHabitatMsg.setText(msg);
        lblHabitatMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblHabitatMsg.setVisible(true); lblHabitatMsg.setManaged(true);
    }

    public void setFamigliaSpecie(final List<FamigliaSpecieRow> rows) { tableFamigliaSpecie.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<FamigliaSpecieRow> getTableFamigliaSpecie() { return tableFamigliaSpecie; }
    public Button getBtnAggiungiFamiglia() { return btnAggiungiFamiglia; }
    public Button getBtnRimuoviFamiglia() { return btnRimuoviFamiglia; }
    public TextField getTxtFamigliaNome() { return txtFamigliaNome; }
    public TextField getTxtFamigliaDesc() { return txtFamigliaDesc; }
    public void showFamigliaMsg(final String msg, final boolean success) {
        lblFamigliaMsg.setText(msg);
        lblFamigliaMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblFamigliaMsg.setVisible(true); lblFamigliaMsg.setManaged(true);
    }

    /* Tipi Area e Recinto */
    public void setTipoArea(final List<TipoAreaRow> rows) { tableTipoArea.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<TipoAreaRow> getTableTipoArea() { return tableTipoArea; }
    public Button getBtnAggiungiTipoArea() { return btnAggiungiTipoArea; }
    public Button getBtnRimuoviTipoArea() { return btnRimuoviTipoArea; }
    public TextField getTxtTipoAreaNome() { return txtTipoAreaNome; }
    public TextField getTxtTipoAreaDesc() { return txtTipoAreaDesc; }
    public void showTipoAreaMsg(final String msg, final boolean success) {
        lblTipoAreaMsg.setText(msg);
        lblTipoAreaMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblTipoAreaMsg.setVisible(true); lblTipoAreaMsg.setManaged(true);
    }

    public void setTipoRecinto(final List<TipoRecintoRow> rows) { tableTipoRecinto.setItems(FXCollections.observableArrayList(rows)); }
    public TableView<TipoRecintoRow> getTableTipoRecinto() { return tableTipoRecinto; }
    public Button getBtnAggiungiTipoRecinto() { return btnAggiungiTipoRecinto; }
    public Button getBtnRimuoviTipoRecinto() { return btnRimuoviTipoRecinto; }
    public TextField getTxtTipoRecintoNome() { return txtTipoRecintoNome; }
    public TextField getTxtTipoRecintoDesc() { return txtTipoRecintoDesc; }
    public void showTipoRecintoMsg(final String msg, final boolean success) {
        lblTipoRecintoMsg.setText(msg);
        lblTipoRecintoMsg.setStyle(success ? StyleHelper.STYLE_SUCCESS_LABEL : StyleHelper.STYLE_ERROR_LABEL);
        lblTipoRecintoMsg.setVisible(true); lblTipoRecintoMsg.setManaged(true);
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