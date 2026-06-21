package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.view.GestioneView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import it.unibo.zoo.model.jdbc.entityDao.*;
import java.time.LocalDate;

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

        // Toggle pannello nuovo tipo biglietto
        view.getBtnNuovoTipoBiglietto().setOnAction(e -> {
            panelTipiBigliettiVisible = !panelTipiBigliettiVisible;
            view.setPanelNuovoTipoBigliettoVisible(panelTipiBigliettiVisible);
        });
        view.getBtnSalvaTipoBiglietto().setOnAction(e -> TipiBigliettiController.handleSalvaTipoBiglietto(view));
        view.getBtnEliminaTipoBiglietto().setOnAction(e -> TipiBigliettiController.handleEliminaTipoBiglietto(view));

        view.getTableTipiBiglietti().getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            view.getBtnEliminaTipoBiglietto().setDisable(newSel == null);
        });

        // Toggle pannello nuovo ordine
        view.getBtnNuovoOrdine().setOnAction(e -> {
            panelOrdineVisible = !panelOrdineVisible;
            view.setPanelNuovoOrdineVisible(panelOrdineVisible);
        });

        // Salva ordine (mock)
        view.getBtnSalvaOrdine().setOnAction(e -> OrdiniController.handleSalvaOrdine(view));

        // Toggle pannello nuova visita
        view.getBtnNuovaVisita().setOnAction(e -> {
            panelVisitaVisible = !panelVisitaVisible;
            if (!panelVisitaVisible) editingVisitaId = null;
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
        view.getBtnSalvaVisita().setOnAction(e -> VisiteController.handleSalvaVisita(view));

        // Toggle pannello nuovo turno
        view.getBtnNuovoTurno().setOnAction(e -> {
            panelTurnoVisible = !panelTurnoVisible;
            if (!panelTurnoVisible) editingTurnoId = null;
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
                    
                view.getComboTurnoOraInizio().setValue(newSel.getOraInizio());
                view.getComboTurnoOraFine().setValue(newSel.getOraFine());
                
                panelTurnoVisible = true;
                view.setPanelNuovoTurnoVisible(true);
            }
        });
        view.getBtnSalvaTurno().setOnAction(e -> TurnoController.handleSalvaTurno(view));
        view.getBtnEliminaTurno().setOnAction(e -> TurnoController.handleEliminaTurno(view));

        // Toggle pannello nuovo dipendente
        view.getBtnNuovoDipendente().setOnAction(e -> {
            panelDipendenteVisible = !panelDipendenteVisible;
            if (!panelDipendenteVisible) editingDipendenteId = null;
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
        view.getBtnSalvaDipendente().setOnAction(e -> DipendenteController.handleSalvaDipendente(view));

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
            view.setPanelNuovaSpesaVisible(panelSpesaVisible);
        });

        // Filtra spese per intervallo date
        view.getBtnFiltraSpese().setOnAction(e -> {
            SpesaController.populateSpese(view);
        });

        view.getComboStatPeriodo().valueProperty().addListener((obs, oldVal, newVal) -> {
            SaldoController.populateStatistiche(view);
        });

        // Salva nuova spesa
        view.getBtnSalvaSpesa().setOnAction(e -> {
            panelSpesaVisible = false;
            SpesaController.handleSalvaSpesa(view);
            refresh();
        });

        // Toggle pannello nuovo animale e selezione
        view.getBtnNuovoAnimale().setOnAction(e -> {
            editingAnimaleId = null;
            view.getTxtAnimaleNome().clear();
            view.getComboAnimaleSesso().setValue(null);
            view.getDateAnimaleNascita().setValue(null);
            view.getDateAnimaleArrivo().setValue(null);
            view.getDateAnimaleUscita().setValue(null);
            view.getComboAnimaleVivo().setValue(null);
            view.getComboAnimaleSpecie().setValue(null);
            
            panelAnimaleVisible = true;
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

        // Toggle pannello nuovo recinto
        view.getBtnNuovoRecinto().setOnAction(e -> {
            panelRecintoVisible = !panelRecintoVisible;
            view.setPanelNuovoRecintoVisible(panelRecintoVisible);
        });
        view.getBtnSalvaRecinto().setOnAction(e -> RecintoController.handleSalvaRecinto(view));
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
        RecintoController.populateRecinti(view);
        TipiBigliettiController.populateTipiBiglietti(view);
    }

}
