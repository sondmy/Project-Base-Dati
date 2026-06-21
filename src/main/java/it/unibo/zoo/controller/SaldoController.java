package it.unibo.zoo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.chart.XYChart;
import it.unibo.zoo.model.entity.DettaglioScontrino;
import it.unibo.zoo.model.entity.Scontrino;
import it.unibo.zoo.model.entity.TipoBiglietto;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.jdbc.entityDao.DettaglioScontrinoDao;
import it.unibo.zoo.model.jdbc.entityDao.ScontrinoDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoBigliettoDao;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.view.GestioneView;

public class SaldoController {
    
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void populateSaldo(final GestioneView view) {
        final List<Transazione> transazioni = new TransazioneDao().findAll();
        final Map<Integer, it.unibo.zoo.model.entity.CategoriaTransazione> catMap = new it.unibo.zoo.model.jdbc.entityDao.CategoriaTransazioneDao().findAll().stream()
                .collect(Collectors.toMap(it.unibo.zoo.model.entity.CategoriaTransazione::getIdCategoria, c -> c));

        double entrate = 0;
        double uscite = 0;

        for (final Transazione t : transazioni) {
            if ("E".equalsIgnoreCase(t.getTipo())) {
                entrate += t.getImporto();
            } else {
                uscite += t.getImporto();
            }
        }

        view.setEntrate(String.format("€%.2f", entrate));
        view.setUscite(String.format("€%.2f", uscite));
        final double saldo = entrate - uscite;
        view.setSaldo(String.format("€%.2f", saldo));

        // Ultime 5 transazioni (le ultime nella lista)
        final int start = Math.max(0, transazioni.size() - 5);
        final List<GestioneView.TransazioneRow> rows = new ArrayList<>();
        for (int i = transazioni.size() - 1; i >= start; i--) {
            final Transazione t = transazioni.get(i);
            final it.unibo.zoo.model.entity.CategoriaTransazione cat = catMap.get(t.getIdCategoria());
            rows.add(new GestioneView.TransazioneRow(
                    t.getData().format(DATE_FMT),
                    t.getTipo(),
                    String.format("€%.2f", t.getImporto()),
                    cat != null ? cat.getNome() : "—",
                    t.getDescrizione() != null ? t.getDescrizione() : ""
            ));
        }
        view.setTransazioni(rows);
    }

    public static void populateStatistiche(final GestioneView view) {
        String periodo = view.getComboStatPeriodo().getValue();
        
        List<Scontrino> scontrini = new ScontrinoDao().findAll();
        List<DettaglioScontrino> dettagli = new DettaglioScontrinoDao().findAll();
        List<TipoBiglietto> tipi = new TipoBigliettoDao().findAll();
        
        Map<Integer, LocalDate> scontrinoDataMap = scontrini.stream()
                .collect(Collectors.toMap(Scontrino::getIdScontrino, Scontrino::getDataAcquisto));
        Map<Integer, String> tipoNomeMap = tipi.stream()
                .collect(Collectors.toMap(TipoBiglietto::getIdBiglietto, TipoBiglietto::getNome));

        Map<String, Integer> venditePerPeriodo = new java.util.TreeMap<>();
        Map<Integer, Integer> venditePerTipo = new java.util.HashMap<>();
        
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("yyyy-MM");
        
        int totalTickets = 0;
        
        for (DettaglioScontrino d : dettagli) {
            LocalDate data = scontrinoDataMap.get(d.getIdScontrino());
            if (data == null) continue;
            
            String periodKey;
            if ("Mese".equals(periodo)) {
                periodKey = data.format(monthFmt);
            } else if ("Settimana".equals(periodo)) {
                int week = data.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                int year = data.get(IsoFields.WEEK_BASED_YEAR);
                periodKey = year + "-W" + String.format("%02d", week);
            } else {
                periodKey = data.format(DATE_FMT);
            }
            
            venditePerPeriodo.put(periodKey, venditePerPeriodo.getOrDefault(periodKey, 0) + d.getQuantita());
            venditePerTipo.put(d.getIdBiglietto(), venditePerTipo.getOrDefault(d.getIdBiglietto(), 0) + d.getQuantita());
            totalTickets += d.getQuantita();
        }
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : venditePerPeriodo.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        view.getChartStatBiglietti().getData().clear();
        view.getChartStatBiglietti().getData().add(series);
        
        int topId = -1;
        int max = -1;
        for (Map.Entry<Integer, Integer> entry : venditePerTipo.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topId = entry.getKey();
            }
        }
        
        if (topId != -1) {
            view.getLblStatTopBiglietto().setText(tipoNomeMap.getOrDefault(topId, "-"));
        } else {
            view.getLblStatTopBiglietto().setText("-");
        }
        
        view.getLblStatTotBiglietti().setText(String.valueOf(totalTickets));
    }

}
