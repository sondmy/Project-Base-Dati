package it.unibo.zoo.controller;

import it.unibo.zoo.model.MockData;
import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.Fornitore;
import it.unibo.zoo.model.entity.OrdineGiornaliero;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.TipoCibo;
import it.unibo.zoo.model.entity.TipoMansione;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.entity.Turno;
import it.unibo.zoo.model.entity.VisitaMedica;
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
        final List<Transazione> transazioni = MockData.getTransazioni();
        final Map<Integer, CategoriaTransazione> catMap = MockData.getCategorieTransazione().stream()
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
        final List<OrdineGiornaliero> ordini = MockData.getOrdiniGiornalieri();
        final Map<Integer, Fornitore> fornMap = MockData.getFornitori().stream()
                .collect(Collectors.toMap(Fornitore::getIdFornitore, f -> f));
        final Map<Integer, TipoCibo> ciboMap = MockData.getTipiCibo().stream()
                .collect(Collectors.toMap(TipoCibo::getIdTipoCibo, c -> c));

        final List<GestioneView.OrdineRow> rows = new ArrayList<>();
        for (final OrdineGiornaliero o : ordini) {
            final Fornitore f = fornMap.get(o.getIdFornitore());
            final TipoCibo c = ciboMap.get(o.getIdTipoCibo());
            rows.add(new GestioneView.OrdineRow(
                    o.getData().format(DATE_FMT),
                    f != null ? f.getNome() : "—",
                    c != null ? c.getNome() : "—",
                    String.format("%.1f", o.getQuantitaKg()),
                    o.isPagato() ? "Pagato" : "Da pagare"
            ));
        }
        view.setOrdini(rows);

        // Popola combo fornitori e tipi cibo
        view.getComboFornitore().getItems().clear();
        for (final Fornitore f : MockData.getFornitori()) {
            view.getComboFornitore().getItems().add(f.getNome());
        }
        view.getComboTipoCibo().getItems().clear();
        for (final TipoCibo c : MockData.getTipiCibo()) {
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
        final List<VisitaMedica> visite = MockData.getVisiteMediche();
        final Map<Integer, Animale> animMap = MockData.getAnimali().stream()
                .collect(Collectors.toMap(Animale::getIdAnimale, a -> a));
        final Map<Integer, Specie> specieMap = MockData.getSpecie().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));
        final Map<Integer, Dipendente> dipMap = MockData.getDipendenti().stream()
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
            final Dipendente vet = dipMap.get(v.getIdVeterinario());
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
        final List<Turno> turni = MockData.getTurniOggi();
        final Map<Integer, Dipendente> dipMap = MockData.getDipendenti().stream()
                .collect(Collectors.toMap(Dipendente::getIdDipendente, d -> d));
        final Map<Integer, TipoMansione> mansMap = MockData.getMansioni().stream()
                .collect(Collectors.toMap(TipoMansione::getIdTipoMansione, m -> m));
        final Map<Integer, Area> areaMap = MockData.getAree().stream()
                .collect(Collectors.toMap(Area::getIdArea, a -> a));

        final List<GestioneView.TurnoRow> rows = new ArrayList<>();
        for (final Turno t : turni) {
            final Dipendente d = dipMap.get(t.getIdDipendente());
            final String nomeDip = d != null ? d.getNome() + " " + d.getCognome() : "—";
            final String mansione;
            if (d != null) {
                final TipoMansione m = mansMap.get(d.getIdMansione());
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
