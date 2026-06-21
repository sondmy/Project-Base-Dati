package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.StatoEsistenza;
import it.unibo.zoo.model.entity.Habitat;
import it.unibo.zoo.model.entity.FamigliaSpecie;
import it.unibo.zoo.model.jdbc.entityDao.StatoEsistenzaDao;
import it.unibo.zoo.model.jdbc.entityDao.HabitatDao;
import it.unibo.zoo.model.jdbc.entityDao.FamigliaSpecieDao;
import it.unibo.zoo.view.GestioneView;
import it.unibo.zoo.view.GestioneView.StatoEsistenzaRow;
import it.unibo.zoo.view.GestioneView.HabitatRow;
import it.unibo.zoo.view.GestioneView.FamigliaSpecieRow;

import java.util.List;
import java.util.stream.Collectors;

public class ClassificazioneController {

    public static void populateClassificazione(final GestioneView view) {
        populateStatoEsistenza(view);
        populateHabitat(view);
        populateFamigliaSpecie(view);
    }

    private static void populateStatoEsistenza(final GestioneView view) {
        StatoEsistenzaDao dao = new StatoEsistenzaDao();
        List<StatoEsistenza> list = dao.findAll();
        List<StatoEsistenzaRow> rows = list.stream()
                .map(s -> new StatoEsistenzaRow(
                        String.valueOf(s.getIdStato()),
                        s.getNome(),
                        s.getDescrizione() != null ? s.getDescrizione() : "—"
                ))
                .collect(Collectors.toList());
        view.setStatoEsistenza(rows);
    }

    private static void populateHabitat(final GestioneView view) {
        HabitatDao dao = new HabitatDao();
        List<Habitat> list = dao.findAll();
        List<HabitatRow> rows = list.stream()
                .map(h -> new HabitatRow(
                        String.valueOf(h.getIdHabitat()),
                        h.getNome(),
                        h.getDescrizione() != null ? h.getDescrizione() : "—"
                ))
                .collect(Collectors.toList());
        view.setHabitat(rows);
    }

    private static void populateFamigliaSpecie(final GestioneView view) {
        FamigliaSpecieDao dao = new FamigliaSpecieDao();
        List<FamigliaSpecie> list = dao.findAll();
        List<FamigliaSpecieRow> rows = list.stream()
                .map(f -> new FamigliaSpecieRow(
                        String.valueOf(f.getIdFamigliaSpecie()),
                        f.getNome(),
                        f.getDescrizione() != null ? f.getDescrizione() : "—"
                ))
                .collect(Collectors.toList());
        view.setFamigliaSpecie(rows);
    }

    public static void handleAggiungiStato(final GestioneView view) {
        String nome = view.getTxtStatoNome().getText();
        String desc = view.getTxtStatoDesc().getText();
        if (nome == null || nome.trim().isEmpty()) return;
        
        try {
            StatoEsistenzaDao dao = new StatoEsistenzaDao();
            StatoEsistenza entity = new StatoEsistenza(nome.trim(), desc != null && !desc.trim().isEmpty() ? desc.trim() : null);
            dao.insert(entity);
            view.getTxtStatoNome().clear();
            view.getTxtStatoDesc().clear();
            populateStatoEsistenza(view);
            view.showStatoMsg("Stato aggiunto con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showStatoMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviStato(final GestioneView view) {
        StatoEsistenzaRow row = view.getTableStatoEsistenza().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new StatoEsistenzaDao().delete(Integer.parseInt(row.getIdStato()));
                populateStatoEsistenza(view);
                view.showStatoMsg("Stato rimosso con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showStatoMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }

    public static void handleAggiungiHabitat(final GestioneView view) {
        String nome = view.getTxtHabitatNome().getText();
        String desc = view.getTxtHabitatDesc().getText();
        if (nome == null || nome.trim().isEmpty()) return;
        
        try {
            HabitatDao dao = new HabitatDao();
            Habitat entity = new Habitat(nome.trim(), desc != null && !desc.trim().isEmpty() ? desc.trim() : null);
            dao.insert(entity);
            view.getTxtHabitatNome().clear();
            view.getTxtHabitatDesc().clear();
            populateHabitat(view);
            view.showHabitatMsg("Habitat aggiunto con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showHabitatMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviHabitat(final GestioneView view) {
        HabitatRow row = view.getTableHabitat().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new HabitatDao().delete(Integer.parseInt(row.getIdHabitat()));
                populateHabitat(view);
                view.showHabitatMsg("Habitat rimosso con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showHabitatMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }

    public static void handleAggiungiFamiglia(final GestioneView view) {
        String nome = view.getTxtFamigliaNome().getText();
        String desc = view.getTxtFamigliaDesc().getText();
        if (nome == null || nome.trim().isEmpty()) return;
        
        try {
            FamigliaSpecieDao dao = new FamigliaSpecieDao();
            FamigliaSpecie entity = new FamigliaSpecie(nome.trim(), desc != null && !desc.trim().isEmpty() ? desc.trim() : null);
            dao.insert(entity);
            view.getTxtFamigliaNome().clear();
            view.getTxtFamigliaDesc().clear();
            populateFamigliaSpecie(view);
            view.showFamigliaMsg("Famiglia aggiunta con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showFamigliaMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviFamiglia(final GestioneView view) {
        FamigliaSpecieRow row = view.getTableFamigliaSpecie().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new FamigliaSpecieDao().delete(Integer.parseInt(row.getIdFamiglia()));
                populateFamigliaSpecie(view);
                view.showFamigliaMsg("Famiglia rimossa con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showFamigliaMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }
}
