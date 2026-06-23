package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.StatoEsistenza;
import it.unibo.zoo.model.entity.Habitat;
import it.unibo.zoo.model.entity.FamigliaSpecie;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.jdbc.entityDao.StatoEsistenzaDao;
import it.unibo.zoo.model.jdbc.entityDao.HabitatDao;
import it.unibo.zoo.model.jdbc.entityDao.FamigliaSpecieDao;
import it.unibo.zoo.model.jdbc.entityDao.SpecieDao;
import it.unibo.zoo.view.GestioneView;
import it.unibo.zoo.view.GestioneView.StatoEsistenzaRow;
import it.unibo.zoo.view.GestioneView.HabitatRow;
import it.unibo.zoo.view.GestioneView.FamigliaSpecieRow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassificazioneController {

    public static void populateClassificazione(final GestioneView view) {
        populateStatoEsistenza(view);
        populateHabitat(view);
        populateFamigliaSpecie(view);
        populateSpecie(view);
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

    private static void populateSpecie(final GestioneView view) {
        SpecieDao dao = new SpecieDao();
        List<Specie> list = dao.findAll();
        Map<Integer, String> habMap = new HabitatDao().findAll().stream().collect(Collectors.toMap(Habitat::getIdHabitat, Habitat::getNome));
        Map<Integer, String> staMap = new StatoEsistenzaDao().findAll().stream().collect(Collectors.toMap(StatoEsistenza::getIdStato, StatoEsistenza::getNome));
        Map<Integer, String> famMap = new FamigliaSpecieDao().findAll().stream().collect(Collectors.toMap(FamigliaSpecie::getIdFamigliaSpecie, FamigliaSpecie::getNome));
        
        List<GestioneView.SpecieRow> rows = list.stream()
                .map(s -> new GestioneView.SpecieRow(
                        String.valueOf(s.getIdSpecie()),
                        s.getNomeScentifico(),
                        s.getNomeComune(),
                        s.getIdHabitat() != null ? habMap.getOrDefault(s.getIdHabitat(), "—") : "—",
                        s.getIdStato() != null ? staMap.getOrDefault(s.getIdStato(), "—") : "—",
                        s.getIdFamigliaSpecie() != null ? famMap.getOrDefault(s.getIdFamigliaSpecie(), "—") : "—"
                )).collect(Collectors.toList());
        view.setSpecie(rows);
        
        view.getComboSpecieHabitat().getItems().clear();
        view.getComboSpecieHabitat().getItems().addAll(habMap.entrySet().stream().map(e -> e.getKey() + " - " + e.getValue()).collect(Collectors.toList()));
        view.getComboSpecieStato().getItems().clear();
        view.getComboSpecieStato().getItems().addAll(staMap.entrySet().stream().map(e -> e.getKey() + " - " + e.getValue()).collect(Collectors.toList()));
        view.getComboSpecieFamiglia().getItems().clear();
        view.getComboSpecieFamiglia().getItems().addAll(famMap.entrySet().stream().map(e -> e.getKey() + " - " + e.getValue()).collect(Collectors.toList()));
    }

    public static void handleAggiungiSpecie(final GestioneView view) {
        String sci = view.getTxtSpecieNomeSci().getText();
        String com = view.getTxtSpecieNomeCom().getText();
        if (sci == null || sci.trim().isEmpty() || com == null || com.trim().isEmpty()) return;
        
        String habStr = view.getComboSpecieHabitat().getValue();
        Integer idHab = habStr != null && !habStr.trim().isEmpty() ? Integer.parseInt(habStr.split(" - ")[0]) : null;
        
        String staStr = view.getComboSpecieStato().getValue();
        Integer idSta = staStr != null && !staStr.trim().isEmpty() ? Integer.parseInt(staStr.split(" - ")[0]) : null;
        
        String famStr = view.getComboSpecieFamiglia().getValue();
        Integer idFam = famStr != null && !famStr.trim().isEmpty() ? Integer.parseInt(famStr.split(" - ")[0]) : null;
        
        try {
            SpecieDao dao = new SpecieDao();
            Specie entity = new Specie(sci.trim(), com.trim(), idHab, idSta, idFam);
            dao.insert(entity);
            view.getTxtSpecieNomeSci().clear();
            view.getTxtSpecieNomeCom().clear();
            view.getComboSpecieHabitat().setValue(null);
            view.getComboSpecieStato().setValue(null);
            view.getComboSpecieFamiglia().setValue(null);
            populateSpecie(view);
            view.showSpecieMsg("Specie aggiunta con successo.", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.showSpecieMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviSpecie(final GestioneView view) {
        GestioneView.SpecieRow row = view.getTableSpecie().getSelectionModel().getSelectedItem();
        if (row != null) {
            try {
                new SpecieDao().delete(Integer.parseInt(row.getIdSpecie()));
                populateSpecie(view);
                view.showSpecieMsg("Specie rimossa con successo.", true);
            } catch (Exception e) {
                e.printStackTrace();
                view.showSpecieMsg("Impossibile rimuovere (in uso).", false);
            }
        }
    }
}
