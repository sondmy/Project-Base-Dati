package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.TipoBiglietto;
import it.unibo.zoo.model.jdbc.entityDao.TipoBigliettoDao;
import it.unibo.zoo.model.entity.Scontrino;
import it.unibo.zoo.model.entity.DettaglioScontrino;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.jdbc.entityDao.ScontrinoDao;
import it.unibo.zoo.model.jdbc.entityDao.DettaglioScontrinoDao;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.view.BigliettiView;

import java.time.LocalDate;
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
        int idTipoBiglietto = 0;
        double prezzo = 0;
        for (final TipoBiglietto tb : tipiBiglietto) {
            if (tb.getNome().equals(bigliettoScelto)) {
                prezzo = tb.getPrezzo();
                idTipoBiglietto = tb.getIdBiglietto();
                break;
            }
        }

        final double totale = prezzo * quantita;
        final String nomeGruppo = view.getTxtNomeGruppo().getText();
        Integer numPersone = null;
        
        if (nomeGruppo != null && !nomeGruppo.trim().isEmpty()) {
            numPersone = view.getSpinnerNumPersone().getValue();
        }

        try {
            Scontrino scontrino = new Scontrino(0, LocalDate.now(), 
                    nomeGruppo != null && !nomeGruppo.trim().isEmpty() ? nomeGruppo.trim() : null, 
                    numPersone, 2); // 2 = Utente Biglietteria
            scontrino = new ScontrinoDao().insert(scontrino);

            DettaglioScontrino dett = new DettaglioScontrino(0, scontrino.getIdScontrino(), idTipoBiglietto, quantita, prezzo);
            new DettaglioScontrinoDao().insert(dett);

            Transazione trans = new Transazione(0, "E", totale, LocalDate.now(), 
                    "Acquisto biglietti " + bigliettoScelto + " x" + quantita, 
                    1, 2, null, scontrino.getIdScontrino()); // idCategoria=1 (Vendita Biglietti)
            new TransazioneDao().insert(trans);

            if (numPersone != null) {
                view.showSuccess(String.format(
                        "Acquisto registrato! Gruppo: %s (%d persone) — %d x %s — Totale: \u20AC%.2f",
                        nomeGruppo.trim(), numPersone, quantita, bigliettoScelto, totale));
            } else {
                view.showSuccess(String.format(
                        "Acquisto registrato! %d x %s — Totale: \u20AC%.2f",
                        quantita, bigliettoScelto, totale));
            }
        } catch (Exception ex) {
            view.showError("Errore durante l'acquisto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
