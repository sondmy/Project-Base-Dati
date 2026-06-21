package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.CategoriaTransazione;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.jdbc.entityDao.CategoriaTransazioneDao;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.view.DonazioniView;

import java.time.LocalDate;
import java.util.Optional;

public class DonazioniController {

    private final DonazioniView view;

    public DonazioniController(final DonazioniView view) {
        this.view = view;
        view.getBtnSalvaDonazione().setOnAction(e -> handleSalvaDonazione());
    }

    private void handleSalvaDonazione() {
        try {
            final String importoStr = view.getTxtImporto().getText();
            if (importoStr == null || importoStr.isEmpty()) {
                view.showMessage("Inserire un importo valido.", false);
                return;
            }

            final double importo = Double.parseDouble(importoStr.replace(",", "."));
            if (importo <= 0) {
                view.showMessage("L'importo deve essere maggiore di zero.", false);
                return;
            }

            String descrizione = view.getTxtDescrizione().getText();
            if (descrizione == null || descrizione.isEmpty()) {
                descrizione = "Donazione anonima";
            } else {
                descrizione = "Donazione: " + descrizione;
            }

            final CategoriaTransazioneDao catDao = new CategoriaTransazioneDao();
            Optional<CategoriaTransazione> catOpt = catDao.findByNome("Donazioni");
            int idCategoria;

            if (catOpt.isPresent()) {
                idCategoria = catOpt.get().getIdCategoria();
            } else {
                // Crea la categoria se non esiste
                CategoriaTransazione nuovaCat = new CategoriaTransazione(0, "Donazioni", "Donazioni volontarie", "E");
                nuovaCat = catDao.insert(nuovaCat);
                idCategoria = nuovaCat.getIdCategoria();
            }

            final Transazione t = new Transazione("E", importo, LocalDate.now(), descrizione, idCategoria, 1, null, null);
            new TransazioneDao().insert(t);

            view.showMessage("Grazie per aver donato!", true);
            view.getTxtImporto().clear();
            view.getTxtDescrizione().clear();

        } catch (NumberFormatException e) {
            view.showMessage("Formato importo non valido.", false);
        } catch (Exception e) {
            view.showMessage("Errore durante il salvataggio: " + e.getMessage(), false);
        }
    }
}
