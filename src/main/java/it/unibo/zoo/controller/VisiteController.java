package it.unibo.zoo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.VisitaMedica;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.model.jdbc.entityDao.DipendenteDao;
import it.unibo.zoo.model.jdbc.entityDao.SpecieDao;
import it.unibo.zoo.model.jdbc.entityDao.VisitaMedicaDao;
import it.unibo.zoo.view.GestioneView;

public class VisiteController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void populateVisite(final GestioneView view) {
        final List<VisitaMedica> visite = new VisitaMedicaDao().findAll();
        final Map<Integer, Animale> animMap = new AnimaleDao().findAll().stream()
                .collect(Collectors.toMap(Animale::getIdAnimale, a -> a));
        final Map<Integer, Specie> specieMap = new SpecieDao().findAll().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));
        final Map<Integer, Dipendente> dipMap = new DipendenteDao().findAll().stream()
                .collect(Collectors.toMap(Dipendente::getIdDipendente, d -> d));

        final List<GestioneView.VisitaRow> rows = new ArrayList<>();
        for (final VisitaMedica v : visite) {
            final Animale a = animMap.get(v.getIdAnimale());
            final String nomeAnimale = a != null ? a.getNome() : "—";
            final String nomeSpecie;
            if (a != null) {
                final Specie sp = specieMap.get(a.getIdSpecie());
                nomeSpecie = sp != null ? sp.getNomeComune() : "—";
            } else {
                nomeSpecie = "—";
            }
            final Dipendente vet = dipMap.get(v.getIdDipendente());
            final String nomeVet = vet != null ? vet.getNome() + " " + vet.getCognome() : "—";
            final String stato = v.getDataFine() == null ? "In corso" : "Concluso";

            rows.add(new GestioneView.VisitaRow(
                    nomeAnimale, nomeSpecie, v.getDiagnosi(),
                    v.getDataVisita().format(DATE_FMT),
                    nomeVet, stato
            ));
        }
        view.setVisite(rows);
        
        view.getComboVisitaAnimale().getItems().clear();
        for(Animale a : new AnimaleDao().findAll()) {
            view.getComboVisitaAnimale().getItems().add(a.getIdAnimale() + " - " + a.getNome());
        }
        view.getComboVisitaVet().getItems().clear();
        for(Dipendente d : new DipendenteDao().findAll()) { // Here we just take any dipendente for now
            view.getComboVisitaVet().getItems().add(d.getIdDipendente() + " - " + d.getNome() + " " + d.getCognome());
        }
    }

    

    public static void handleSalvaVisita(final GestioneView view) {
        try {
            String animale = view.getComboVisitaAnimale().getValue();
            String vet = view.getComboVisitaVet().getValue();
            String diagnosi = view.getTxtDiagnosi().getText();
            LocalDate data = view.getDateVisita().getValue();
            
            if(animale == null || vet == null || data == null) {
                view.showVisitaMsg("Animale, Veterinario e Data sono obbligatori.", false);
                return;
            }
            
            int idAnimale = Integer.parseInt(animale.split(" - ")[0]);
            int idVet = Integer.parseInt(vet.split(" - ")[0]);
            
            VisitaMedica v = new VisitaMedica(0, null, diagnosi, null, data, null, idAnimale, idVet);
            new VisitaMedicaDao().insert(v);
            
            view.showVisitaMsg("Visita registrata con successo!", true);
            view.setPanelNuovaVisitaVisible(false);
            VisiteController.populateVisite(view);
        } catch(Exception e) {
            view.showVisitaMsg("Errore: " + e.getMessage(), false);
        }
    }
}
