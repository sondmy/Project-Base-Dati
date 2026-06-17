package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.model.jdbc.entityDao.UtenteDao;
import it.unibo.zoo.utils.InputValidator;
import it.unibo.zoo.utils.PasswordHasher;

public class LoginController {

    private final UtenteDao utenteDao;

    public LoginController() {
        this.utenteDao = new UtenteDao();
    }

    public Utente login(String email, String password) {
        InputValidator.notBlank(email, "email");
        InputValidator.notBlank(password, "password");

        Utente utente = utenteDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        if (!PasswordHasher.verify(password, utente.getPasswordHash())) {
            throw new IllegalArgumentException("Password non valida");
        }

        return utente;
    }
}
