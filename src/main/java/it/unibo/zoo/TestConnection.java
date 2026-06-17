package it.unibo.zoo;

import java.sql.Connection;
import java.time.LocalDate;

import java.util.List;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.entity.TipoArea;
import it.unibo.zoo.model.jdbc.ConnectionFactory;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.model.jdbc.entityDao.TipoAreaDao;
import it.unibo.zoo.model.jdbc.entityDao.SpecieDao;
import it.unibo.zoo.model.jdbc.entityDao.DipendenteDao;
import it.unibo.zoo.model.jdbc.entityDao.VisitaMedicaDao;
import it.unibo.zoo.model.entity.Specie;
import it.unibo.zoo.model.entity.Dipendente;
import it.unibo.zoo.model.entity.VisitaMedica;
import java.util.Optional;

public class TestConnection {
    
    public static void testAnimal() {
        System.out.println("Aggiornamento password utenti con BCrypt...");
        String newPassword = "password123";
        String hashed = it.unibo.zoo.utils.PasswordHasher.hash(newPassword);
        
        try (java.sql.Connection conn = it.unibo.zoo.model.jdbc.ConnectionFactory.getInstance().getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement("UPDATE utente SET password_hash = ?")) {
            stmt.setString(1, hashed);
            int updated = stmt.executeUpdate();
            System.out.println("Aggiornati " + updated + " utenti con la password: " + newPassword);
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

    public static void testQueries() {
        System.out.println("--- ESECUZIONE QUERY DI TEST ---");
        try {
            System.out.println("1. Elenco di tutte le Specie:");
            SpecieDao specieDao = new SpecieDao();
            List<Specie> specie = specieDao.findAll();
            specie.forEach(s -> System.out.println("  - " + s.getNomeComune() + " (" + s.getNomeScentifico() + ")"));

            System.out.println("\n2. Elenco dei Dipendenti:");
            DipendenteDao dipDao = new DipendenteDao();
            List<Dipendente> dipendenti = dipDao.findAll();
            dipendenti.forEach(d -> System.out.println("  - " + d.getNome() + " " + d.getCognome() + " (CF: " + d.getCodiceFiscale() + ")"));

            System.out.println("\n3. Elenco delle Visite Mediche in corso:");
            VisitaMedicaDao visitaDao = new VisitaMedicaDao();
            List<VisitaMedica> visite = visitaDao.findAll();
            visite.stream()
                  .filter(v -> v.getDataFine() == null)
                  .forEach(v -> System.out.println("  - Visita del " + v.getDataVisita() + " - Diagnosi: " + v.getDiagnosi()));

            System.out.println("--------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {

        try {
            Connection conn = ConnectionFactory.getInstance().getConnection();
            System.out.println("Connessione OK: " + conn);

            testAnimal();
            testQueries();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}