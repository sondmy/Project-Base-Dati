package it.unibo.zoo.controller;

import it.unibo.zoo.model.SessionManager;
import it.unibo.zoo.model.entity.Utente;
import it.unibo.zoo.view.LoginView;

import java.time.LocalDate;

/**
 * Controller per la schermata Login.
 * Verifica credenziali mock e salva l'utente nella sessione.
 */
public class LoginController {

    private final LoginView view;
    private final NavigationController navController;

    public LoginController(final LoginView view, final NavigationController navController) {
        this.view = view;
        this.navController = navController;
        init();
    }

    private void init() {
        view.getBtnAccedi().setOnAction(e -> handleLogin());
        view.getTxtPassword().setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        view.hideError();

        final String email = view.getTxtEmail().getText();
        final String password = view.getTxtPassword().getText();

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            view.showError("Inserisci email e password.");
            return;
        }

        final Utente utente = authenticate(email.trim(), password);
        if (utente != null) {
            SessionManager.getInstance().login(utente);
            navController.navigaGestione();
        } else {
            view.showError("Credenziali non valide");
        }
    }

    /**
     * Verifica credenziali mock.
     * @return l'Utente autenticato, oppure null se le credenziali non corrispondono.
     */
    private Utente authenticate(final String email, final String password) {
        if ("admin@zoo.it".equals(email) && "admin123".equals(password)) {
            return new Utente(1, email, "hashed", LocalDate.of(2024, 1, 1), 1);
        }
        if ("cassiere@zoo.it".equals(email) && "cassa123".equals(password)) {
            return new Utente(2, email, "hashed", LocalDate.of(2024, 6, 1), 3);
        }
        return null;
    }
}
