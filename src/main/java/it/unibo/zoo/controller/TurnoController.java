package it.unibo.zoo.controller;

import it.unibo.zoo.controller.DataEventBus.DataType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;

import it.unibo.zoo.model.entity.Area;
import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.Mansione;
import it.unibo.zoo.model.entity.Turno;
import it.unibo.zoo.model.jdbc.entityDao.AreaDao;
import it.unibo.zoo.model.jdbc.entityDao.DipendenteDao;
import it.unibo.zoo.model.jdbc.entityDao.MansioneDao;
import it.unibo.zoo.model.jdbc.entityDao.TurnoDao;
import it.unibo.zoo.view.GestioneView;

public class TurnoController {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static void populateTurni(final GestioneView view) {
        String filterMode = view.getComboFiltroTurni().getValue();
        LocalDate filterDate = view.getDateFiltroTurni().getValue();
        
        List<Turno> turni;
        if ("Giorno".equals(filterMode) && filterDate != null) {
            turni = new TurnoDao().findByData(filterDate);
        } else if ("Settimana".equals(filterMode) && filterDate != null) {
            // Calcola da Lunedì a Domenica per la settimana della data selezionata
            LocalDate start = filterDate.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            LocalDate end = filterDate.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
            turni = new TurnoDao().findByDateRange(start, end);
        } else {
            turni = new TurnoDao().findAll();
        }
        final Map<Integer, Dipendente> dipMap = new DipendenteDao().findAll().stream()
                .collect(Collectors.toMap(Dipendente::getIdDipendente, d -> d));
        final Map<Integer, Mansione> mansMap = new MansioneDao().findAll().stream()
                .collect(Collectors.toMap(Mansione::getIdMansione, m -> m));
        final Map<Integer, Area> areaMap = new AreaDao().findAll().stream()
                .collect(Collectors.toMap(Area::getIdArea, a -> a));

        final List<GestioneView.TurnoRow> rows = new ArrayList<>();
        Map<Integer, Integer> turniCount = new java.util.HashMap<>();
        
        for (final Turno t : turni) {
            final Dipendente d = dipMap.get(t.getIdDipendente());
            if (d != null) {
                turniCount.put(d.getIdDipendente(), turniCount.getOrDefault(d.getIdDipendente(), 0) + 1);
            }
            
            final String nomeDip = d != null ? d.getNome() + " " + d.getCognome() : "—";
            final String mansione;
            if (d != null) {
                final Mansione m = mansMap.get(d.getIdMansione());
                mansione = m != null ? m.getNome() : "—";
            } else {
                mansione = "—";
            }
            final Area area = areaMap.get(t.getIdArea());
            final String nomeArea = area != null ? area.getNome() : "—";
            
            final String giornoStr = t.getDataGiorno() != null ? t.getDataGiorno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "—";

            rows.add(new GestioneView.TurnoRow(
                    String.valueOf(t.getIdTurno()),
                    String.valueOf(t.getIdDipendente()),
                    String.valueOf(t.getIdArea()),
                    giornoStr, nomeDip, mansione, nomeArea,
                    t.getOraInizio().format(TIME_FMT),
                    t.getOraFine().format(TIME_FMT)
            ));
        }
        view.setTurni(rows);
        
        List<Map.Entry<Integer, Integer>> topEntries = new ArrayList<>(turniCount.entrySet());
        topEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        List<String> topList = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : topEntries) {
            Dipendente d = dipMap.get(entry.getKey());
            if (d != null) {
                topList.add(d.getNome() + " " + d.getCognome() + " (" + entry.getValue() + " turni)");
                count++;
                if (count >= 5) break;
            }
        }
        view.getListTopTurni().setItems(FXCollections.observableArrayList(topList));
        
        view.getComboTurnoDip().getItems().clear();
        for(Dipendente d : new DipendenteDao().findAll()) {
            view.getComboTurnoDip().getItems().add(d.getIdDipendente() + " - " + d.getNome() + " " + d.getCognome());
        }
        view.getComboTurnoArea().getItems().clear();
        for(Area a : new AreaDao().findAll()) {
            view.getComboTurnoArea().getItems().add(a.getIdArea() + " - " + a.getNome());
        }
    }

    public static void handleEliminaTurno(final GestioneView view) {
        GestioneView.TurnoRow sel = view.getTableTurni().getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                new TurnoDao().delete(Integer.parseInt(sel.getIdTurno()));
                DataEventBus.getInstance().publish(DataType.TURNO);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void handleSalvaTurno(final GestioneView view) {
        try {
            String dip = view.getComboTurnoDip().getValue();
            String area = view.getComboTurnoArea().getValue();
            LocalDate giorno = view.getDateTurnoGiorno().getValue();
            String inizio = view.getComboTurnoOraInizio().getValue();
            String fine = view.getComboTurnoOraFine().getValue();
            
            if(dip == null || area == null || giorno == null || inizio == null || fine == null) {
                view.showTurnoMsg("Tutti i campi sono obbligatori.", false);
                return;
            }
            
            int idDip = Integer.parseInt(dip.split(" - ")[0]);
            int idArea = Integer.parseInt(area.split(" - ")[0]);
            
            LocalDateTime dtInizio = giorno.atTime(LocalTime.parse(inizio));
            LocalDateTime dtFine = giorno.atTime(LocalTime.parse(fine));
            
            if (dtFine.isBefore(dtInizio) || dtFine.equals(dtInizio)) {
                view.showTurnoMsg("L'ora di fine deve essere successiva all'ora di inizio.", false);
                return;
            }
            
            Turno t = new Turno(0, giorno, dtInizio, dtFine, idDip, idArea);
            new TurnoDao().insert(t);
            
            view.showTurnoMsg("Turno salvato con successo!", true);
            view.setPanelNuovoTurnoVisible(false);
            DataEventBus.getInstance().publish(DataType.TURNO);
        } catch(Exception e) {
            view.showTurnoMsg("Errore: " + e.getMessage(), false);
        }
    }

}
