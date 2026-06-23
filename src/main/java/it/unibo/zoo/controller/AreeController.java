package it.unibo.zoo.controller;

import it.unibo.zoo.controller.DataEventBus.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.zoo.model.entity.Area;

import java.util.Map;

import it.unibo.zoo.model.jdbc.entityDao.AreaDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoAreaDao;
import it.unibo.zoo.model.entity.TipoArea;

import it.unibo.zoo.view.GestioneView;

public class AreeController {
    
    public static void populateAree(final GestioneView view) {
        final List<Area> aree = new AreaDao().findAll();
        final Map<Integer, TipoArea> tipoAreaMap = new TipoAreaDao().findAll().stream()
                .collect(Collectors.toMap(TipoArea::getIdTipoArea, t -> t));

        final List<GestioneView.AreaRow> rows = new ArrayList<>();
        for (final Area a : aree) {
            final TipoArea t = tipoAreaMap.get(a.getIdTipoArea());
            rows.add(new GestioneView.AreaRow(
                    String.valueOf(a.getIdArea()),
                    a.getNome(),
                    String.valueOf(a.getMetratura()),
                    t != null ? t.getNome() : "-"
            ));
        }
        view.setAree(rows);

        view.getComboAreaTipo().getItems().clear();
        for (final TipoArea t : new TipoAreaDao().findAll()) {
            view.getComboAreaTipo().getItems().add(t.getIdTipoArea() + " - " + t.getNome());
        }
    }

    public static void handleSalvaArea(final GestioneView view) {
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
            DataEventBus.getInstance().publish(DataType.AREA);
        } catch(Exception e) {
            view.showAreaMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleEliminaArea(final GestioneView view) {
        GestioneView.AreaRow row = view.getTableAree().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new AreaDao().delete(Integer.parseInt(row.getIdArea()));
                DataEventBus.getInstance().publish(DataType.AREA);
                view.showAreaMsg("Area eliminata con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showAreaMsg("Impossibile eliminare (in uso).", false);
            }
        }
    }

    public static void populateTipoArea(final GestioneView view) {
        List<GestioneView.TipoAreaRow> rows = new TipoAreaDao().findAll().stream()
                .map(t -> new GestioneView.TipoAreaRow(
                        String.valueOf(t.getIdTipoArea()),
                        t.getNome(),
                        t.getDescrizione() != null ? t.getDescrizione() : "-"
                )).collect(Collectors.toList());
        view.setTipoArea(rows);
    }

    public static void handleAggiungiTipoArea(final GestioneView view) {
        String nome = view.getTxtTipoAreaNome().getText();
        String desc = view.getTxtTipoAreaDesc().getText();
        if (nome == null || nome.trim().isEmpty()) return;
        
        try {
            TipoArea entity = new TipoArea(nome.trim(), desc != null && !desc.trim().isEmpty() ? desc.trim() : null);
            new TipoAreaDao().insert(entity);
            view.getTxtTipoAreaNome().clear();
            view.getTxtTipoAreaDesc().clear();
            DataEventBus.getInstance().publish(DataType.TIPO_AREA);
            view.showTipoAreaMsg("Tipo Area aggiunto con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showTipoAreaMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviTipoArea(final GestioneView view) {
        GestioneView.TipoAreaRow row = view.getTableTipoArea().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new TipoAreaDao().delete(Integer.parseInt(row.getIdTipoArea()));
                DataEventBus.getInstance().publish(DataType.TIPO_AREA);
                view.showTipoAreaMsg("Tipo Area rimosso con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showTipoAreaMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }

    public static void populateTipoRecinto(final GestioneView view) {
        List<GestioneView.TipoRecintoRow> rows = new it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao().findAll().stream()
                .map(t -> new GestioneView.TipoRecintoRow(
                        String.valueOf(t.getIdTipoRecinto()),
                        t.getNome(),
                        t.getDescrizione() != null ? t.getDescrizione() : "-"
                )).collect(Collectors.toList());
        view.setTipoRecinto(rows);
    }

    public static void handleAggiungiTipoRecinto(final GestioneView view) {
        String nome = view.getTxtTipoRecintoNome().getText();
        String desc = view.getTxtTipoRecintoDesc().getText();
        if (nome == null || nome.trim().isEmpty()) return;
        
        try {
            it.unibo.zoo.model.entity.TipoRecinto entity = new it.unibo.zoo.model.entity.TipoRecinto(nome.trim(), desc != null && !desc.trim().isEmpty() ? desc.trim() : null);
            new it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao().insert(entity);
            view.getTxtTipoRecintoNome().clear();
            view.getTxtTipoRecintoDesc().clear();
            DataEventBus.getInstance().publish(DataType.TIPO_RECINTO);
            view.showTipoRecintoMsg("Tipo Recinto aggiunto con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showTipoRecintoMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviTipoRecinto(final GestioneView view) {
        GestioneView.TipoRecintoRow row = view.getTableTipoRecinto().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new it.unibo.zoo.model.jdbc.entityDao.TipoRecintoDao().delete(Integer.parseInt(row.getIdTipoRecinto()));
                DataEventBus.getInstance().publish(DataType.TIPO_RECINTO);
                view.showTipoRecintoMsg("Tipo Recinto rimosso con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showTipoRecintoMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }
}
