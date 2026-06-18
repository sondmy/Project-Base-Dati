package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.TipoBiglietto;
import it.unibo.zoo.model.jdbc.entityDao.TipoBigliettoDao;
import it.unibo.zoo.view.BigliettiView;

import java.util.List;

/**
 * Controller per la sezione Biglietti.
 * Popola la tabella, gestisce la visibilità del campo numPersone
 * e valida l'acquisto.
 */
public class BigliettiController {

    private final BigliettiView view;
    private final List<TipoBiglietto> tipiBiglietto;

    public BigliettiController(final BigliettiView view) {
        this.view = view;
        this.tipiBiglietto = new TipoBigliettoDao().findAll();
        init();
    }

    private void init() {
        // Popola tabella e combo
        view.setTipiBiglietto(tipiBiglietto);

        // Gestione visibilità numPersone in base al campo nomeGruppo
        view.getTxtNomeGruppo().textProperty().addListener((obs, oldVal, newVal) -> {
            final boolean hasGroup = newVal != null && !newVal.trim().isEmpty();
            view.setNumPersoneVisible(hasGroup);
        });

        // Gestione click conferma
        view.getBtnConferma().setOnAction(e -> handleConferma());
    }

    private void handleConferma() {
        view.hideMessage();

        final String bigliettoScelto = view.getComboBiglietto().getValue();
        if (bigliettoScelto == null || bigliettoScelto.isEmpty()) {
            view.showError("Seleziona un tipo di biglietto.");
            return;
        }

        final int quantita = view.getSpinnerQuantita().getValue();

        // Trova il prezzo del biglietto scelto
        double prezzo = 0;
        for (final TipoBiglietto tb : tipiBiglietto) {
            if (tb.getNome().equals(bigliettoScelto)) {
                prezzo = tb.getPrezzo();
                break;
            }
        }

        final double totale = prezzo * quantita;

        final String nomeGruppo = view.getTxtNomeGruppo().getText();
        if (nomeGruppo != null && !nomeGruppo.trim().isEmpty()) {
            final int numPersone = view.getSpinnerNumPersone().getValue();
            view.showSuccess(String.format(
                    "Acquisto registrato! Gruppo: %s (%d persone) — %d x %s — Totale: \u20AC%.2f",
                    nomeGruppo.trim(), numPersone, quantita, bigliettoScelto, totale));
        } else {
            view.showSuccess(String.format(
                    "Acquisto registrato! %d x %s — Totale: \u20AC%.2f",
                    quantita, bigliettoScelto, totale));
        }
    }
}
