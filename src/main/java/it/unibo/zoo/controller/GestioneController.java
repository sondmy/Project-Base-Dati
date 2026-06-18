package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.entity.Dipendente;
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
import java.util.stream.Collectors;

/**
 * Controller per il pannello di gestione (4 tab).
 */
public class GestioneController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private final GestioneView view;
    private boolean panelOrdineVisible;

    public GestioneController(final GestioneView view) {
        this.view = view;
        this.panelOrdineVisible = false;
        init();
    }

    private void init() {
        populateSaldo();
        populateOrdini();
        populateVisite();
        populateTurni();

        // Toggle pannello nuovo ordine
        view.getBtnNuovoOrdine().setOnAction(e -> {
            panelOrdineVisible = !panelOrdineVisible;
            view.setPanelNuovoOrdineVisible(panelOrdineVisible);
        });

        // Salva ordine (mock)
        view.getBtnSalvaOrdine().setOnAction(e -> handleSalvaOrdine());
    }

    /* ═══ TAB 1 — Saldo ══════════════════════════════════ */

    private void populateSaldo() {
        final List<Transazione> transazioni = new TransazioneDao().findAll();
        final Map<Integer, CategoriaTransazione> catMap = new CategoriaTransazioneDao().findAll().stream()
                .collect(Collectors.toMap(CategoriaTransazione::getIdCategoria, c -> c));

        double entrate = 0;
        double uscite = 0;
        for (final Transazione t : transazioni) {
            if ("E".equals(t.getTipo())) {
                entrate += t.getImporto();
            } else {
                uscite += t.getImporto();
            }
        }

        view.setEntrate(String.format("\u20AC%.2f", entrate));
        view.setUscite(String.format("\u20AC%.2f", uscite));
        view.setSaldo(String.format("\u20AC%.2f", entrate - uscite));

        // Ultime 5 transazioni (le ultime nella lista)
        final int start = Math.max(0, transazioni.size() - 5);
        final List<GestioneView.TransazioneRow> rows = new ArrayList<>();
        for (int i = transazioni.size() - 1; i >= start; i--) {
            final Transazione t = transazioni.get(i);
            final CategoriaTransazione cat = catMap.get(t.getIdCategoria());
            rows.add(new GestioneView.TransazioneRow(
                    t.getData().format(DATE_FMT),
                    t.getTipo(),
                    String.format("\u20AC%.2f", t.getImporto()),
                    cat != null ? cat.getNome() : "—",
                    t.getDescrizione()
            ));
        }
        view.setTransazioni(rows);
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
                    String.format("%.1f", o.getQuantitaKg()),
                    o.getIdTransazione() != null ? "Pagato" : "Da pagare"
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

        view.showOrdineMsg(String.format(
                "Ordine salvato! %s — %s — %d kg", fornitore, tipoCibo, qta), true);
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
            final String stato = v.getDataFine() == null ? "In corso" : "Concluso";

            rows.add(new GestioneView.VisitaRow(
                    nomeAnimale, nomeSpecie, v.getDiagnosi(),
                    v.getDataVisita().format(DATE_FMT),
                    nomeVet, stato
            ));
        }
        view.setVisite(rows);
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
        for (final Turno t : turni) {
            final Dipendente d = dipMap.get(t.getIdDipendente());
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
                    nomeDip, mansione, nomeArea,
                    t.getOraInizio().format(TIME_FMT),
                    t.getOraFine().format(TIME_FMT)
            ));
        }
        view.setTurni(rows);
    }
}
