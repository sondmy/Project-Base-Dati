package it.unibo.zoo;

import java.sql.Connection;
import java.time.LocalDate;

import java.util.List;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.TipoArea;
import it.unibo.zoo.model.jdbc.ConnectionFactory;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoAreaDao;
import java.util.Optional;

public class TestConnection {
    
    public static void testAnimal() {
        AnimaleDao dao = new AnimaleDao();

        try {
            Animale animale = new Animale(
                0,
                "Simba",
                'M',
                true,
                LocalDate.of(2020, 5, 10),
                LocalDate.now(),
                null,
                1, // id_recinto esistente
                1  // id_specie esistente
            );

            Animale inserito = dao.insert(animale);
            System.out.println("Inserito: " + inserito);

            Optional<Animale> trovato = dao.findById(inserito.getIdAnimale());
            System.out.println("Trovato: " + trovato.orElse(null));

            List<Animale> perRecinto = dao.findByRecinto(1);
            System.out.println("Animali nel recinto 1: " + perRecinto.size());

            inserito.setNome("Re Simba");
            dao.update(inserito);

            // dao.delete(inserito.getIdAnimale());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTipoArea() {
        TipoAreaDao dao = new TipoAreaDao();

        try {
            TipoArea tipo = new TipoArea(2, "prova33", "Area animali esposti");
            TipoArea inserito = dao.insert(tipo);
            System.out.println("Inserito: " + inserito);

            Optional<TipoArea> trovato = dao.findById(inserito.getIdTipoArea());
            System.out.println("Trovato: " + trovato.orElse(null));

            List<TipoArea> tutti = dao.findAll();
            System.out.println("Totali: " + tutti.size());

            inserito.setDescrizione("Area espositiva aggiornata");
            boolean updated = dao.update(inserito);
            System.out.println("Aggiornato: " + updated);

            boolean deleted = dao.delete(inserito.getIdTipoArea());
            System.out.println("Eliminato: " + deleted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private static void testConnection(){
    //     try {
    //         Connection conn = ConnectionFactory.getInstance().getConnection();
    //         System.out.println("Connessione OK: " + conn);
    //         conn.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
    
    public static void main(String[] args) {

        try {
            Connection conn = ConnectionFactory.getInstance().getConnection();
            System.out.println("Connessione OK: " + conn);

            testTipoArea();

            testAnimal();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}