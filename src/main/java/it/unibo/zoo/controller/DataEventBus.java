package it.unibo.zoo.controller;

import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event Bus centralizzato per la sincronizzazione reattiva dei dati.
 * <p>
 * Implementa il pattern Publish/Subscribe: ogni controller pubblica un evento
 * quando modifica un dato, e tutti i subscriber registrati vengono notificati
 * automaticamente (incluse le dipendenze transitive tra tipi di dato).
 * <p>
 * L'esecuzione dei callback è sempre garantita sul JavaFX Application Thread.
 */
public final class DataEventBus {

    /**
     * Enumerazione di tutti i tipi di dato gestiti nell'applicazione.
     */
    public enum DataType {
        HABITAT,
        STATO_ESISTENZA,
        FAMIGLIA_SPECIE,
        SPECIE,
        DIETA,
        ANIMALE,
        AREA,
        TIPO_AREA,
        RECINTO,
        TIPO_RECINTO,
        DIPENDENTE,
        MANSIONE,
        TURNO,
        VISITA,
        ORDINE,
        SPESA,
        TRANSAZIONE,
        SALDO,
        TIPO_BIGLIETTO,
        FORNITORE
    }

    /* ── Singleton ───────────────────────────────────────── */

    private static final DataEventBus INSTANCE = new DataEventBus();

    public static DataEventBus getInstance() {
        return INSTANCE;
    }

    private DataEventBus() {
        initDependencies();
    }

    /* ── Stato interno ───────────────────────────────────── */

    /** Mappa: DataType → lista di callback registrati */
    private final Map<DataType, List<Runnable>> subscribers = new ConcurrentHashMap<>();

    /** Mappa delle dipendenze: quando DataType X cambia, anche Y, Z, ... devono essere notificati */
    private final Map<DataType, Set<DataType>> dependencies = new EnumMap<>(DataType.class);



    /* ── Inizializzazione dipendenze ─────────────────────── */

    private void initDependencies() {
        // Habitat → Specie (combo habitat nella creazione specie)
        addDependency(DataType.HABITAT, DataType.SPECIE);

        // StatoEsistenza → Specie (combo stato nella creazione specie)
        addDependency(DataType.STATO_ESISTENZA, DataType.SPECIE);

        // FamigliaSpecie → Specie (combo famiglia nella creazione specie)
        addDependency(DataType.FAMIGLIA_SPECIE, DataType.SPECIE);

        // Specie → Animale (combo specie nella creazione animale), Dieta (combo specie nella dieta)
        addDependency(DataType.SPECIE, DataType.ANIMALE);
        addDependency(DataType.SPECIE, DataType.DIETA);

        // TipoArea → Area (combo tipo nella creazione area)
        addDependency(DataType.TIPO_AREA, DataType.AREA);

        // Area → Recinto (combo area nella creazione recinto), Turno (combo area nel turno)
        addDependency(DataType.AREA, DataType.RECINTO);
        addDependency(DataType.AREA, DataType.TURNO);

        // TipoRecinto → Recinto (combo tipo nella creazione recinto)
        addDependency(DataType.TIPO_RECINTO, DataType.RECINTO);

        // Recinto → Animale (combo recinto nella creazione animale)
        addDependency(DataType.RECINTO, DataType.ANIMALE);

        // Animale → Visita (combo animale nella visita), Recinto (stat. recinto più popolato)
        addDependency(DataType.ANIMALE, DataType.VISITA);
        addDependency(DataType.ANIMALE, DataType.RECINTO);

        // Dipendente → Visita (combo vet.), Turno (combo dipendente)
        addDependency(DataType.DIPENDENTE, DataType.VISITA);
        addDependency(DataType.DIPENDENTE, DataType.TURNO);

        // Mansione → Dipendente (combo mansione)
        addDependency(DataType.MANSIONE, DataType.DIPENDENTE);

        // Transazione → Saldo, Spesa
        addDependency(DataType.TRANSAZIONE, DataType.SALDO);
        addDependency(DataType.TRANSAZIONE, DataType.SPESA);
    }

    private void addDependency(DataType source, DataType dependent) {
        dependencies.computeIfAbsent(source, k -> EnumSet.noneOf(DataType.class)).add(dependent);
    }

    /* ── API pubblica ────────────────────────────────────── */

    /**
     * Registra un callback da eseguire quando il tipo di dato specificato viene modificato.
     *
     * @param type     il tipo di dato da osservare
     * @param callback il codice da eseguire (sarà eseguito sul JavaFX Application Thread)
     */
    public void subscribe(DataType type, Runnable callback) {
        subscribers.computeIfAbsent(type, k -> Collections.synchronizedList(new ArrayList<>())).add(callback);
    }

    /**
     * Pubblica un evento di modifica per uno o più tipi di dato.
     * Notifica tutti i subscriber diretti e propaga le dipendenze.
     *
     * @param types i tipi di dato che sono stati modificati
     */
    public void publish(DataType... types) {
        // Raccogli tutti i tipi da notificare (diretti + dipendenze transitive)
        Set<DataType> toNotify = EnumSet.noneOf(DataType.class);
        for (DataType type : types) {
            collectAll(type, toNotify);
        }

        // Esegui le notifiche sul JavaFX Application Thread
        Runnable notifyAll = () -> {
            for (DataType type : toNotify) {
                List<Runnable> callbacks = subscribers.get(type);
                if (callbacks != null) {
                    // Itera su una copia per evitare ConcurrentModificationException
                    List<Runnable> snapshot;
                    synchronized (callbacks) {
                        snapshot = new ArrayList<>(callbacks);
                    }
                    for (Runnable cb : snapshot) {
                        try {
                            cb.run();
                        } catch (Exception e) {
                            System.err.println("[DataEventBus] Errore nel callback per " + type + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        if (Platform.isFxApplicationThread()) {
            notifyAll.run();
        } else {
            Platform.runLater(notifyAll);
        }
    }

    /**
     * Raccoglie ricorsivamente il tipo specificato e tutte le sue dipendenze transitive.
     */
    private void collectAll(DataType type, Set<DataType> collected) {
        if (!collected.add(type)) {
            return; // già visitato, previene loop circolari
        }
        Set<DataType> deps = dependencies.get(type);
        if (deps != null) {
            for (DataType dep : deps) {
                collectAll(dep, collected);
            }
        }
    }

    /**
     * Rimuove tutte le sottoscrizioni. Utile per cleanup quando la vista viene distrutta.
     */
    public void unsubscribeAll() {
        subscribers.clear();
    }
}
