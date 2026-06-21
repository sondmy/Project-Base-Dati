package it.unibo.zoo.controller;

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
        for (final TipoCibo c : new TipoCiboDao().findAll()) {
            view.getComboTipoCibo().getItems().add(c.getNome());
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
            int idTipoCibo = 0;
            for (TipoCibo c : new TipoCiboDao().findAll()) {
                if (c.getNome().equals(tipoCibo)) {
                    idTipoCibo = c.getIdTipoCibo();
                    break;
                }
            }
            
            double costoTotale = qta * 2.50; // Prezzo fittizio
            Transazione t = new Transazione(0, "U", costoTotale, LocalDate.now(), "Acquisto cibo: " + tipoCibo + " da " + fornitore, 2, 1, idFornitore, null);
            t = new TransazioneDao().insert(t);
            
            OrdineGiornalieroCibo ordine = new OrdineGiornalieroCibo(0, LocalDate.now(), (double)qta, idFornitore, idTipoCibo, t.getIdTransazione());
            new OrdineGiornalieroCiboDao().insert(ordine);
            
            SaldoController.populateSaldo(view);
            OrdiniController.populateOrdini(view);
            view.showOrdineMsg("Ordine salvato! Costo: €" + String.format("%.2f", costoTotale), true);
        } catch(Exception e) {
            view.showOrdineMsg("Errore db: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }
}
