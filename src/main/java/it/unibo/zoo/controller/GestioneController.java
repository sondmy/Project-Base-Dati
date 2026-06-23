package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.view.GestioneView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import it.unibo.zoo.model.jdbc.entityDao.*;
import java.time.LocalDate;
import it.unibo.zoo.controller.DataEventBus.DataType;

/**
 * Controller per il pannello di gestione (4 tab).
 */
public class GestioneController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final GestioneView view;
    private boolean panelOrdineVisible;
    private boolean panelSpesaVisible;
    private boolean panelVisitaVisible;
    private boolean panelTurnoVisible;
    private boolean panelDipendenteVisible;
    private boolean panelAnimaleVisible;
    private boolean panelAreaVisible;
    private boolean panelRecintoVisible;
    private boolean panelTipiBigliettiVisible;
    private Integer editingAnimaleId = null;
    private Integer editingVisitaId = null;
    private Integer editingDipendenteId = null;
    private Integer editingTurnoId = null;
    private Integer editingRecintoId = null;
    private Integer editingTipoBigliettoId = null;

    public GestioneController(final GestioneView view) {
        this.view = view;
        this.panelOrdineVisible = false;
        this.panelSpesaVisible = false;
        this.panelVisitaVisible = false;
        this.panelTurnoVisible = false;
        this.panelDipendenteVisible = false;
        this.panelAnimaleVisible = false;
        this.panelAreaVisible = false;
        this.panelRecintoVisible = false;
        this.panelTipiBigliettiVisible = false;
        init();
    }

    private void init() {
        refresh();
        registerSubscriptions();

        // Toggle pannello nuovo tipo biglietto
        view.getBtnNuovoTipoBiglietto().setOnAction(e -> {
            panelTipiBigliettiVisible = !panelTipiBigliettiVisible;
            if (!panelTipiBigliettiVisible) editingTipoBigliettoId = null;
            view.getTxtTipoBigliettoNome().clear();
            view.getTxtTipoBigliettoDesc().clear();
            view.getTxtTipoBigliettoPrezzo().clear();
            view.getChkTipoBigliettoAttivo().setSelected(true);
            view.setPanelNuovoTipoBigliettoVisible(panelTipiBigliettiVisible);
        });
        view.getBtnSalvaTipoBiglietto().setOnAction(e -> {
            TipiBigliettiController.handleSalvaTipoBiglietto(view, editingTipoBigliettoId);
            editingTipoBigliettoId = null;
        });
        view.getBtnEliminaTipoBiglietto().setOnAction(e -> TipiBigliettiController.handleEliminaTipoBiglietto(view));

        view.getTableTipiBiglietti().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnEliminaTipoBiglietto().setDisable(newSel == null);
            if (newSel != null && newSel.getIdBiglietto() != null) {
                editingTipoBigliettoId = Integer.parseInt(newSel.getIdBiglietto());
                view.getTxtTipoBigliettoNome().setText(newSel.getNome());
                view.getTxtTipoBigliettoDesc().setText(newSel.getDescrizione() == null ? "" : newSel.getDescrizione());
                view.getTxtTipoBigliettoPrezzo().setText(newSel.getPrezzo().replace("€", "").replace(",", "."));
                view.getChkTipoBigliettoAttivo().setSelected("Sì".equals(newSel.getAttivo()));
                panelTipiBigliettiVisible = true;
                view.setPanelNuovoTipoBigliettoVisible(true);
            }
        });

        // Toggle pannello nuovo ordine
        view.getBtnNuovoOrdine().setOnAction(e -> {
            panelOrdineVisible = !panelOrdineVisible;
            if (panelOrdineVisible) {
                view.getComboFornitore().setValue(null);
                view.getComboTipoCibo().setValue(null);
                try { view.getSpinnerQta().getValueFactory().setValue(1); } catch(Exception ignored) {}
            }
            view.setPanelNuovoOrdineVisible(panelOrdineVisible);
        });

        // Salva ordine
        view.getBtnSalvaOrdine().setOnAction(e -> OrdiniController.handleSalvaOrdine(view));
        
        view.getComboFornitore().valueProperty().addListener((obs, oldVal, newVal) -> {
            OrdiniController.updateCibiDisponibili(view, newVal);
        });

        // Toggle pannello nuova visita
        view.getBtnNuovaVisita().setOnAction(e -> {
            panelVisitaVisible = !panelVisitaVisible;
            if (!panelVisitaVisible) {
                editingVisitaId = null;
            } else {
                editingVisitaId = null;
                view.getComboVisitaAnimale().setValue(null);
                view.getComboVisitaVet().setValue(null);
                view.getTxtDiagnosi().clear();
                view.getDateVisita().setValue(LocalDate.now());
                view.getDateVisitaFine().setValue(null);
                view.getTxtVisitaNote().clear();
                view.getTxtVisitaPeso().clear();
            }
            view.setPanelNuovaVisitaVisible(panelVisitaVisible);
        });
        view.getTableVisite().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null && newSel.getIdVisita() != null) {
                editingVisitaId = Integer.parseInt(newSel.getIdVisita());
                view.getTxtDiagnosi().setText(newSel.getDiagnosi());
                view.getTxtVisitaPeso().setText(newSel.getPeso().equals("—") ? "" : newSel.getPeso());
                view.getTxtVisitaNote().setText(newSel.getNote().equals("—") ? "" : newSel.getNote());
                view.getDateVisita().setValue(LocalDate.parse(newSel.getDataVisita(), DATE_FMT));
                if (!newSel.getDataFine().equals("—")) {
                    view.getDateVisitaFine().setValue(LocalDate.parse(newSel.getDataFine(), DATE_FMT));
                } else {
                    view.getDateVisitaFine().setValue(null);
                }
                
                view.getComboVisitaAnimale().getItems().stream()
                    .filter(i -> i.startsWith(newSel.getIdAnimale() + " -"))
                    .findFirst()
                    .ifPresent(view.getComboVisitaAnimale()::setValue);
                    
                view.getComboVisitaVet().getItems().stream()
                    .filter(i -> i.startsWith(newSel.getIdVet() + " -"))
                    .findFirst()
                    .ifPresent(view.getComboVisitaVet()::setValue);
                    
                panelVisitaVisible = true;
                view.setPanelNuovaVisitaVisible(true);
            }
        });
        view.getBtnSalvaVisita().setOnAction(e -> {
            VisiteController.handleSalvaVisita(view, editingVisitaId);
            editingVisitaId = null;
        });

        // Toggle pannello nuovo turno
        view.getBtnNuovoTurno().setOnAction(e -> {
            panelTurnoVisible = !panelTurnoVisible;
            if (!panelTurnoVisible) {
                editingTurnoId = null;
            } else {
                editingTurnoId = null;
                view.getComboTurnoDip().setValue(null);
                view.getComboTurnoArea().setValue(null);
                view.getDateTurnoGiorno().setValue(LocalDate.now());
                view.getComboTurnoOraInizio().setValue(null);
                view.getComboTurnoOraFine().setValue(null);
            }
            view.setPanelNuovoTurnoVisible(panelTurnoVisible);
        });
        view.getBtnSalvaTurno().setOnAction(e -> TurnoController.handleSalvaTurno(view));

        view.getTableTurni().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnEliminaTurno().setDisable(newSel == null);
            if (newSel != null && newSel.getIdTurno() != null) {
                editingTurnoId = Integer.parseInt(newSel.getIdTurno());
                
                view.getComboTurnoDip().getItems().stream()
                    .filter(i -> i.startsWith(newSel.getIdDipendente() + " -"))
                    .findFirst()
                    .ifPresent(view.getComboTurnoDip()::setValue);
                
                view.getComboTurnoArea().getItems().stream()
                    .filter(i -> i.startsWith(newSel.getIdArea() + " -"))
                    .findFirst()
                    .ifPresent(view.getComboTurnoArea()::setValue);
                    
                if (newSel.getGiorno() != null && !newSel.getGiorno().equals("—")) {
                    view.getDateTurnoGiorno().setValue(LocalDate.parse(newSel.getGiorno(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } else {
                    view.getDateTurnoGiorno().setValue(LocalDate.now());
                }
                view.getComboTurnoOraInizio().setValue(newSel.getOraInizio());
                view.getComboTurnoOraFine().setValue(newSel.getOraFine());
                
                panelTurnoVisible = true;
                view.setPanelNuovoTurnoVisible(true);
            }
        });
        view.getBtnSalvaTurno().setOnAction(e -> TurnoController.handleSalvaTurno(view));
        view.getBtnEliminaTurno().setOnAction(e -> TurnoController.handleEliminaTurno(view));
        view.getBtnFiltraTurni().setOnAction(e -> TurnoController.populateTurni(view));

        // Toggle pannello nuovo dipendente
        view.getBtnNuovoDipendente().setOnAction(e -> {
            panelDipendenteVisible = !panelDipendenteVisible;
            if (!panelDipendenteVisible) {
                editingDipendenteId = null;
            } else {
                editingDipendenteId = null;
                view.getTxtDipCf().clear();
                view.getTxtDipNome().clear();
                view.getTxtDipCognome().clear();
                view.getDateDipNascita().setValue(null);
                view.getComboDipMansione().setValue(null);
            }
            view.setPanelNuovoDipendenteVisible(panelDipendenteVisible);
        });
        view.getTablePersonale().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null && newSel.getIdDipendente() != null) {
                editingDipendenteId = Integer.parseInt(newSel.getIdDipendente());
                view.getTxtDipCf().setText(newSel.getCodiceFiscale());
                view.getTxtDipNome().setText(newSel.getNome());
                view.getTxtDipCognome().setText(newSel.getCognome());
                view.getDateDipNascita().setValue(LocalDate.parse(newSel.getDataNascita(), DATE_FMT));
                
                view.getComboDipMansione().getItems().stream()
                    .filter(i -> i.equals(newSel.getMansione()))
                    .findFirst()
                    .ifPresent(view.getComboDipMansione()::setValue);
                    
                panelDipendenteVisible = true;
                view.setPanelNuovoDipendenteVisible(true);
            }
        });
        view.getBtnSalvaDipendente().setOnAction(e -> {
            DipendenteController.handleSalvaDipendente(view, editingDipendenteId);
            editingDipendenteId = null;
        });

        // Tipo Mansione
        view.getBtnAggiungiTipoMansione().setOnAction(e -> DipendenteController.handleAggiungiTipoMansione(view));
        view.getBtnRimuoviTipoMansione().setOnAction(e -> DipendenteController.handleRimuoviTipoMansione(view));
        view.getTableTipoMansione().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviTipoMansione().setDisable(newSel == null);
        });

        // Calcola ricavo totale
        view.getBtnCalcolaRicavo().setOnAction(e -> {
            LocalDate inizio = view.getDateSaldoInizio().getValue();
            LocalDate fine = view.getDateSaldoFine().getValue();
            if (inizio != null && fine != null) {
                if (inizio.isAfter(fine)) {
                    view.getLblRicavoTotale().setText("Errore: Date non valide.");
                    view.getLblRicavoTotale().setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
                    return;
                }
                List<Transazione> tList = new TransazioneDao().findByDateRange(inizio, fine);
                double ricavo = 0;
                for (Transazione t : tList) {
                    if ("E".equalsIgnoreCase(t.getTipo())) {
                        ricavo += t.getImporto();
                    } else {
                        ricavo -= t.getImporto();
                    }
                }
                view.getLblRicavoTotale().setText(String.format("Ricavo nel periodo: €%.2f", ricavo));
                view.getLblRicavoTotale().setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1976D2;");
            } else {
                view.getLblRicavoTotale().setText("Seleziona entrambe le date.");
                view.getLblRicavoTotale().setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
            }
        });

        // Toggle pannello nuova spesa
        view.getBtnNuovaSpesa().setOnAction(e -> {
            panelSpesaVisible = !panelSpesaVisible;
            if (panelSpesaVisible) {
                view.getTxtSpesaImporto().clear();
                view.getTxtSpesaDescrizione().clear();
            }
            view.setPanelNuovaSpesaVisible(panelSpesaVisible);
        });

        // Filtra spese per intervallo date
        view.getBtnFiltraSpese().setOnAction(e -> {
            SpesaController.handleFiltraSpese(view);
        });

        view.getComboStatPeriodo().valueProperty().addListener((obs, oldVal, newVal) -> {
            SaldoController.populateStatistiche(view);
        });

        // Salva nuova spesa
        view.getBtnSalvaSpesa().setOnAction(e -> {
            panelSpesaVisible = false;
            SpesaController.handleSalvaSpesa(view);
        });

        // Toggle pannello nuovo animale e selezione
        view.getBtnNuovoAnimale().setOnAction(e -> {
            panelAnimaleVisible = !panelAnimaleVisible;
            if (!panelAnimaleVisible) {
                editingAnimaleId = null;
            } else {
                editingAnimaleId = null;
                view.getTxtAnimaleNome().clear();
                view.getComboAnimaleSesso().setValue(null);
                view.getDateAnimaleNascita().setValue(null);
                view.getDateAnimaleArrivo().setValue(null);
                view.getDateAnimaleUscita().setValue(null);
                view.getComboAnimaleVivo().setValue(null);
                view.getComboAnimaleSpecie().setValue(null);
                view.getComboAnimaleRecinto().setValue(null);
            }
            view.setPanelNuovoAnimaleVisible(panelAnimaleVisible);
        });
        view.getBtnSalvaAnimale().setOnAction(e -> {
            AnimaliController.handleSalvaAnimale(view, editingAnimaleId);
            editingAnimaleId = null;
        });

        view.getTableAnimali().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editingAnimaleId = Integer.parseInt(newSelection.getIdAnimale());
                view.getTxtAnimaleNome().setText(newSelection.getNome());
                view.getComboAnimaleSesso().setValue(newSelection.getSesso());
                view.getDateAnimaleNascita().setValue(newSelection.getDataNascita() != null && !newSelection.getDataNascita().equals("-") ? LocalDate.parse(newSelection.getDataNascita(), DATE_FMT) : null);
                view.getDateAnimaleArrivo().setValue(newSelection.getDataArrivo() != null && !newSelection.getDataArrivo().equals("-") ? LocalDate.parse(newSelection.getDataArrivo(), DATE_FMT) : null);
                view.getDateAnimaleUscita().setValue(newSelection.getDataUscita() != null && !newSelection.getDataUscita().equals("-") ? LocalDate.parse(newSelection.getDataUscita(), DATE_FMT) : null);
                view.getComboAnimaleVivo().setValue(newSelection.getVivo());
                view.getComboAnimaleSpecie().getItems().stream().filter(s -> s.endsWith(newSelection.getSpecie())).findFirst().ifPresent(view.getComboAnimaleSpecie()::setValue);
                
                if (newSelection.getRecinto() != null && !newSelection.getRecinto().equals("—")) {
                    view.getComboAnimaleRecinto().getItems().stream()
                        .filter(r -> r.startsWith(newSelection.getRecinto().split(" - ")[0] + " -"))
                        .findFirst()
                        .ifPresent(view.getComboAnimaleRecinto()::setValue);
                } else {
                    view.getComboAnimaleRecinto().setValue(null);
                }
                
                panelAnimaleVisible = true;
                view.setPanelNuovoAnimaleVisible(true);
            }
        });

        // Toggle pannello nuova area
        view.getBtnNuovaArea().setOnAction(e -> {
            panelAreaVisible = !panelAreaVisible;
            view.setPanelNuovaAreaVisible(panelAreaVisible);
        });
        view.getBtnSalvaArea().setOnAction(e -> AreeController.handleSalvaArea(view));
        view.getBtnEliminaArea().setOnAction(e -> AreeController.handleEliminaArea(view));
        view.getTableAree().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnEliminaArea().setDisable(newSel == null);
        });

        // Toggle pannello nuovo recinto
        view.getBtnNuovoRecinto().setOnAction(e -> {
            panelRecintoVisible = !panelRecintoVisible;
            if (!panelRecintoVisible) {
                editingRecintoId = null;
            } else {
                editingRecintoId = null;
                view.getTxtRecintoNome().clear();
                view.getComboRecintoArea().setValue(null);
                view.getComboRecintoTipo().setValue(null);
                try { view.getSpinnerRecintoCapienza().getValueFactory().setValue(1); } catch(Exception ignored) {}
            }
            view.setPanelNuovoRecintoVisible(panelRecintoVisible);
        });
        
        view.getTableRecinti().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnEliminaRecinto().setDisable(newSel == null);
            if (newSel != null && newSel.getIdRecinto() != null) {
                editingRecintoId = Integer.parseInt(newSel.getIdRecinto());
                view.getTxtRecintoNome().setText(newSel.getNome());
                
                view.getComboRecintoArea().getItems().stream()
                    .filter(i -> i.endsWith("- " + newSel.getArea()))
                    .findFirst()
                    .ifPresent(view.getComboRecintoArea()::setValue);
                
                view.getComboRecintoTipo().getItems().stream()
                    .filter(i -> i.endsWith("- " + newSel.getTipo()))
                    .findFirst()
                    .ifPresent(view.getComboRecintoTipo()::setValue);
                
                try {
                    view.getSpinnerRecintoCapienza().getValueFactory().setValue(Integer.parseInt(newSel.getCapienza()));
                } catch(Exception ignored) {}
                
                panelRecintoVisible = true;
                view.setPanelNuovoRecintoVisible(true);
            }
        });
        
        view.getBtnSalvaRecinto().setOnAction(e -> RecintoController.handleSalvaRecinto(view, editingRecintoId));
        view.getBtnEliminaRecinto().setOnAction(e -> RecintoController.handleEliminaRecinto(view));
        
        // Tab Classificazione
        view.getBtnAggiungiStato().setOnAction(e -> ClassificazioneController.handleAggiungiStato(view));
        view.getBtnRimuoviStato().setOnAction(e -> ClassificazioneController.handleRimuoviStato(view));
        view.getBtnAggiungiHabitat().setOnAction(e -> ClassificazioneController.handleAggiungiHabitat(view));
        view.getBtnRimuoviHabitat().setOnAction(e -> ClassificazioneController.handleRimuoviHabitat(view));
        view.getBtnAggiungiFamiglia().setOnAction(e -> ClassificazioneController.handleAggiungiFamiglia(view));
        view.getBtnRimuoviFamiglia().setOnAction(e -> ClassificazioneController.handleRimuoviFamiglia(view));
        view.getBtnAggiungiSpecie().setOnAction(e -> ClassificazioneController.handleAggiungiSpecie(view));
        view.getBtnRimuoviSpecie().setOnAction(e -> ClassificazioneController.handleRimuoviSpecie(view));
        view.getBtnAggiungiDieta().setOnAction(e -> ClassificazioneController.handleAggiungiDieta(view));
        view.getBtnRimuoviDieta().setOnAction(e -> ClassificazioneController.handleRimuoviDieta(view));

        view.getTableStatoEsistenza().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviStato().setDisable(newSel == null);
        });
        view.getTableHabitat().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviHabitat().setDisable(newSel == null);
        });
        view.getTableFamigliaSpecie().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviFamiglia().setDisable(newSel == null);
        });
        view.getTableSpecie().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviSpecie().setDisable(newSel == null);
        });
        view.getTableDieta().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviDieta().setDisable(newSel == null);
        });

        // Tipi Area e Recinto
        view.getBtnAggiungiTipoArea().setOnAction(e -> AreeController.handleAggiungiTipoArea(view));
        view.getBtnRimuoviTipoArea().setOnAction(e -> AreeController.handleRimuoviTipoArea(view));
        view.getTableTipoArea().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviTipoArea().setDisable(newSel == null);
        });

        view.getBtnAggiungiTipoRecinto().setOnAction(e -> AreeController.handleAggiungiTipoRecinto(view));
        view.getBtnRimuoviTipoRecinto().setOnAction(e -> AreeController.handleRimuoviTipoRecinto(view));
        view.getTableTipoRecinto().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnRimuoviTipoRecinto().setDisable(newSel == null);
        });
    }

    private void refresh(){
        SaldoController.populateSaldo(view);
        SaldoController.populateStatistiche(view);
        OrdiniController.populateOrdini(view);
        VisiteController.populateVisite(view);
        TurnoController.populateTurni(view);
        DipendenteController.populatePersonale(view);
        SpesaController.populateSpese(view);
        AnimaliController.populateAnimali(view);
        AreeController.populateAree(view);
        AreeController.populateTipoArea(view);
        RecintoController.populateRecinti(view);
        AreeController.populateTipoRecinto(view);
        TipiBigliettiController.populateTipiBiglietti(view);
        ClassificazioneController.populateClassificazione(view);
    }

    /**
     * Registra tutte le sottoscrizioni all'EventBus.
     * Ogni tipo di dato viene mappato al metodo populate*() appropriato.
     * Quando un controller pubblica un evento, il bus notifica tutti i subscriber
     * (incluse le dipendenze transitive), garantendo la sincronizzazione globale dei dati.
     */
    private void registerSubscriptions() {
        DataEventBus bus = DataEventBus.getInstance();

        // Pulisci eventuali sottoscrizioni precedenti (es. se GestioneView viene ricreata)
        bus.unsubscribeAll();

        // Tab Classificazione
        bus.subscribe(DataType.STATO_ESISTENZA, () -> ClassificazioneController.populateStatoEsistenza(view));
        bus.subscribe(DataType.HABITAT, () -> ClassificazioneController.populateHabitat(view));
        bus.subscribe(DataType.FAMIGLIA_SPECIE, () -> ClassificazioneController.populateFamigliaSpecie(view));
        bus.subscribe(DataType.SPECIE, () -> ClassificazioneController.populateSpecie(view));
        bus.subscribe(DataType.DIETA, () -> ClassificazioneController.populateDieta(view));

        // Tab Animali
        bus.subscribe(DataType.ANIMALE, () -> AnimaliController.populateAnimali(view));

        // Tab Aree
        bus.subscribe(DataType.AREA, () -> AreeController.populateAree(view));
        bus.subscribe(DataType.TIPO_AREA, () -> AreeController.populateTipoArea(view));

        // Tab Recinti
        bus.subscribe(DataType.RECINTO, () -> RecintoController.populateRecinti(view));
        bus.subscribe(DataType.TIPO_RECINTO, () -> AreeController.populateTipoRecinto(view));

        // Tab Personale e Mansioni
        bus.subscribe(DataType.DIPENDENTE, () -> DipendenteController.populatePersonale(view));
        bus.subscribe(DataType.MANSIONE, () -> DipendenteController.populatePersonale(view));

        // Tab Visite
        bus.subscribe(DataType.VISITA, () -> VisiteController.populateVisite(view));

        // Tab Turni
        bus.subscribe(DataType.TURNO, () -> TurnoController.populateTurni(view));

        // Tab Ordini
        bus.subscribe(DataType.ORDINE, () -> OrdiniController.populateOrdini(view));

        // Tab Spese
        bus.subscribe(DataType.SPESA, () -> SpesaController.populateSpese(view));

        // Tab Saldo / Transazioni
        bus.subscribe(DataType.SALDO, () -> SaldoController.populateSaldo(view));
        bus.subscribe(DataType.TRANSAZIONE, () -> {
            SaldoController.populateSaldo(view);
            SaldoController.populateStatistiche(view);
        });

        // Tab Tipi Biglietti
        bus.subscribe(DataType.TIPO_BIGLIETTO, () -> TipiBigliettiController.populateTipiBiglietti(view));
    }

}
