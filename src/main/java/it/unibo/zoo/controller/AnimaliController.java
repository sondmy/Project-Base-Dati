package it.unibo.zoo.controller;

import it.unibo.zoo.model.MockData;
import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Habitat;
import it.unibo.zoo.model.entity.Recinto;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.StatoEsistenza;
import it.unibo.zoo.view.AnimaliView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller per la sezione Animali.
 * Popola e filtra la TableView.
 */
public class AnimaliController {

    private final AnimaliView view;
    private final List<Animale> animali;
    private final Map<Integer, Specie> specieMap;
    private final Map<Integer, Habitat> habitatMap;
    private final Map<Integer, StatoEsistenza> statoMap;
    private final Map<Integer, Recinto> recintoMap;

    public AnimaliController(final AnimaliView view) {
        this.view = view;
        this.animali = MockData.getAnimali();
        this.specieMap = MockData.getSpecie().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));
        this.habitatMap = MockData.getHabitat().stream()
                .collect(Collectors.toMap(Habitat::getIdHabitat, h -> h));
        this.statoMap = MockData.getStatiEsistenza().stream()
                .collect(Collectors.toMap(StatoEsistenza::getIdStato, s -> s));
        this.recintoMap = MockData.getRecinti().stream()
                .collect(Collectors.toMap(Recinto::getIdRecinto, r -> r));
        init();
    }

    private void init() {
        // Popola iniziale
        view.setAnimali(buildRows(animali));

        // Ricerca
        view.getBtnCerca().setOnAction(e -> handleCerca());
        view.getTxtCerca().setOnAction(e -> handleCerca());
    }

    private void handleCerca() {
        final String query = view.getTxtCerca().getText();
        if (query == null || query.trim().isEmpty()) {
            view.setAnimali(buildRows(animali));
            return;
        }
        final String lower = query.trim().toLowerCase();
        final List<Animale> filtrati = animali.stream()
                .filter(a -> {
                    if (a.getNome().toLowerCase().contains(lower)) {
                        return true;
                    }
                    final Specie sp = specieMap.get(a.getIdSpecie());
                    return sp != null && sp.getNomeComune().toLowerCase().contains(lower);
                })
                .collect(Collectors.toList());
        view.setAnimali(buildRows(filtrati));
    }

    private List<AnimaliView.AnimaleRow> buildRows(final List<Animale> lista) {
        final List<AnimaliView.AnimaleRow> rows = new ArrayList<>();
        for (final Animale a : lista) {
            final Specie sp = specieMap.get(a.getIdSpecie());
            final String nomeSpecie = sp != null ? sp.getNomeComune() : "—";
            final String habitat;
            if (sp != null) {
                final Habitat h = habitatMap.get(sp.getIdHabitat());
                habitat = h != null ? h.getNome() : "—";
            } else {
                habitat = "—";
            }
            final String stato;
            if (sp != null) {
                final StatoEsistenza se = statoMap.get(sp.getIdStatoEsistenza());
                stato = se != null ? se.getNome() : "—";
            } else {
                stato = "—";
            }
            // Sesso: non presente nell'entity, usiamo mock basato su idAnimale pari/dispari
            final String sesso = (a.getIdAnimale() % 2 == 0) ? "F" : "M";
            final Recinto rec = recintoMap.get(a.getIdRecinto());
            final String recinto = rec != null ? "R" + rec.getIdRecinto() : "—";

            rows.add(new AnimaliView.AnimaleRow(a.getNome(), nomeSpecie, sesso, habitat, stato, recinto));
        }
        return rows;
    }
}
