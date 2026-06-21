package it.unibo.zoo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

/**
 * Schermata iniziale con 2 card cliccabili: Biglietti, Animali.
 */
public class HomeView {

    private final VBox root;
    private final VBox cardBiglietti;
    private final VBox cardAnimali;
    private final VBox cardDonazioni;

    public HomeView() {
        root = new VBox(24);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(48, 32, 48, 32));
        
        try {
            Image bgImage = new Image("file:src/main/java/it/unibo/zoo/utils/images/home.png");
            if (bgImage.isError()) {
                throw new Exception("Image error");
            }
            BackgroundImage bgi = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            root.setBackground(new Background(bgi));
        } catch (Exception e) {
            root.setStyle(StyleHelper.STYLE_APP_BG);
        }

        /* ── Titolo ──────────────────────────────────────── */
        final Label title = new Label("Benvenuto nel gestionale dello Zoo");
        title.setStyle(StyleHelper.STYLE_TITLE);

        /* ── Card ────────────────────────────────────────── */
        cardBiglietti = createCard(
                "\uD83C\uDFAB Acquista Biglietti",
                "Scegli il tipo di biglietto e completa l'acquisto in pochi click."
        );
        cardAnimali = createCard(
                "\uD83D\uDC3E Scopri gli Animali",
                "Esplora l'elenco degli animali del nostro zoo e le loro specie."
        );
        cardDonazioni = createCard(
                "\uD83D\uDCB0 Donazioni",
                "Fai una donazione per supportare i nostri animali."
        );

        final HBox cardsBox = new HBox(20, cardBiglietti, cardAnimali, cardDonazioni);
        cardsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, cardsBox);
    }

    /* ── Accesso ─────────────────────────────────────────── */

    public VBox getRoot() { return root; }

    public VBox getCardBiglietti() { return cardBiglietti; }
    public VBox getCardAnimali()   { return cardAnimali; }
    public VBox getCardDonazioni() { return cardDonazioni; }

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
