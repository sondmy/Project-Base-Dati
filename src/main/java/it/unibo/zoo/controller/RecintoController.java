package it.unibo.zoo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import it.unibo.zoo.model.jdbc.entityDao.AreaDao;
import it.unibo.zoo.model.jdbc.entityDao.RecintoDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao;
import it.unibo.zoo.model.entity.Recinto;
import it.unibo.zoo.model.entity.TipoRecinto;

import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.view.GestioneView;

public class RecintoController {

    public static void populateRecinti(final GestioneView view) {
        final List<Recinto> recinti = new RecintoDao().findAll();
        final Map<Integer, Area> areaMap = new AreaDao().findAll().stream()
                .collect(Collectors.toMap(Area::getIdArea, a -> a));
        final Map<Integer, TipoRecinto> tipoRecintoMap = new TipoRecintoDao().findAll().stream()
                .collect(Collectors.toMap(TipoRecinto::getIdTipoRecinto, t -> t));

        final List<GestioneView.RecintoRow> rows = new ArrayList<>();
        for (final Recinto r : recinti) {
            final Area a = areaMap.get(r.getIdArea());
            final TipoRecinto t = tipoRecintoMap.get(r.getIdTipoRecinto());
            rows.add(new GestioneView.RecintoRow(
                    String.valueOf(r.getIdRecinto()),
                    r.getNome(),
                    a != null ? a.getNome() : "-",
                    String.valueOf(r.getCapienzaMassima()),
                    t != null ? t.getNome() : "-"
            ));
        }
        view.setRecinti(rows);

        view.getComboRecintoArea().getItems().clear();
        for (final Area a : new AreaDao().findAll()) {
            view.getComboRecintoArea().getItems().add(a.getIdArea() + " - " + a.getNome());
        }

        view.getComboRecintoTipo().getItems().clear();
        for (final TipoRecinto t : new TipoRecintoDao().findAll()) {
            view.getComboRecintoTipo().getItems().add(t.getIdTipoRecinto() + " - " + t.getNome());
        }
        
        // Calcola recinto con più animali attivi
        try {
            final List<it.unibo.zoo.model.entity.StoricoCollocazione> collocazioni = new it.unibo.zoo.model.jdbc.entityDao.StoricoCollocazioneDao().findAll();
            final Map<Integer, Long> conteggioAnimali = collocazioni.stream()
                .filter(sc -> sc.getDataFine() == null)
                .collect(Collectors.groupingBy(it.unibo.zoo.model.entity.StoricoCollocazione::getIdRecinto, Collectors.counting()));
                
            Integer topRecintoId = null;
            long maxAnimali = -1;
            for (Map.Entry<Integer, Long> entry : conteggioAnimali.entrySet()) {
                if (entry.getValue() > maxAnimali) {
                    maxAnimali = entry.getValue();
                    topRecintoId = entry.getKey();
                }
            }
            
            if (topRecintoId != null && maxAnimali > 0) {
                final Integer finalTopId = topRecintoId;
                Recinto topRecinto = recinti.stream().filter(r -> r.getIdRecinto() == finalTopId).findFirst().orElse(null);
                if (topRecinto != null) {
                    view.getLblTopRecintoAnimali().setText("Recinto più popolato: " + topRecinto.getNome() + " (" + maxAnimali + " animali)");
                    view.getLblTopRecintoAnimali().setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1976D2;");
                }
            } else {
                view.getLblTopRecintoAnimali().setText("Recinto più popolato: Nessun animale presente");
                view.getLblTopRecintoAnimali().setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            }
        } catch (Exception e) {
            view.getLblTopRecintoAnimali().setText("Errore nel calcolo del recinto più popolato");
        }
    }

    public static void handleSalvaRecinto(final GestioneView view, final Integer editingRecintoId) {
        try {
            String nome = view.getTxtRecintoNome().getText();
            String areaStr = view.getComboRecintoArea().getValue();
            String tipoStr = view.getComboRecintoTipo().getValue();
            Integer capienza = view.getSpinnerRecintoCapienza().getValue();
            
            if(nome == null || nome.isBlank() || areaStr == null || tipoStr == null || capienza == null) {
                view.showRecintoMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idArea = Integer.parseInt(areaStr.split(" - ")[0]);
            int idTipoRecinto = Integer.parseInt(tipoStr.split(" - ")[0]);
            
            Recinto r = new Recinto(nome.trim(), capienza, idArea, idTipoRecinto);
            
            if (editingRecintoId != null) {
                r.setIdRecinto(editingRecintoId);
                new RecintoDao().update(r);
                view.showRecintoMsg("Recinto modificato con successo!", true);
            } else {
                new RecintoDao().insert(r);
                view.showRecintoMsg("Recinto aggiunto con successo!", true);
            }
            
            view.setPanelNuovoRecintoVisible(false);
            RecintoController.populateRecinti(view);
        } catch(Exception e) {
            view.showRecintoMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleEliminaRecinto(final GestioneView view) {
        GestioneView.RecintoRow row = view.getTableRecinti().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new RecintoDao().delete(Integer.parseInt(row.getIdRecinto()));
                populateRecinti(view);
                view.showRecintoMsg("Recinto eliminato con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showRecintoMsg("Impossibile eliminare (in uso).", false);
            }
        }
    }

}
