package it.unibo.zoo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Finestra principale dell'applicazione.
 * Contiene una navbar in alto, un pannello centrale intercambiabile e un footer.
 */
public class MainView {

    private final BorderPane root;
    private final HBox navButtonsBox;
    private Button btnHome;
    private Button btnAnimali;
    private Button btnBiglietti;
    private Button btnGestione;

    public MainView() {
        root = new BorderPane();
        root.setStyle(StyleHelper.STYLE_APP_BG);

        /* ── TOP: Navbar ─────────────────────────────────── */
        final HBox navbar = new HBox();
        navbar.setStyle(StyleHelper.STYLE_NAVBAR);
        navbar.setAlignment(Pos.CENTER_LEFT);

        final Label title = new Label("\uD83E\uDD81 Zoo Manager");
        title.setStyle(StyleHelper.STYLE_NAVBAR_TITLE);

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnHome = createNavButton("Home");
        btnAnimali = createNavButton("Animali");
        btnBiglietti = createNavButton("Biglietti");
        btnGestione = createNavButton("Gestione");
        btnGestione.setVisible(false);
        btnGestione.setManaged(false);

        navButtonsBox = new HBox(8, btnHome, btnAnimali, btnBiglietti, btnGestione);
        navButtonsBox.setAlignment(Pos.CENTER_RIGHT);

        navbar.getChildren().addAll(title, spacer, navButtonsBox);
        root.setTop(navbar);

        /* ── BOTTOM: Footer ──────────────────────────────── */
        final HBox footer = new HBox();
        footer.setStyle(StyleHelper.STYLE_FOOTER);
        footer.setAlignment(Pos.CENTER);
        final Label footerLabel = new Label("\u00A9 2025 Zoo Manager \u2014 Gestionale interno");
        footerLabel.setStyle(StyleHelper.STYLE_FOOTER_TEXT);
        footer.getChildren().add(footerLabel);
        root.setBottom(footer);
    }

    /* ── Accesso ai componenti per il Controller ─────── */

    public BorderPane getRoot() { return root; }

    public void setCenter(final javafx.scene.Node node) {
        root.setCenter(node);
    }

    public Button getBtnHome()      { return btnHome; }
    public Button getBtnAnimali()   { return btnAnimali; }
    public Button getBtnBiglietti() { return btnBiglietti; }
    public Button getBtnGestione()  { return btnGestione; }

    /**
     * Mostra o nasconde il pulsante "Gestione" nella navbar.
     */
    public void setGestioneVisible(final boolean visible) {
        btnGestione.setVisible(visible);
        btnGestione.setManaged(visible);
    }

    /* ── Utility ─────────────────────────────────────────── */

    private Button createNavButton(final String text) {
        final Button btn = new Button(text);
        btn.setStyle(StyleHelper.STYLE_NAV_BUTTON);
        btn.setOnMouseEntered(e -> btn.setStyle(StyleHelper.STYLE_NAV_BUTTON_HOVER));
        btn.setOnMouseExited(e -> btn.setStyle(StyleHelper.STYLE_NAV_BUTTON));
        return btn;
    }
}
