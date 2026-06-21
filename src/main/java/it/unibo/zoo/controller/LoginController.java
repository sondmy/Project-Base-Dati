package it.unibo.zoo.controller;

import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.model.jdbc.entityDao.UtenteDao;
import it.unibo.zoo.utils.InputValidator;
import it.unibo.zoo.utils.PasswordHasher;
import it.unibo.zoo.view.LoginView;

public class LoginController {

    private final UtenteDao utenteDao;
    private final LoginView view;
    private final java.util.function.Consumer<Utente> onSuccess;

    public LoginController(LoginView view, java.util.function.Consumer<Utente> onSuccess) {
        this.utenteDao = new UtenteDao();
        this.view = view;
        this.onSuccess = onSuccess;
        
        if (this.view != null) {
            this.view.getBtnAccedi().setOnAction(e -> handleLogin());
        }
    }
    
    public LoginController() {
        this(null, null);
    }

    private void handleLogin() {
        String email = view.getTxtEmail().getText();
        String password = view.getTxtPassword().getText();

        try {
            Utente u = login(email, password);
            view.hideError();
            if (onSuccess != null) {
                onSuccess.accept(u);
            }
        } catch (Exception ex) {
            view.showError(ex.getMessage() != null ? ex.getMessage() : "Credenziali non valide");
        }
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
