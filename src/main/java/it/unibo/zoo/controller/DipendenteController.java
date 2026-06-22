package it.unibo.zoo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.Mansione;
import it.unibo.zoo.model.jdbc.entityDao.DipendenteDao;
import it.unibo.zoo.model.jdbc.entityDao.MansioneDao;
import it.unibo.zoo.view.GestioneView;

public class DipendenteController {
    
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void populatePersonale(final GestioneView view) {
        final List<Dipendente> dipendenti = new DipendenteDao().findAll();
        final Map<Integer, Mansione> mansMap = new MansioneDao().findAll().stream()
                .collect(Collectors.toMap(Mansione::getIdMansione, m -> m));

        final List<GestioneView.DipendenteRow> rows = new ArrayList<>();
        for (final Dipendente d : dipendenti) {
            final Mansione m = mansMap.get(d.getIdMansione());
            rows.add(new GestioneView.DipendenteRow(
                    String.valueOf(d.getIdDipendente()),
                    d.getCodiceFiscale(),
                    d.getNome(),
                    d.getCognome(),
                    d.getDataNascita() != null ? d.getDataNascita().format(DATE_FMT) : "—",
                    m != null ? m.getNome() : "—"
            ));
        }
        view.setPersonale(rows);

        final List<GestioneView.TipoMansioneRow> mansioneRows = new ArrayList<>();
        view.getComboDipMansione().getItems().clear();
        for (final Mansione m : new MansioneDao().findAll()) {
            view.getComboDipMansione().getItems().add(m.getNome());
            mansioneRows.add(new GestioneView.TipoMansioneRow(
                    String.valueOf(m.getIdMansione()),
                    m.getNome(),
                    m.getDescrizione() != null ? m.getDescrizione() : ""
            ));
        }
        view.getTableTipoMansione().setItems(javafx.collections.FXCollections.observableArrayList(mansioneRows));
    }

    public static void handleSalvaDipendente(final GestioneView view, Integer editingId) {
        try {
            String cf = view.getTxtDipCf().getText();
            String nome = view.getTxtDipNome().getText();
            String cognome = view.getTxtDipCognome().getText();
            LocalDate dataNascita = view.getDateDipNascita().getValue();
            String mansioneNome = view.getComboDipMansione().getValue();
            
            if(cf == null || cf.isEmpty() || nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() || dataNascita == null || mansioneNome == null) {
                view.showDipendenteMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idMans = 0;
            for (Mansione m : new MansioneDao().findAll()) {
                if (m.getNome().equals(mansioneNome)) {
                    idMans = m.getIdMansione();
                    break;
                }
            }
            
            DipendenteDao dao = new DipendenteDao();
            if (editingId == null) {
                Dipendente d = new Dipendente(0, cf, nome, cognome, dataNascita, LocalDate.now(), idMans);
                dao.insert(d);
                view.showDipendenteMsg("Dipendente assunto con successo!", true);
            } else {
                Dipendente old = dao.findById(editingId).orElse(null);
                LocalDate assunzione = old != null ? old.getDataAssunzione() : LocalDate.now();
                Dipendente d = new Dipendente(editingId, cf, nome, cognome, dataNascita, assunzione, idMans);
                dao.update(d);
                view.showDipendenteMsg("Dipendente modificato con successo!", true);
            }
            
            view.setPanelNuovoDipendenteVisible(false);
            DipendenteController.populatePersonale(view);
        } catch(Exception e) {
            view.showDipendenteMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleAggiungiTipoMansione(final GestioneView view) {
        try {
            String nome = view.getTxtTipoMansioneNome().getText();
            String desc = view.getTxtTipoMansioneDesc().getText();
            if (nome == null || nome.trim().isEmpty()) {
                view.showTipoMansioneMsg("Il nome è obbligatorio.", false);
                return;
            }
            Mansione m = new Mansione(nome.trim(), desc != null ? desc.trim() : "");
            new MansioneDao().insert(m);
            view.showTipoMansioneMsg("Tipo mansione aggiunto!", true);
            view.getTxtTipoMansioneNome().clear();
            view.getTxtTipoMansioneDesc().clear();
            populatePersonale(view);
        } catch(Exception e) {
            view.showTipoMansioneMsg("Errore: " + e.getMessage(), false);
        }
    }

    public static void handleRimuoviTipoMansione(final GestioneView view) {
        GestioneView.TipoMansioneRow sel = view.getTableTipoMansione().getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                int id = Integer.parseInt(sel.getIdTipoMansione());
                new MansioneDao().delete(id);
                view.showTipoMansioneMsg("Tipo mansione rimosso!", true);
                populatePersonale(view);
            } catch (Exception e) {
                view.showTipoMansioneMsg("Errore: " + e.getMessage(), false);
            }
        }
    }
}
