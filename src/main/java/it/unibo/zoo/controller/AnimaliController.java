package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.Habitat;
import it.unibo.zoo.model.entity.Recinto;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.StatoEsistenza;
import it.unibo.zoo.model.entity.StoricoCollocazione;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.model.jdbc.entityDao.HabitatDao;
import it.unibo.zoo.model.jdbc.entityDao.RecintoDao;
import it.unibo.zoo.model.jdbc.entityDao.SpecieDao;
import it.unibo.zoo.model.jdbc.entityDao.StatoEsistenzaDao;
import it.unibo.zoo.model.jdbc.entityDao.StoricoCollocazioneDao;
import it.unibo.zoo.view.AnimaliView;
import it.unibo.zoo.view.GestioneView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final Map<Integer, Integer> animaleRecintoMap; // idAnimale -> idRecinto (collocazione attuale)

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public AnimaliController(final AnimaliView view) {
        this.view = view;
        this.animali = new AnimaleDao().findAll();
        this.specieMap = new SpecieDao().findAll().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));
        this.habitatMap = new HabitatDao().findAll().stream()
                .collect(Collectors.toMap(Habitat::getIdHabitat, h -> h));
        this.statoMap = new StatoEsistenzaDao().findAll().stream()
                .collect(Collectors.toMap(StatoEsistenza::getIdStato, s -> s));
        this.recintoMap = new RecintoDao().findAll().stream()
                .collect(Collectors.toMap(Recinto::getIdRecinto, r -> r));
        // Costruisci mappa animale -> recinto corrente (collocazione senza data_fine)
        this.animaleRecintoMap = new StoricoCollocazioneDao().findAll().stream()
                .filter(sc -> sc.getDataFine() == null)
                .collect(Collectors.toMap(StoricoCollocazione::getIdAnimale, StoricoCollocazione::getIdRecinto,
                        (a, b) -> b));
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
                final StatoEsistenza se = statoMap.get(sp.getIdStato());
                stato = se != null ? se.getNome() : "—";
            } else {
                stato = "—";
            }
            final String sesso = String.valueOf(a.getSesso());
            final Integer idRecinto = animaleRecintoMap.get(a.getIdAnimale());
            final Recinto rec = idRecinto != null ? recintoMap.get(idRecinto) : null;
            final String recinto = rec != null ? "R" + rec.getIdRecinto() : "—";

            rows.add(new AnimaliView.AnimaleRow(a.getNome(), nomeSpecie, sesso, habitat, stato, recinto));
        }
        return rows;
    }

    public static void populateAnimali(final GestioneView view) {
        final List<Animale> animali = new AnimaleDao().findAll();
        final java.util.Map<Integer, Specie> specieMap = new SpecieDao().findAll().stream()
                .collect(Collectors.toMap(Specie::getIdSpecie, s -> s));

        final List<GestioneView.AnimaleRow> rows = new ArrayList<>();
        for (final Animale a : animali) {
            final Specie s = specieMap.get(a.getIdSpecie());
            rows.add(new GestioneView.AnimaleRow(
                    String.valueOf(a.getIdAnimale()),
                    a.getNome(),
                    String.valueOf(a.getSesso()),
                    a.getDataNascita() != null ? a.getDataNascita().format(DATE_FMT) : "-",
                    a.getDataArrivo() != null ? a.getDataArrivo().format(DATE_FMT) : "-",
                    a.getDataUscita() != null ? a.getDataUscita().format(DATE_FMT) : "-",
                    s != null ? s.getNomeComune() : "-",
                    a.isVivo() ? "Vivo" : "Morto"
            ));
        }
        view.setAnimali(rows);

        view.getComboAnimaleSpecie().getItems().clear();
        for (final Specie s : new SpecieDao().findAll()) {
            view.getComboAnimaleSpecie().getItems().add(s.getIdSpecie() + " - " + s.getNomeComune());
        }
    }
    public static void handleSalvaAnimale(final GestioneView view, final Integer editingAnimaleId) {
        try {
            String nome = view.getTxtAnimaleNome().getText();
            String sessoStr = view.getComboAnimaleSesso().getValue();
            LocalDate dataNascita = view.getDateAnimaleNascita().getValue();
            LocalDate dataArrivo = view.getDateAnimaleArrivo().getValue();
            LocalDate dataUscita = view.getDateAnimaleUscita().getValue();
            String vivoStr = view.getComboAnimaleVivo().getValue();
            String specieStr = view.getComboAnimaleSpecie().getValue();
            
            if(nome == null || nome.isEmpty() || sessoStr == null || specieStr == null) {
                view.showAnimaleMsg("Nome, Sesso e Specie sono obbligatori.", false);
                return;
            }
            
            int idSpecie = Integer.parseInt(specieStr.split(" - ")[0]);
            char sesso = sessoStr.charAt(0);
            boolean vivo = "Vivo".equals(vivoStr);
            
            if (dataArrivo == null) {
                dataArrivo = LocalDate.now();
            }
            
            AnimaleDao dao = new AnimaleDao();
            if (editingAnimaleId == null) {
                Animale a = new Animale(0, nome, sesso, vivo, dataNascita, dataArrivo, dataUscita, idSpecie);
                dao.insert(a);
                view.showAnimaleMsg("Animale aggiunto con successo!", true);
            } else {
                Animale a = new Animale(editingAnimaleId, nome, sesso, vivo, dataNascita, dataArrivo, dataUscita, idSpecie);
                dao.update(a);
                view.showAnimaleMsg("Animale modificato con successo!", true);
            }
            
            view.setPanelNuovoAnimaleVisible(false);
            AnimaliController.populateAnimali(view);
        } catch(Exception e) {
            view.showAnimaleMsg("Errore: " + e.getMessage(), false);
        }
    }
}
