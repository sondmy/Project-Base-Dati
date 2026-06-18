package it.unibo.zoo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Schermata iniziale con 3 card cliccabili: Biglietti, Animali, Area Gestione.
 */
public class HomeView {

    private final VBox root;
    private final VBox cardBiglietti;
    private final VBox cardAnimali;
    private final VBox cardGestione;

    public HomeView() {
        root = new VBox(24);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(48, 32, 48, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        /* ── Titolo ──────────────────────────────────────── */
        final Label title = new Label("Benvenuto nel gestionale dello Zoo");
        title.setStyle(StyleHelper.STYLE_TITLE);

        final Label subtitle = new Label("Seleziona una sezione per iniziare");
        subtitle.setStyle(StyleHelper.STYLE_SUBTITLE);

        /* ── Card ────────────────────────────────────────── */
        cardBiglietti = createCard(
                "\uD83C\uDFAB Acquista Biglietti",
                "Scegli il tipo di biglietto e completa l'acquisto in pochi click."
        );
        cardAnimali = createCard(
                "\uD83D\uDC3E Scopri gli Animali",
                "Esplora l'elenco degli animali del nostro zoo e le loro specie."
        );
        cardGestione = createCard(
                "\uD83D\uDD12 Area Gestione",
                "Accedi al pannello riservato per gestire finanze, ordini e turni."
        );

        final HBox cardsBox = new HBox(20, cardBiglietti, cardAnimali, cardGestione);
        cardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, subtitle, cardsBox);
    }

    /* ── Accesso ─────────────────────────────────────────── */

    public VBox getRoot() { return root; }

    public VBox getCardBiglietti() { return cardBiglietti; }
    public VBox getCardAnimali()   { return cardAnimali; }
    public VBox getCardGestione()  { return cardGestione; }

    /* ── Utility ─────────────────────────────────────────── */

    private VBox createCard(final String titleText, final String descText) {
        final VBox card = new VBox(8);
        card.setStyle(StyleHelper.STYLE_CARD);
        card.setPrefWidth(260);
        card.setMinHeight(140);
        card.setAlignment(Pos.CENTER_LEFT);

        final Label cardTitle = new Label(titleText);
        cardTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: "
                + StyleHelper.TEXT_MAIN + ";");

        final Label cardDesc = new Label(descText);
        cardDesc.setWrapText(true);
        cardDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: " + StyleHelper.TEXT_MUTED + ";");

        card.getChildren().addAll(cardTitle, cardDesc);

        card.setOnMouseEntered(e -> card.setStyle(StyleHelper.STYLE_CARD_HOVER));
        card.setOnMouseExited(e -> card.setStyle(StyleHelper.STYLE_CARD));

        return card;
    }
}
