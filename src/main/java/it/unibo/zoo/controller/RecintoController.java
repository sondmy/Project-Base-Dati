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

}
