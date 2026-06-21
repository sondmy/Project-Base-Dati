package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.TipoBiglietto;
import it.unibo.zoo.model.jdbc.entityDao.TipoBigliettoDao;
import it.unibo.zoo.view.GestioneView;

import java.util.List;
import java.util.stream.Collectors;

public class TipiBigliettiController {

    public static void populateTipiBiglietti(GestioneView view) {
        List<TipoBiglietto> tipi = new TipoBigliettoDao().findAll();
        List<GestioneView.TipiBigliettiRow> rows = tipi.stream()
                .map(t -> new GestioneView.TipiBigliettiRow(
                        String.valueOf(t.getIdBiglietto()),
                        t.getNome(),
                        String.format("€%.2f", t.getPrezzo())
                ))
                .collect(Collectors.toList());
        view.setTipiBiglietti(rows);
    }

    public static void handleSalvaTipoBiglietto(GestioneView view) {
        try {
            String nome = view.getTxtTipoBigliettoNome().getText();
            String prezzoStr = view.getTxtTipoBigliettoPrezzo().getText();

            if (nome == null || nome.trim().isEmpty() || prezzoStr == null || prezzoStr.trim().isEmpty()) {
                view.showTipiBigliettiMsg("Nome e prezzo sono obbligatori.", false);
                return;
            }

            double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));

            TipoBiglietto nuovo = new TipoBiglietto(nome, "", prezzo, true);
            new TipoBigliettoDao().insert(nuovo);

            view.showTipiBigliettiMsg("Tipo biglietto salvato con successo!", true);
            view.getTxtTipoBigliettoNome().clear();
            view.getTxtTipoBigliettoPrezzo().clear();
            view.setPanelNuovoTipoBigliettoVisible(false);

            populateTipiBiglietti(view);
        } catch (NumberFormatException ex) {
            view.showTipiBigliettiMsg("Il prezzo deve essere un numero valido.", false);
        } catch (Exception ex) {
            view.showTipiBigliettiMsg("Errore: " + ex.getMessage(), false);
        }
    }

    public static void handleEliminaTipoBiglietto(GestioneView view) {
        GestioneView.TipiBigliettiRow sel = view.getTableTipiBiglietti().getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                int id = Integer.parseInt(sel.getIdBiglietto());
                new TipoBigliettoDao().delete(id);
                populateTipiBiglietti(view);
                view.showTipiBigliettiMsg("Tipo biglietto eliminato.", true);
            } catch (Exception ex) {
                view.showTipiBigliettiMsg("Impossibile eliminare (potrebbe essere in uso in alcuni scontrini).", false);
            }
        }
    }
}