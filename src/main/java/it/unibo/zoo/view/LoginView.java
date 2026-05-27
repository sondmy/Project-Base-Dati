package it.unibo.zoo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Form di login centrato.
 */
public class LoginView {

    private final VBox root;
    private final TextField txtEmail;
    private final PasswordField txtPassword;
    private final Button btnAccedi;
    private final Label lblErrore;

    public LoginView() {
        root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(64, 32, 64, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        /* ── Card login ──────────────────────────────────── */
        final VBox card = new VBox(16);
        card.setStyle(StyleHelper.STYLE_CARD);
        card.setMaxWidth(360);
        card.setPadding(new Insets(32));
        card.setAlignment(Pos.CENTER_LEFT);

        final Label title = new Label("\uD83D\uDD12 Accesso riservato");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        final Label lblEmail = new Label("Email");
        lblEmail.setStyle(StyleHelper.STYLE_LABEL);
        txtEmail = new TextField();
        txtEmail.setPromptText("email@zoo.it");
        txtEmail.setStyle(StyleHelper.STYLE_TEXT_FIELD);

        final Label lblPassword = new Label("Password");
        lblPassword.setStyle(StyleHelper.STYLE_LABEL);
        txtPassword = new PasswordField();
        txtPassword.setPromptText("••••••••");
        txtPassword.setStyle(StyleHelper.STYLE_TEXT_FIELD);

        btnAccedi = new Button("Accedi");
        btnAccedi.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnAccedi.setMaxWidth(Double.MAX_VALUE);
        btnAccedi.setOnMouseEntered(e -> btnAccedi.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnAccedi.setOnMouseExited(e -> btnAccedi.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        lblErrore = new Label("Credenziali non valide");
        lblErrore.setStyle(StyleHelper.STYLE_ERROR_LABEL);
        lblErrore.setVisible(false);
        lblErrore.setManaged(false);

        card.getChildren().addAll(title, lblEmail, txtEmail, lblPassword, txtPassword, btnAccedi, lblErrore);
        root.getChildren().add(card);
    }

    /* ── Metodi pubblici ─────────────────────────────────── */

    public VBox getRoot() { return root; }

    public TextField getTxtEmail() { return txtEmail; }

    public PasswordField getTxtPassword() { return txtPassword; }

    public Button getBtnAccedi() { return btnAccedi; }

    /**
     * Mostra il messaggio di errore.
     */
    public void showError(final String msg) {
        lblErrore.setText(msg);
        lblErrore.setVisible(true);
        lblErrore.setManaged(true);
    }

    /**
     * Nasconde il messaggio di errore.
     */
    public void hideError() {
        lblErrore.setVisible(false);
        lblErrore.setManaged(false);
    }
}
