package it.unibo.zoo.controller;

import it.unibo.zoo.view.AnimaliView;
import it.unibo.zoo.view.BigliettiView;
import it.unibo.zoo.view.DonazioniView;
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
        mainView.getBtnDonazioni().setOnAction(e -> navigaDonazioni());
        mainView.getBtnGestione().setOnAction(e -> navigaLogin());
    }

    /**
     * Aggiorna la visibilità del pulsante Gestione in base allo stato della sessione.
     */
    private void refreshNavbar() {
        mainView.setGestioneVisible(true);
    }

    /* ── Metodi di navigazione ───────────────────────── */

    public void navigaHome() {
        refreshNavbar();
        final HomeView homeView = new HomeView();
        homeView.getCardBiglietti().setOnMouseClicked(e -> navigaBiglietti());
        homeView.getCardAnimali().setOnMouseClicked(e -> navigaAnimali());
        homeView.getCardDonazioni().setOnMouseClicked(e -> navigaDonazioni());
        mainView.setCenter(homeView.getRoot());
    }

    public void navigaAnimali() {
        refreshNavbar();
        final AnimaliView animaliView = new AnimaliView();
        try {
            new AnimaliController(animaliView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainView.setCenter(animaliView.getRoot());
    }

    public void navigaBiglietti() {
        refreshNavbar();
        final BigliettiView bigliettiView = new BigliettiView();
        try {
            new BigliettiController(bigliettiView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainView.setCenter(bigliettiView.getRoot());
    }

    public void navigaDonazioni() {
        refreshNavbar();
        final DonazioniView donazioniView = new DonazioniView();
        try {
            new it.unibo.zoo.controller.DonazioniController(donazioniView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainView.setCenter(donazioniView.getRoot());
    }

    public void navigaLogin() {
        refreshNavbar();
        final LoginView loginView = new LoginView();
        new LoginController(loginView, this::navigaGestione);
        mainView.setCenter(loginView.getRoot());
    }

    public void navigaGestione(it.unibo.zoo.model.entity.Utente utente) {
        refreshNavbar();
        mainView.setGestioneVisible(true);
        final GestioneView gestioneView = new GestioneView();
        gestioneView.filterTabsByRuolo(utente.getRuolo());
        try {
            new GestioneController(gestioneView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainView.setCenter(gestioneView.getRoot());
    }
}
