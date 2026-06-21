package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.model.entity.Fornitore;
import it.unibo.zoo.model.entity.OrdineGiornalieroCibo;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.TipoCibo;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.entity.Turno;
import it.unibo.zoo.model.entity.VisitaMedica;
import it.unibo.zoo.model.entity.Mansione;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.model.jdbc.entityDao.CategoriaTransazioneDao;
import it.unibo.zoo.model.jdbc.entityDao.OrdineGiornalieroCiboDao;
import it.unibo.zoo.model.jdbc.entityDao.FornitoreDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoCiboDao;
import it.unibo.zoo.model.jdbc.entityDao.VisitaMedicaDao;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.model.jdbc.entityDao.SpecieDao;
import it.unibo.zoo.model.jdbc.entityDao.DipendenteDao;
import it.unibo.zoo.model.jdbc.entityDao.TurnoDao;
import it.unibo.zoo.model.jdbc.entityDao.MansioneDao;
import it.unibo.zoo.model.jdbc.entityDao.AreaDao;
import it.unibo.zoo.view.GestioneView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.unibo.zoo.model.entity.*;
import it.unibo.zoo.model.jdbc.entityDao.*;
import java.time.LocalDate;
import java.util.stream.Collectors;


/**
 * Controller per il pannello di gestione (4 tab).
 */
public class GestioneController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private final GestioneView view;
    private boolean panelOrdineVisible;
    private boolean panelSpesaVisible;
    private boolean panelVisitaVisible;
    private boolean panelTurnoVisible;
    private boolean panelDipendenteVisible;
    private boolean panelAnimaleVisible;
    private boolean panelAreaVisible;
    private boolean panelRecintoVisible;
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
        init();
    }

    private void init() {
        populateSaldo();
        populateStatistiche();
        populateOrdini();
        populateVisite();
        populateTurni();
        populatePersonale();
        populateSpese();
        populateAnimali();
        populateAree();
        populateRecinti();

        // Toggle pannello nuovo ordine
        view.getBtnNuovoOrdine().setOnAction(e -> {
            panelOrdineVisible = !panelOrdineVisible;
            view.setPanelNuovoOrdineVisible(panelOrdineVisible);
        });

        // Salva ordine (mock)
        view.getBtnSalvaOrdine().setOnAction(e -> handleSalvaOrdine());

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
        view.getBtnSalvaVisita().setOnAction(e -> handleSalvaVisita());

        // Toggle pannello nuovo turno
        view.getBtnNuovoTurno().setOnAction(e -> {
            panelTurnoVisible = !panelTurnoVisible;
            if (!panelTurnoVisible) editingTurnoId = null;
            view.setPanelNuovoTurnoVisible(panelTurnoVisible);
        });
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
        view.getBtnSalvaTurno().setOnAction(e -> handleSalvaTurno());
        view.getBtnEliminaTurno().setOnAction(e -> handleEliminaTurno());

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
        view.getBtnSalvaDipendente().setOnAction(e -> handleSalvaDipendente());

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
        view.getBtnFiltraSpese().setOnAction(e -> populateSpese());

        view.getComboStatPeriodo().valueProperty().addListener((obs, oldVal, newVal) -> {
            populateStatistiche();
        });

        // Salva nuova spesa
        view.getBtnSalvaSpesa().setOnAction(e -> handleSalvaSpesa());

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
        view.getBtnSalvaAnimale().setOnAction(e -> handleSalvaAnimale());

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
        view.getBtnSalvaArea().setOnAction(e -> handleSalvaArea());

        // Toggle pannello nuovo recinto
        view.getBtnNuovoRecinto().setOnAction(e -> {
            panelRecintoVisible = !panelRecintoVisible;
            view.setPanelNuovoRecintoVisible(panelRecintoVisible);
        });
        view.getBtnSalvaRecinto().setOnAction(e -> handleSalvaRecinto());
    }

    /* ═══ TAB 1 — Saldo ══════════════════════════════════ */

    private void populateSaldo() {
        final List<Transazione> transazioni = new TransazioneDao().findAll();
        final Map<Integer, it.unibo.zoo.model.entity.CategoriaTransazione> catMap = new it.unibo.zoo.model.jdbc.entityDao.CategoriaTransazioneDao().findAll().stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.CategoriaTransazione::getIdCategoria, c -> c));

        double entrate = 0;
        double uscite = 0;

        for (final Transazione t : transazioni) {
            if ("E".equalsIgnoreCase(t.getTipo())) {
                entrate += t.getImporto();
            } else {
                uscite += t.getImporto();
            }
        }

        view.setEntrate(String.format("€%.2f", entrate));
        view.setUscite(String.format("€%.2f", uscite));
        final double saldo = entrate - uscite;
        view.setSaldo(String.format("€%.2f", saldo));

        // Ultime 5 transazioni (le ultime nella lista)
        final int start = Math.max(0, transazioni.size() - 5);
        final List<GestioneView.TransazioneRow> rows = new ArrayList<>();
        for (int i = transazioni.size() - 1; i >= start; i--) {
            final Transazione t = transazioni.get(i);
            final it.unibo.zoo.model.entity.CategoriaTransazione cat = catMap.get(t.getIdCategoria());
            rows.add(new GestioneView.TransazioneRow(
                    t.getData().format(DATE_FMT),
                    t.getTipo(),
                    String.format("€%.2f", t.getImporto()),
                    cat != null ? cat.getNome() : "—",
                    t.getDescrizione() != null ? t.getDescrizione() : ""
            ));
        }
        view.setTransazioni(rows);
    }

    private void populateStatistiche() {
        String periodo = view.getComboStatPeriodo().getValue();
        
        List<it.unibo.zoo.model.entity.Scontrino> scontrini = new it.unibo.zoo.model.jdbc.entityDao.ScontrinoDao().findAll();
        List<it.unibo.zoo.model.entity.DettaglioScontrino> dettagli = new it.unibo.zoo.model.jdbc.entityDao.DettaglioScontrinoDao().findAll();
        List<it.unibo.zoo.model.entity.TipoBiglietto> tipi = new it.unibo.zoo.model.jdbc.entityDao.TipoBigliettoDao().findAll();
        
        Map<Integer, LocalDate> scontrinoDataMap = scontrini.stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.Scontrino::getIdScontrino, it.unibo.zoo.model.entity.Scontrino::getDataAcquisto));
        Map<Integer, String> tipoNomeMap = tipi.stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.TipoBiglietto::getIdBiglietto, it.unibo.zoo.model.entity.TipoBiglietto::getNome));

        Map<String, Integer> venditePerPeriodo = new java.util.TreeMap<>();
        Map<Integer, Integer> venditePerTipo = new java.util.HashMap<>();
        
        java.time.format.DateTimeFormatter monthFmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM");
        
        int totalTickets = 0;
        
        for (it.unibo.zoo.model.entity.DettaglioScontrino d : dettagli) {
            LocalDate data = scontrinoDataMap.get(d.getIdScontrino());
            if (data == null) continue;
            
            String periodKey;
            if ("Mese".equals(periodo)) {
                periodKey = data.format(monthFmt);
            } else if ("Settimana".equals(periodo)) {
                int week = data.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                int year = data.get(java.time.temporal.IsoFields.WEEK_BASED_YEAR);
                periodKey = year + "-W" + String.format("%02d", week);
            } else {
                periodKey = data.format(DATE_FMT);
            }
            
            venditePerPeriodo.put(periodKey, venditePerPeriodo.getOrDefault(periodKey, 0) + d.getQuantita());
            venditePerTipo.put(d.getIdBiglietto(), venditePerTipo.getOrDefault(d.getIdBiglietto(), 0) + d.getQuantita());
            totalTickets += d.getQuantita();
        }
        
        javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : venditePerPeriodo.entrySet()) {
            series.getData().add(new javafx.scene.chart.XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        view.getChartStatBiglietti().getData().clear();
        view.getChartStatBiglietti().getData().add(series);
        
        int topId = -1;
        int max = -1;
        for (Map.Entry<Integer, Integer> entry : venditePerTipo.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topId = entry.getKey();
            }
        }
        
        if (topId != -1) {
            view.getLblStatTopBiglietto().setText(tipoNomeMap.getOrDefault(topId, "-"));
        } else {
            view.getLblStatTopBiglietto().setText("-");
        }
        
        view.getLblStatTotBiglietti().setText(String.valueOf(totalTickets));
    }

    /* ═══ TAB 2 — Ordini giornalieri ═════════════════════ */

    private void populateOrdini() {
        final List<OrdineGiornalieroCibo> ordini = new OrdineGiornalieroCiboDao().findAll();
        final Map<Integer, Fornitore> fornMap = new FornitoreDao().findAll().stream()
                .collect(Collectors.toMap(Fornitore::getIdFornitore, f -> f));
        final Map<Integer, TipoCibo> ciboMap = new TipoCiboDao().findAll().stream()
                .collect(Collectors.toMap(TipoCibo::getIdTipoCibo, c -> c));

        final List<GestioneView.OrdineRow> rows = new ArrayList<>();
        for (final OrdineGiornalieroCibo o : ordini) {
            final Fornitore f = fornMap.get(o.getIdFornitore());
            final TipoCibo c = ciboMap.get(o.getIdTipoCibo());
            rows.add(new GestioneView.OrdineRow(
                    o.getDataOrdine().format(DATE_FMT),
                    f != null ? f.getNomeAzienda() : "—",
                    c != null ? c.getNome() : "—",
                    String.format("%.1f", o.getQuantitaKg())
            ));
        }
        view.setOrdini(rows);

        view.getComboFornitore().getItems().clear();
        for (final Fornitore f : new FornitoreDao().findAll()) {
            view.getComboFornitore().getItems().add(f.getNomeAzienda());
        }
        view.getComboTipoCibo().getItems().clear();
        for (final TipoCibo c : new TipoCiboDao().findAll()) {
            view.getComboTipoCibo().getItems().add(c.getNome());
        }
    }

    private void handleSalvaOrdine() {
        final String fornitore = view.getComboFornitore().getValue();
        final String tipoCibo = view.getComboTipoCibo().getValue();
        final int qta = view.getSpinnerQta().getValue();

        if (fornitore == null || tipoCibo == null) {
            view.showOrdineMsg("Seleziona fornitore e tipo cibo.", false);
            return;
        }
        
        try {
            int idFornitore = 0;
            for (Fornitore f : new FornitoreDao().findAll()) {
                if (f.getNomeAzienda().equals(fornitore)) {
                    idFornitore = f.getIdFornitore();
                    break;
                }
            }
            int idTipoCibo = 0;
            for (TipoCibo c : new TipoCiboDao().findAll()) {
                if (c.getNome().equals(tipoCibo)) {
                    idTipoCibo = c.getIdTipoCibo();
                    break;
                }
            }
            
            double costoTotale = qta * 2.50; // Prezzo fittizio
            Transazione t = new Transazione(0, "U", costoTotale, LocalDate.now(), "Acquisto cibo: " + tipoCibo + " da " + fornitore, 2, 1, idFornitore, null);
            t = new TransazioneDao().insert(t);
            
            OrdineGiornalieroCibo ordine = new OrdineGiornalieroCibo(0, LocalDate.now(), (double)qta, idFornitore, idTipoCibo, t.getIdTransazione());
            new OrdineGiornalieroCiboDao().insert(ordine);
            
            populateSaldo();
            populateOrdini();
            view.showOrdineMsg("Ordine salvato! Costo: €" + String.format("%.2f", costoTotale), true);
        } catch(Exception e) {
            view.showOrdineMsg("Errore db: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    /* ═══ TAB 3 — Animali in cura ════════════════════════ */

    private void populateVisite() {
        final List<VisitaMedica> visite = new VisitaMedicaDao().findAll();
        final Map<Integer, Animale> animMap = new AnimaleDao().findAll().stream()
                .collect(Collectors.toMap(Animale::getIdAnimale, a -> a));
        final Map<Integer, Specie> specieMap = new SpecieDao().findAll().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));
        final Map<Integer, Dipendente> dipMap = new DipendenteDao().findAll().stream()
                .collect(Collectors.toMap(Dipendente::getIdDipendente, d -> d));

        final List<GestioneView.VisitaRow> rows = new ArrayList<>();
        for (final VisitaMedica v : visite) {
            final Animale a = animMap.get(v.getIdAnimale());
            final String nomeAnimale = a != null ? a.getNome() : "—";
            final String nomeSpecie;
            if (a != null) {
                final Specie sp = specieMap.get(a.getIdSpecie());
                nomeSpecie = sp != null ? sp.getNomeComune() : "—";
            } else {
                nomeSpecie = "—";
            }
            final Dipendente vet = dipMap.get(v.getIdDipendente());
            final String nomeVet = vet != null ? vet.getNome() + " " + vet.getCognome() : "—";
            final String dataFineStr = v.getDataFine() == null ? "—" : v.getDataFine().format(DATE_FMT);
            final String pesoStr = v.getPeso() != null ? String.format("%.2f", v.getPeso()) : "—";
            final String noteStr = v.getNoteTrattamento() != null && !v.getNoteTrattamento().isEmpty() ? v.getNoteTrattamento() : "—";

            rows.add(new GestioneView.VisitaRow(
                    String.valueOf(v.getIdVisita()),
                    String.valueOf(v.getIdAnimale()),
                    String.valueOf(v.getIdDipendente()),
                    nomeAnimale, nomeSpecie, v.getDiagnosi(),
                    pesoStr, noteStr,
                    v.getDataVisita().format(DATE_FMT),
                    dataFineStr,
                    nomeVet
            ));
        }
        view.setVisite(rows);
        
        view.getComboVisitaAnimale().getItems().clear();
        for(Animale a : new AnimaleDao().findAll()) {
            view.getComboVisitaAnimale().getItems().add(a.getIdAnimale() + " - " + a.getNome());
        }
        view.getComboVisitaVet().getItems().clear();
        Map<Integer, Mansione> mansioniMap = new MansioneDao().findAll().stream()
                .collect(Collectors.toMap(Mansione::getIdMansione, m -> m));
        for(Dipendente d : new DipendenteDao().findAll()) {
            Mansione m = mansioniMap.get(d.getIdMansione());
            if (m != null && "Veterinario".equalsIgnoreCase(m.getNome())) {
                view.getComboVisitaVet().getItems().add(d.getIdDipendente() + " - " + d.getNome() + " " + d.getCognome());
            }
        }
    }

    /* ═══ TAB 4 — Turni del giorno ═══════════════════════ */

    private void populateTurni() {
        final List<Turno> turni = new TurnoDao().findAll();
        final Map<Integer, Dipendente> dipMap = new DipendenteDao().findAll().stream()
                .collect(Collectors.toMap(Dipendente::getIdDipendente, d -> d));
        final Map<Integer, Mansione> mansMap = new MansioneDao().findAll().stream()
                .collect(Collectors.toMap(Mansione::getIdMansione, m -> m));
        final Map<Integer, Area> areaMap = new AreaDao().findAll().stream()
                .collect(Collectors.toMap(Area::getIdArea, a -> a));

        final List<GestioneView.TurnoRow> rows = new ArrayList<>();
        Map<Integer, Integer> turniCount = new java.util.HashMap<>();
        
        for (final Turno t : turni) {
            final Dipendente d = dipMap.get(t.getIdDipendente());
            if (d != null) {
                turniCount.put(d.getIdDipendente(), turniCount.getOrDefault(d.getIdDipendente(), 0) + 1);
            }
            
            final String nomeDip = d != null ? d.getNome() + " " + d.getCognome() : "—";
            final String mansione;
            if (d != null) {
                final Mansione m = mansMap.get(d.getIdMansione());
                mansione = m != null ? m.getNome() : "—";
            } else {
                mansione = "—";
            }
            final Area area = areaMap.get(t.getIdArea());
            final String nomeArea = area != null ? area.getNome() : "—";

            rows.add(new GestioneView.TurnoRow(
                    String.valueOf(t.getIdTurno()),
                    String.valueOf(t.getIdDipendente()),
                    String.valueOf(t.getIdArea()),
                    nomeDip, mansione, nomeArea,
                    t.getOraInizio().format(TIME_FMT),
                    t.getOraFine().format(TIME_FMT)
            ));
        }
        view.setTurni(rows);
        
        List<Map.Entry<Integer, Integer>> topEntries = new ArrayList<>(turniCount.entrySet());
        topEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        List<String> topList = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : topEntries) {
            Dipendente d = dipMap.get(entry.getKey());
            if (d != null) {
                topList.add(d.getNome() + " " + d.getCognome() + " (" + entry.getValue() + " turni)");
                count++;
                if (count >= 5) break;
            }
        }
        view.getListTopTurni().setItems(javafx.collections.FXCollections.observableArrayList(topList));
        
        view.getComboTurnoDip().getItems().clear();
        for(Dipendente d : new DipendenteDao().findAll()) {
            view.getComboTurnoDip().getItems().add(d.getIdDipendente() + " - " + d.getNome() + " " + d.getCognome());
        }
        view.getComboTurnoArea().getItems().clear();
        for(Area a : new AreaDao().findAll()) {
            view.getComboTurnoArea().getItems().add(a.getIdArea() + " - " + a.getNome());
        }
    }

    /* ═══ TAB 5 — Personale ══════════════════════════ */
    private void populatePersonale() {
        final List<Dipendente> dipendenti = new DipendenteDao().findAll();
        System.out.println("Dipendenti: " + dipendenti.size());
        final Map<Integer, Mansione> mansMap = new MansioneDao().findAll().stream()
                .collect(Collectors.toMap(Mansione::getIdMansione, m -> m));

        final List<GestioneView.DipendenteRow> rows = new ArrayList<>();
        for (final Dipendente d : dipendenti) {
            final Mansione m = mansMap.get(d.getIdMansione());
            rows.add(new GestioneView.DipendenteRow(
                    String.valueOf(d.getIdDipendente()),
                    d.getCodiceFiscale(), d.getNome(), d.getCognome(),
                    d.getDataNascita() != null ? d.getDataNascita().format(DATE_FMT) : "—",
                    m != null ? m.getNome() : "—"
            ));
        }
        view.setPersonale(rows);

        view.getComboDipMansione().getItems().clear();
        for (final Mansione m : new MansioneDao().findAll()) {
            view.getComboDipMansione().getItems().add(m.getNome());
        }
    }

    private void handleSalvaDipendente() {
        try {
            String cf = view.getTxtDipCf().getText();
            String nome = view.getTxtDipNome().getText();
            String cognome = view.getTxtDipCognome().getText();
            LocalDate dataNascita = view.getDateDipNascita().getValue();
            String mansioneNome = view.getComboDipMansione().getValue();
            
            if(cf == null || cf.isEmpty() || nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() || dataNascita == null || mansioneNome == null) {
                view.showDipendenteMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idMans = 0;
            for (Mansione m : new MansioneDao().findAll()) {
                if (m.getNome().equals(mansioneNome)) {
                    idMans = m.getIdMansione();
                    break;
                }
            }
            
            if (editingDipendenteId != null) {
                DipendenteDao dao = new DipendenteDao();
                Dipendente d = dao.findAll().stream().filter(x -> x.getIdDipendente() == editingDipendenteId).findFirst().orElse(null);
                if (d != null) {
                    d.setCodiceFiscale(cf);
                    d.setNome(nome);
                    d.setCognome(cognome);
                    d.setDataNascita(dataNascita);
                    d.setIdMansione(idMans);
                    dao.update(d);
                    view.showDipendenteMsg("Dipendente modificato con successo!", true);
                }
            } else {
                Dipendente d = new Dipendente(0, cf, nome, cognome, dataNascita, LocalDate.now(), idMans);
                new DipendenteDao().insert(d);
                view.showDipendenteMsg("Dipendente assunto con successo!", true);
            }
            
            view.setPanelNuovoDipendenteVisible(false);
            populatePersonale();
            editingDipendenteId = null;
        } catch(Exception e) {
            view.showDipendenteMsg("Errore: " + e.getMessage(), false);
        }
    }

    private void handleSalvaVisita() {
        try {
            String animale = view.getComboVisitaAnimale().getValue();
            String vet = view.getComboVisitaVet().getValue();
            String diagnosi = view.getTxtDiagnosi().getText();
            String pesoStr = view.getTxtVisitaPeso().getText();
            String note = view.getTxtVisitaNote().getText();
            LocalDate data = view.getDateVisita().getValue();
            LocalDate dataFine = view.getDateVisitaFine().getValue();
            
            if(animale == null || vet == null || data == null) {
                view.showVisitaMsg("Animale, Veterinario e Data inizio sono obbligatori.", false);
                return;
            }
            
            int idAnimale = Integer.parseInt(animale.split(" - ")[0]);
            int idVet = Integer.parseInt(vet.split(" - ")[0]);
            
            Double peso = null;
            if (pesoStr != null && !pesoStr.trim().isEmpty()) {
                pesoStr = pesoStr.replace(",", ".");
                peso = Double.parseDouble(pesoStr);
            }
            
            if (editingVisitaId != null) {
                VisitaMedicaDao dao = new VisitaMedicaDao();
                VisitaMedica v = dao.findAll().stream().filter(x -> x.getIdVisita() == editingVisitaId).findFirst().orElse(null);
                if (v != null) {
                    v.setIdAnimale(idAnimale);
                    v.setIdDipendente(idVet);
                    v.setDiagnosi(diagnosi);
                    v.setPeso(peso);
                    v.setNoteTrattamento(note);
                    v.setDataVisita(data);
                    v.setDataFine(dataFine);
                    dao.update(v);
                    view.showVisitaMsg("Visita modificata con successo!", true);
                }
            } else {
                VisitaMedica v = new VisitaMedica(0, peso, diagnosi, note, data, dataFine, idAnimale, idVet);
                new VisitaMedicaDao().insert(v);
                view.showVisitaMsg("Visita registrata con successo!", true);
            }
            
            view.setPanelNuovaVisitaVisible(false);
            populateVisite();
            editingVisitaId = null;
        } catch(Exception e) {
            view.showVisitaMsg("Errore: " + e.getMessage(), false);
        }
    }
    
    private void handleEliminaTurno() {
        GestioneView.TurnoRow sel = view.getTableTurni().getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                new TurnoDao().delete(Integer.parseInt(sel.getIdTurno()));
                populateTurni();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleSalvaTurno() {
        try {
            String dip = view.getComboTurnoDip().getValue();
            String area = view.getComboTurnoArea().getValue();
            String inizio = view.getComboTurnoOraInizio().getValue();
            String fine = view.getComboTurnoOraFine().getValue();
            
            if(dip == null || area == null || inizio == null || fine == null) {
                view.showTurnoMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idDip = Integer.parseInt(dip.split(" - ")[0]);
            int idArea = Integer.parseInt(area.split(" - ")[0]);
            
            java.time.LocalDateTime dtInizio = LocalDate.now().atTime(java.time.LocalTime.parse(inizio));
            java.time.LocalDateTime dtFine = LocalDate.now().atTime(java.time.LocalTime.parse(fine));
            
            if (editingTurnoId != null) {
                TurnoDao dao = new TurnoDao();
                Turno t = dao.findAll().stream().filter(x -> x.getIdTurno() == editingTurnoId).findFirst().orElse(null);
                if (t != null) {
                    t.setIdDipendente(idDip);
                    t.setIdArea(idArea);
                    t.setOraInizio(dtInizio);
                    t.setOraFine(dtFine);
                    dao.update(t);
                    view.showTurnoMsg("Turno modificato con successo!", true);
                }
            } else {
                Turno t = new Turno(0, dtInizio, dtFine, idDip, idArea);
                new TurnoDao().insert(t);
                view.showTurnoMsg("Turno salvato con successo!", true);
            }
            
            view.setPanelNuovoTurnoVisible(false);
            populateTurni();
            editingTurnoId = null;
        } catch(Exception e) {
            view.showTurnoMsg("Errore: " + e.getMessage(), false);
        }
    }

    /* ═══ TAB 6 — Spese ══════════════════════════════════ */

    private void populateSpese() {
        final List<Transazione> transazioni = new TransazioneDao().findAll();
        // Mostra solo le uscite (spese)
        final List<Transazione> spese = transazioni.stream()
                .filter(t -> "U".equals(t.getTipo()))
                .collect(Collectors.toList());

        final List<GestioneView.SpesaRow> rows = new ArrayList<>();
        for (final Transazione t : spese) {
            rows.add(new GestioneView.SpesaRow(
                    t.getData().format(DATE_FMT),
                    t.getDescrizione() != null ? t.getDescrizione() : "—",
                    String.format("\u20AC%.2f", t.getImporto())
            ));
        }
        view.setSpese(rows);
    }

    private void handleFiltraSpese() {
        final LocalDate from = view.getDateSpesaInizio().getValue();
        final LocalDate to = view.getDateSpesaFine().getValue();

        if (from == null || to == null) {
            // Se nessun filtro, ricarica tutte le spese
            populateSpese();
            return;
        }

        if (from.isAfter(to)) {
            view.showSpesaMsg("La data di inizio deve essere precedente alla data di fine.", false);
            return;
        }

        final List<Transazione> transazioni = new TransazioneDao().findByDateRange(from, to);
        final List<Transazione> spese = transazioni.stream()
                .filter(t -> "U".equals(t.getTipo()))
                .collect(Collectors.toList());

        final List<GestioneView.SpesaRow> rows = new ArrayList<>();
        for (final Transazione t : spese) {
            rows.add(new GestioneView.SpesaRow(
                    t.getData().format(DATE_FMT),
                    t.getDescrizione() != null ? t.getDescrizione() : "—",
                    String.format("\u20AC%.2f", t.getImporto())
            ));
        }
        view.setSpese(rows);
    }

    private void handleSalvaSpesa() {
        try {
            final String importoStr = view.getTxtSpesaImporto().getText();
            final String descrizione = view.getTxtSpesaDescrizione().getText();

            if (importoStr == null || importoStr.trim().isEmpty()) {
                view.showSpesaMsg("L'importo è obbligatorio.", false);
                return;
            }

            final double importo;
            try {
                importo = Double.parseDouble(importoStr.replace(",", ".").trim());
            } catch (NumberFormatException ex) {
                view.showSpesaMsg("Importo non valido. Inserisci un valore numerico.", false);
                return;
            }

            if (importo <= 0) {
                view.showSpesaMsg("L'importo deve essere maggiore di zero.", false);
                return;
            }

            if (descrizione == null || descrizione.trim().isEmpty()) {
                view.showSpesaMsg("La descrizione è obbligatoria.", false);
                return;
            }

            // Data automatica = oggi
            // Recupera dinamicamente la prima categoria di tipo Uscita
            final List<CategoriaTransazione> catUscita = new CategoriaTransazioneDao().findByTipo("U");
            final int idCategoria;
            if (!catUscita.isEmpty()) {
                idCategoria = catUscita.get(0).getIdCategoria();
            } else {
                // Fallback: prova tutte le categorie
                final List<CategoriaTransazione> tutteCat = new CategoriaTransazioneDao().findAll();
                if (tutteCat.isEmpty()) {
                    view.showSpesaMsg("Nessuna categoria transazione disponibile nel database.", false);
                    return;
                }
                idCategoria = tutteCat.get(0).getIdCategoria();
            }

            // Recupera dinamicamente il primo utente disponibile
            final List<Utente> utenti = new UtenteDao().findAll();
            if (utenti.isEmpty()) {
                view.showSpesaMsg("Nessun utente disponibile nel database.", false);
                return;
            }
            final int idUtente = utenti.get(0).getIdUtente();

            final Transazione t = new Transazione("U", importo, LocalDate.now(), descrizione.trim(), idCategoria, idUtente, null, null);
            new TransazioneDao().insert(t);


            view.showSpesaMsg("Spesa registrata con successo!", true);
            view.getTxtSpesaImporto().clear();
            view.getTxtSpesaDescrizione().clear();
            view.setPanelNuovaSpesaVisible(false);
            panelSpesaVisible = false;

            // Aggiorna sia la tab Spese che il Saldo
            populateSpese();
            populateSaldo();
        } catch (Exception e) {
            view.showSpesaMsg("Errore: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    /* ═══ TAB 7 - Animali ══════════════════════════════ */
    private void populateAnimali() {
        final List<Animale> animali = new AnimaleDao().findAll();
        final java.util.Map<Integer, Specie> specieMap = new SpecieDao().findAll().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));

        final List<GestioneView.AnimaleRow> rows = new ArrayList<>();
        for (final Animale a : animali) {
            final Specie s = specieMap.get(a.getIdSpecie());
            rows.add(new GestioneView.AnimaleRow(
                    String.valueOf(a.getIdAnimale()),
                    a.getNome(),
                    String.valueOf(a.getSesso()),
                    a.getDataNascita() != null ? a.getDataNascita().format(DATE_FMT) : "-",
                    a.getDataArrivo() != null ? a.getDataArrivo().format(DATE_FMT) : "-",
                    a.getDataUscita() != null ? a.getDataUscita().format(DATE_FMT) : "-",
                    s != null ? s.getNomeComune() : "-",
                    a.isVivo() ? "Vivo" : "Morto"
            ));
        }
        view.setAnimali(rows);

        view.getComboAnimaleSpecie().getItems().clear();
        for (final Specie s : new SpecieDao().findAll()) {
            view.getComboAnimaleSpecie().getItems().add(s.getIdSpecie() + " - " + s.getNomeComune());
        }
    }

    private void handleSalvaAnimale() {
        try {
            String nome = view.getTxtAnimaleNome().getText();
            String sessoStr = view.getComboAnimaleSesso().getValue();
            LocalDate dataNascita = view.getDateAnimaleNascita().getValue();
            LocalDate dataArrivo = view.getDateAnimaleArrivo().getValue();
            LocalDate dataUscita = view.getDateAnimaleUscita().getValue();
            String vivoStr = view.getComboAnimaleVivo().getValue();
            String specieStr = view.getComboAnimaleSpecie().getValue();
            
            if(nome == null || nome.isEmpty() || sessoStr == null || specieStr == null) {
                view.showAnimaleMsg("Nome, Sesso e Specie sono obbligatori.", false);
                return;
            }
            
            int idSpecie = Integer.parseInt(specieStr.split(" - ")[0]);
            char sesso = sessoStr.charAt(0);
            boolean vivo = "Vivo".equals(vivoStr);
            
            if (dataArrivo == null) {
                dataArrivo = LocalDate.now();
            }
            
            AnimaleDao dao = new AnimaleDao();
            if (editingAnimaleId == null) {
                Animale a = new Animale(0, nome, sesso, vivo, dataNascita, dataArrivo, dataUscita, idSpecie);
                dao.insert(a);
                view.showAnimaleMsg("Animale aggiunto con successo!", true);
            } else {
                Animale a = new Animale(editingAnimaleId, nome, sesso, vivo, dataNascita, dataArrivo, dataUscita, idSpecie);
                dao.update(a);
                view.showAnimaleMsg("Animale modificato con successo!", true);
            }
            
            view.setPanelNuovoAnimaleVisible(false);
            populateAnimali();
            editingAnimaleId = null;
        } catch(Exception e) {
            view.showAnimaleMsg("Errore: " + e.getMessage(), false);
        }
    }

    /* ═══ TAB 8 - Aree ═══════════════════════════════ */
    private void populateAree() {
        final List<Area> aree = new AreaDao().findAll();
        final java.util.Map<Integer, it.unibo.zoo.model.entity.TipoArea> tipoAreaMap = new it.unibo.zoo.model.jdbc.entityDao.TipoAreaDao().findAll().stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.TipoArea::getIdTipoArea, t -> t));

        final List<GestioneView.AreaRow> rows = new ArrayList<>();
        for (final Area a : aree) {
            final it.unibo.zoo.model.entity.TipoArea t = tipoAreaMap.get(a.getIdTipoArea());
            rows.add(new GestioneView.AreaRow(
                    String.valueOf(a.getIdArea()),
                    a.getNome(),
                    String.valueOf(a.getMetratura()),
                    t != null ? t.getNome() : "-"
            ));
        }
        view.setAree(rows);

        view.getComboAreaTipo().getItems().clear();
        for (final it.unibo.zoo.model.entity.TipoArea t : new it.unibo.zoo.model.jdbc.entityDao.TipoAreaDao().findAll()) {
            view.getComboAreaTipo().getItems().add(t.getIdTipoArea() + " - " + t.getNome());
        }
    }

    private void handleSalvaArea() {
        try {
            String nome = view.getTxtAreaNome().getText();
            Integer metratura = view.getSpinnerAreaMetratura().getValue();
            String tipoStr = view.getComboAreaTipo().getValue();
            
            if(nome == null || nome.isEmpty() || tipoStr == null || metratura == null) {
                view.showAreaMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idTipoArea = Integer.parseInt(tipoStr.split(" - ")[0]);
            
            Area a = new Area(0, nome, metratura, idTipoArea);
            new AreaDao().insert(a);
            
            view.showAreaMsg("Area aggiunta con successo!", true);
            view.setPanelNuovaAreaVisible(false);
            populateAree();
        } catch(Exception e) {
            view.showAreaMsg("Errore: " + e.getMessage(), false);
        }
    }

    /* ═══ TAB 9 - Recinti ═════════════════════════════ */
    private void populateRecinti() {
        final List<it.unibo.zoo.model.entity.Recinto> recinti = new it.unibo.zoo.model.jdbc.entityDao.RecintoDao().findAll();
        final java.util.Map<Integer, it.unibo.zoo.model.entity.Area> areaMap = new AreaDao().findAll().stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.Area::getIdArea, a -> a));
        final java.util.Map<Integer, it.unibo.zoo.model.entity.TipoRecinto> tipoRecintoMap = new it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao().findAll().stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.TipoRecinto::getIdTipoRecinto, t -> t));

        final List<GestioneView.RecintoRow> rows = new ArrayList<>();
        for (final it.unibo.zoo.model.entity.Recinto r : recinti) {
            final it.unibo.zoo.model.entity.Area a = areaMap.get(r.getIdArea());
            final it.unibo.zoo.model.entity.TipoRecinto t = tipoRecintoMap.get(r.getIdTipoRecinto());
            rows.add(new GestioneView.RecintoRow(
                    String.valueOf(r.getIdRecinto()),
                    a != null ? a.getNome() : "-",
                    String.valueOf(r.getCapienzaMassima()),
                    t != null ? t.getNome() : "-"
            ));
        }
        view.setRecinti(rows);

        // Compute top Recinto
        final List<Animale> animali = new AnimaleDao().findAll();
        final java.util.Set<Integer> animaliAttivi = animali.stream()
                .filter(an -> an.getDataUscita() == null && an.isVivo())
                .map(Animale::getIdAnimale)
                .collect(Collectors.toSet());

        final List<it.unibo.zoo.model.entity.StoricoCollocazione> storici = new it.unibo.zoo.model.jdbc.entityDao.StoricoCollocazioneDao().findAll();
        final java.util.Map<Integer, Integer> recintoCount = new java.util.HashMap<>();
        for (it.unibo.zoo.model.entity.StoricoCollocazione sc : storici) {
            if (sc.getDataFine() == null && animaliAttivi.contains(sc.getIdAnimale())) {
                recintoCount.put(sc.getIdRecinto(), recintoCount.getOrDefault(sc.getIdRecinto(), 0) + 1);
            }
        }

        int maxAnimali = -1;
        int topRecintoId = -1;
        for (java.util.Map.Entry<Integer, Integer> entry : recintoCount.entrySet()) {
            if (entry.getValue() > maxAnimali) {
                maxAnimali = entry.getValue();
                topRecintoId = entry.getKey();
            }
        }

        if (topRecintoId != -1) {
            it.unibo.zoo.model.entity.Recinto topR = null;
            for (it.unibo.zoo.model.entity.Recinto r : recinti) {
                if (r.getIdRecinto() == topRecintoId) { topR = r; break; }
            }
            if (topR != null) {
                it.unibo.zoo.model.entity.Area a = areaMap.get(topR.getIdArea());
                String nomeArea = a != null ? a.getNome() : "Area " + topR.getIdArea();
                view.getLblTopRecintoAnimali().setText(String.format("Recinto con più animali: Recinto %d in %s (%d animali)", 
                        topR.getIdRecinto(), nomeArea, maxAnimali));
            } else {
                view.getLblTopRecintoAnimali().setText("Recinto con più animali: Nessun dato");
            }
        } else {
            view.getLblTopRecintoAnimali().setText("Recinto con più animali: Nessun dato");
        }

        view.getComboRecintoArea().getItems().clear();
        for (final it.unibo.zoo.model.entity.Area a : new AreaDao().findAll()) {
            view.getComboRecintoArea().getItems().add(a.getIdArea() + " - " + a.getNome());
        }

        view.getComboRecintoTipo().getItems().clear();
        for (final it.unibo.zoo.model.entity.TipoRecinto t : new it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao().findAll()) {
            view.getComboRecintoTipo().getItems().add(t.getIdTipoRecinto() + " - " + t.getNome());
        }
    }

    private void handleSalvaRecinto() {
        try {
            String areaStr = view.getComboRecintoArea().getValue();
            String tipoStr = view.getComboRecintoTipo().getValue();
            Integer capienza = view.getSpinnerRecintoCapienza().getValue();
            
            if(areaStr == null || tipoStr == null || capienza == null) {
                view.showRecintoMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idArea = Integer.parseInt(areaStr.split(" - ")[0]);
            int idTipoRecinto = Integer.parseInt(tipoStr.split(" - ")[0]);
            
            it.unibo.zoo.model.entity.Recinto r = new it.unibo.zoo.model.entity.Recinto(0, capienza, idArea, idTipoRecinto);
            new it.unibo.zoo.model.jdbc.entityDao.RecintoDao().insert(r);
            
            view.showRecintoMsg("Recinto aggiunto con successo!", true);
            view.setPanelNuovoRecintoVisible(false);
            populateRecinti();
        } catch(Exception e) {
            view.showRecintoMsg("Errore: " + e.getMessage(), false);
        }
    }

}
