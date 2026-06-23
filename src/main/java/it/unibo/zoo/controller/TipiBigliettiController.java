package it.unibo.zoo.controller;

import it.unibo.zoo.controller.DataEventBus.DataType;

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
                        t.getDescrizione(),
                        String.format("€%.2f", t.getPrezzo()),
                        t.isAttivo() ? "Sì" : "No"
                ))
                .collect(Collectors.toList());
        view.setTipiBiglietti(rows);
    }

    public static void handleSalvaTipoBiglietto(GestioneView view, Integer editingId) {
        try {
            String nome = view.getTxtTipoBigliettoNome().getText();
            String desc = view.getTxtTipoBigliettoDesc().getText();
            String prezzoStr = view.getTxtTipoBigliettoPrezzo().getText();
            boolean attivo = view.getChkTipoBigliettoAttivo().isSelected();

            if (nome == null || nome.trim().isEmpty() || prezzoStr == null || prezzoStr.trim().isEmpty()) {
                view.showTipiBigliettiMsg("Nome e prezzo sono obbligatori.", false);
                return;
            }

            double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));

            TipoBiglietto nuovo = new TipoBiglietto(nome, desc, prezzo, attivo);
            if (editingId == null) {
                new TipoBigliettoDao().insert(nuovo);
            } else {
                nuovo.setIdBiglietto(editingId);
                new TipoBigliettoDao().update(nuovo);
            }

            view.showTipiBigliettiMsg("Tipo biglietto salvato con successo!", true);
            view.getTxtTipoBigliettoNome().clear();
            view.getTxtTipoBigliettoDesc().clear();
            view.getTxtTipoBigliettoPrezzo().clear();
            view.setPanelNuovoTipoBigliettoVisible(false);

            DataEventBus.getInstance().publish(DataType.TIPO_BIGLIETTO);
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
                DataEventBus.getInstance().publish(DataType.TIPO_BIGLIETTO);
                view.showTipiBigliettiMsg("Tipo biglietto eliminato.", true);
            } catch (Exception ex) {
                view.showTipiBigliettiMsg("Impossibile eliminare (potrebbe essere in uso in alcuni scontrini).", false);
            }
        }
    }
}