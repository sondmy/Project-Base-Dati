package it.unibo.zoo.controller;

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
            AreeController.populateAree(view);
        } catch(Exception e) {
            view.showAreaMsg("Errore: " + e.getMessage(), false);
        }
    }

    
}
