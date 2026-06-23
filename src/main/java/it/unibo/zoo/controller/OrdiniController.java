package it.unibo.zoo.controller;

import it.unibo.zoo.controller.DataEventBus.DataType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.zoo.model.entity.Fornitore;
import it.unibo.zoo.model.entity.OrdineGiornalieroCibo;
import it.unibo.zoo.model.entity.TipoCibo;
import it.unibo.zoo.model.entity.Transazione;
import it.unibo.zoo.model.jdbc.entityDao.FornitoreDao;
import it.unibo.zoo.model.jdbc.entityDao.OrdineGiornalieroCiboDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoCiboDao;
import it.unibo.zoo.model.jdbc.entityDao.TransazioneDao;
import it.unibo.zoo.view.GestioneView;

public class OrdiniController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void populateFabbisogno(final GestioneView view) {
        Map<String, Double> fabbisogno = new OrdineGiornalieroCiboDao().calcolaFabbisognoGiornaliero();
        List<GestioneView.FabbisognoRow> rows = fabbisogno.entrySet().stream()
                .map(e -> new GestioneView.FabbisognoRow(e.getKey(), String.format(java.util.Locale.US, "%.2f", e.getValue())))
                .collect(Collectors.toList());
        view.getTableFabbisogno().getItems().setAll(rows);
    }

    public static void populateOrdini(final GestioneView view) {
        final List<OrdineGiornalieroCibo> ordini = new OrdineGiornalieroCiboDao().findAll();
        final Map<Integer, Fornitore> fornMap = new FornitoreDao().findAll().stream()
                .collect(Collectors.toMap(Fornitore::getIdFornitore, f -> f));
        final Map<Integer, TipoCibo> ciboMap = new TipoCiboDao().findAll().stream()
                .collect(Collectors.toMap(TipoCibo::getIdTipoCibo, c -> c));

        final List<GestioneView.OrdineRow> rows = new ArrayList<>();
        for (final OrdineGiornalieroCibo o : ordini) {
            final Fornitore f = fornMap.get(o.getIdFornitore());
            final TipoCibo c = ciboMap.get(o.getIdTipoCibo());
            rows.add(new GestioneView.OrdineRow(
                    o.getDataOrdine().format(DATE_FMT),
                    f != null ? f.getNomeAzienda() : "—",
                    c != null ? c.getNome() : "—",
                    String.format("%.1f", o.getQuantitaKg())
            ));
        }
        view.setOrdini(rows);

        view.getComboFornitore().getItems().clear();
        for (final Fornitore f : new FornitoreDao().findAll()) {
            view.getComboFornitore().getItems().add(f.getNomeAzienda());
        }
        view.getComboTipoCibo().getItems().clear();
    }

    public static void updateCibiDisponibili(final GestioneView view, final String fornitoreNome) {
        view.getComboTipoCibo().getItems().clear();
        if (fornitoreNome == null) return;
        
        try {
            Fornitore f = new FornitoreDao().findAll().stream()
                .filter(forn -> forn.getNomeAzienda().equals(fornitoreNome))
                .findFirst().orElse(null);
                
            if (f != null) {
                List<it.unibo.zoo.model.entity.FornitoreCibo> fcList = new it.unibo.zoo.model.jdbc.entityDao.FornitoreCiboDao().findByFornitore(f.getIdFornitore());
                Map<Integer, TipoCibo> ciboMap = new TipoCiboDao().findAll().stream()
                    .collect(Collectors.toMap(TipoCibo::getIdTipoCibo, c -> c));
                    
                for (it.unibo.zoo.model.entity.FornitoreCibo fc : fcList) {
                    TipoCibo c = ciboMap.get(fc.getIdTipoCibo());
                    if (c != null) {
                        view.getComboTipoCibo().getItems().add(c.getNome() + " - €" + String.format("%.2f", fc.getPrezzo()) + "/kg");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleSalvaOrdine(final GestioneView view) {
        final String fornitore = view.getComboFornitore().getValue();
        final String tipoCibo = view.getComboTipoCibo().getValue();
        final int qta = view.getSpinnerQta().getValue();

        if (fornitore == null || tipoCibo == null) {
            view.showOrdineMsg("Seleziona fornitore e tipo cibo.", false);
            return;
        }
        
        try {
            int idFornitore = 0;
            for (Fornitore f : new FornitoreDao().findAll()) {
                if (f.getNomeAzienda().equals(fornitore)) {
                    idFornitore = f.getIdFornitore();
                    break;
                }
            }
            
            String nomeCiboSelezionato = tipoCibo.split(" - €")[0];
            String prezzoStr = tipoCibo.split(" - €")[1].replace("/kg", "").replace(",", ".");
            double prezzoAlKg = Double.parseDouble(prezzoStr);
            
            int idTipoCibo = 0;
            for (TipoCibo c : new TipoCiboDao().findAll()) {
                if (c.getNome().equals(nomeCiboSelezionato)) {
                    idTipoCibo = c.getIdTipoCibo();
                    break;
                }
            }
            
            double costoTotale = qta * prezzoAlKg;
            Transazione t = new Transazione(0, "U", costoTotale, LocalDate.now(), "Acquisto cibo: " + nomeCiboSelezionato + " da " + fornitore, 2, 1, idFornitore, null);
            t = new TransazioneDao().insert(t);
            
            OrdineGiornalieroCibo ordine = new OrdineGiornalieroCibo(0, LocalDate.now(), (double)qta, idFornitore, idTipoCibo, t.getIdTransazione());
            new OrdineGiornalieroCiboDao().insert(ordine);
            
            DataEventBus.getInstance().publish(DataType.ORDINE, DataType.TRANSAZIONE);
            view.showOrdineMsg("Ordine salvato! Costo: €" + String.format("%.2f", costoTotale), true);
        } catch(Exception e) {
            view.showOrdineMsg("Errore db: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }
}
