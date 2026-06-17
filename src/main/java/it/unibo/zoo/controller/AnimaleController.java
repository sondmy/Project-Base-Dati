package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Animale;
import it.unibo.zoo.model.jdbc.DaoException;
import it.unibo.zoo.model.jdbc.entityDao.AnimaleDao;
import it.unibo.zoo.utils.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AnimaleController {

    private final AnimaleDao animaleDao;

    public AnimaleController() {
        this.animaleDao = new AnimaleDao();
    }

    public List<Animale> getAllAnimali() {
        return animaleDao.findAll();
    }

    public Optional<Animale> getAnimaleById(int idAnimale) {
        InputValidator.notNegative(idAnimale, "idAnimale");
        return animaleDao.findById(idAnimale);
    }

    public List<Animale> getAnimaliByRecinto(int idRecinto) {
        InputValidator.notNegative(idRecinto, "idRecinto");
        return animaleDao.findByRecinto(idRecinto);
    }

    public List<Animale> getAnimaliBySpecie(int idSpecie) {
        InputValidator.notNegative(idSpecie, "idSpecie");
        return animaleDao.findBySpecie(idSpecie);
    }

    public Animale createAnimale(
            String nome,
            char sesso,
            boolean attivo,
            LocalDate dataNascita,
            LocalDate dataArrivo,
            LocalDate dataUscita,
            int idRecinto,
            int idSpecie) {

        InputValidator.notBlank(nome, "nome");
        InputValidator.checkSesso(sesso);
        InputValidator.notNegative(idRecinto, "idRecinto");
        InputValidator.notNegative(idSpecie, "idSpecie");

        if (dataNascita != null && dataArrivo != null && dataNascita.isAfter(dataArrivo)) {
            throw new IllegalArgumentException("La data di nascita non può essere dopo la data di arrivo");
        }

        Animale animale = new Animale(
                0,
                nome,
                sesso,
                attivo,
                dataNascita,
                dataArrivo,
                dataUscita,
                idRecinto,
                idSpecie
        );

        try {
            return animaleDao.insert(animale);
        } catch (DaoException e) {
            throw new RuntimeException("Errore durante la creazione dell'animale", e);
        }
    }

    public boolean updateAnimale(Animale animale) {
        if (animale == null) {
            throw new IllegalArgumentException("Animale nullo");
        }

        InputValidator.notBlank(animale.getNome(), "nome");
        InputValidator.checkSesso(animale.getSesso());
        InputValidator.notNegative(animale.getIdRecinto(), "idRecinto");
        InputValidator.notNegative(animale.getIdSpecie(), "idSpecie");

        try {
            return animaleDao.update(animale);
        } catch (DaoException e) {
            throw new RuntimeException("Errore durante l'aggiornamento dell'animale", e);
        }
    }

    public boolean deleteAnimale(int idAnimale) {
        InputValidator.notNegative(idAnimale, "idAnimale");
        try {
            return animaleDao.delete(idAnimale);
        } catch (DaoException e) {
            throw new RuntimeException("Errore durante l'eliminazione dell'animale", e);
        }
    }
}