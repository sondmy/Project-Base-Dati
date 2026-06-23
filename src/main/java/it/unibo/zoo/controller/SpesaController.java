package it.unibo.zoo.controller;

import it.unibo.zoo.controller.DataEventBus.DataType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.model.jdbc.entityDao.CategoriaTransazioneDao;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.model.jdbc.entityDao.UtenteDao;
import it.unibo.zoo.view.GestioneView;

public class SpesaController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    public static void populateSpese(final GestioneView view) {
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

    public static void handleFiltraSpese(final GestioneView view) {
        final LocalDate from = view.getDateSpesaInizio().getValue();
        final LocalDate to = view.getDateSpesaFine().getValue();

        if (from == null || to == null) {
            // Se nessun filtro, ricarica tutte le spese
            SpesaController.populateSpese(view);
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

    public static void handleSalvaSpesa(final GestioneView view) {
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

            // L'EventBus si occupa di aggiornare sia la tab Spese che il Saldo
            DataEventBus.getInstance().publish(DataType.SPESA, DataType.TRANSAZIONE);
        } catch (Exception e) {
            view.showSpesaMsg("Errore: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

}
