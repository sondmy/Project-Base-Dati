package it.unibo.zoo.controller;

import it.unibo.zoo.model.SessionManager;
import it.unibo.zoo.view.AnimaliView;
import it.unibo.zoo.view.BigliettiView;
import it.unibo.zoo.view.GestioneView;
import it.unibo.zoo.view.HomeView;
import it.unibo.zoo.view.LoginView;
import it.unibo.zoo.view.MainView;

/**
 * Controller di navigazione: gestisce il cambio del pannello centrale in MainView.
 */
public class NavigationController {

    private final MainView mainView;

    public NavigationController(final MainView mainView) {
        this.mainView = mainView;
        wireNavbar();
    }

    /**
     * Collega i pulsanti della navbar ai metodi di navigazione.
     */
    private void wireNavbar() {
        mainView.getBtnHome().setOnAction(e -> navigaHome());
        mainView.getBtnAnimali().setOnAction(e -> navigaAnimali());
        mainView.getBtnBiglietti().setOnAction(e -> navigaBiglietti());
        mainView.getBtnGestione().setOnAction(e -> navigaGestione());
    }

    /**
     * Aggiorna la visibilità del pulsante Gestione in base allo stato della sessione.
     */
    private void refreshNavbar() {
        mainView.setGestioneVisible(SessionManager.getInstance().isLoggato());
    }

    /* ── Metodi di navigazione ───────────────────────── */

    public void navigaHome() {
        refreshNavbar();
        final HomeView homeView = new HomeView();
        homeView.getCardBiglietti().setOnMouseClicked(e -> navigaBiglietti());
        homeView.getCardAnimali().setOnMouseClicked(e -> navigaAnimali());
        homeView.getCardGestione().setOnMouseClicked(e -> navigaGestione());
        mainView.setCenter(homeView.getRoot());
    }

    public void navigaAnimali() {
        refreshNavbar();
        final AnimaliView animaliView = new AnimaliView();
        new AnimaliController(animaliView);
        mainView.setCenter(animaliView.getRoot());
    }

    public void navigaBiglietti() {
        refreshNavbar();
        final BigliettiView bigliettiView = new BigliettiView();
        new BigliettiController(bigliettiView);
        mainView.setCenter(bigliettiView.getRoot());
    }

    public void navigaLogin() {
        refreshNavbar();
        final LoginView loginView = new LoginView();
        new LoginController(loginView, this);
        mainView.setCenter(loginView.getRoot());
    }

    public void navigaGestione() {
        refreshNavbar();
        if (!SessionManager.getInstance().isLoggato()) {
            navigaLogin();
            return;
        }
        mainView.setGestioneVisible(true);
        final GestioneView gestioneView = new GestioneView();
        new GestioneController(gestioneView);
        mainView.setCenter(gestioneView.getRoot());
    }
}
