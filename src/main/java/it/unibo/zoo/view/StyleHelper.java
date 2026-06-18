package it.unibo.zoo.view;

/**
 * Costanti di stile inline riutilizzabili in tutte le viste.
 * Palette ispirata a Bootstrap 5 con sfondo chiaro.
 */
public final class StyleHelper {

    private StyleHelper() {}

    /* ── Colori ───────────────────────────────────────────── */
    public static final String BG_APP        = "#F8F9FA";
    public static final String NAVBAR_BG     = "#212529";
    public static final String WHITE         = "#FFFFFF";
    public static final String PRIMARY       = "#0D6EFD";
    public static final String TEXT_MAIN     = "#212529";
    public static final String TEXT_MUTED    = "#6C757D";
    public static final String BORDER_CARD   = "#DEE2E6";
    public static final String GREEN         = "#198754";
    public static final String YELLOW        = "#FFC107";
    public static final String ORANGE        = "#FD7E14";
    public static final String RED           = "#DC3545";
    public static final String GREY          = "#6C757D";

    /* ── Stili composti ──────────────────────────────────── */

    public static final String STYLE_APP_BG =
            "-fx-background-color: " + BG_APP + ";";

    public static final String STYLE_NAVBAR =
            "-fx-background-color: " + NAVBAR_BG + ";"
            + " -fx-padding: 12 24 12 24;";

    public static final String STYLE_NAVBAR_TITLE =
            "-fx-text-fill: " + WHITE + ";"
            + " -fx-font-size: 18px;"
            + " -fx-font-weight: bold;";

    public static final String STYLE_NAV_BUTTON =
            "-fx-background-color: transparent;"
            + " -fx-text-fill: " + WHITE + ";"
            + " -fx-font-size: 13px;"
            + " -fx-cursor: hand;"
            + " -fx-padding: 6 14 6 14;"
            + " -fx-background-radius: 6;";

    public static final String STYLE_NAV_BUTTON_HOVER =
            "-fx-background-color: rgba(255,255,255,0.15);"
            + " -fx-text-fill: " + WHITE + ";"
            + " -fx-font-size: 13px;"
            + " -fx-cursor: hand;"
            + " -fx-padding: 6 14 6 14;"
            + " -fx-background-radius: 6;";

    public static final String STYLE_BTN_PRIMARY =
            "-fx-background-color: " + PRIMARY + ";"
            + " -fx-text-fill: " + WHITE + ";"
            + " -fx-font-size: 14px;"
            + " -fx-padding: 8 20 8 20;"
            + " -fx-background-radius: 6;"
            + " -fx-cursor: hand;";

    public static final String STYLE_BTN_PRIMARY_HOVER =
            "-fx-background-color: #0b5ed7;"
            + " -fx-text-fill: " + WHITE + ";"
            + " -fx-font-size: 14px;"
            + " -fx-padding: 8 20 8 20;"
            + " -fx-background-radius: 6;"
            + " -fx-cursor: hand;";

    public static final String STYLE_BTN_OUTLINE =
            "-fx-background-color: transparent;"
            + " -fx-text-fill: " + PRIMARY + ";"
            + " -fx-border-color: " + PRIMARY + ";"
            + " -fx-border-radius: 6;"
            + " -fx-background-radius: 6;"
            + " -fx-font-size: 14px;"
            + " -fx-padding: 8 20 8 20;"
            + " -fx-cursor: hand;";

    public static final String STYLE_BTN_OUTLINE_HOVER =
            "-fx-background-color: " + PRIMARY + ";"
            + " -fx-text-fill: " + WHITE + ";"
            + " -fx-border-color: " + PRIMARY + ";"
            + " -fx-border-radius: 6;"
            + " -fx-background-radius: 6;"
            + " -fx-font-size: 14px;"
            + " -fx-padding: 8 20 8 20;"
            + " -fx-cursor: hand;";

    public static final String STYLE_CARD =
            "-fx-background-color: " + WHITE + ";"
            + " -fx-border-color: " + BORDER_CARD + ";"
            + " -fx-border-radius: 8;"
            + " -fx-background-radius: 8;"
            + " -fx-padding: 16;"
            + " -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);";

    public static final String STYLE_CARD_HOVER =
            "-fx-background-color: " + WHITE + ";"
            + " -fx-border-color: " + PRIMARY + ";"
            + " -fx-border-radius: 8;"
            + " -fx-background-radius: 8;"
            + " -fx-padding: 16;"
            + " -fx-effect: dropshadow(gaussian, rgba(13,110,253,0.18), 12, 0, 0, 3);"
            + " -fx-cursor: hand;";

    public static final String STYLE_TITLE =
            "-fx-font-size: 24px;"
            + " -fx-font-weight: bold;"
            + " -fx-text-fill: " + TEXT_MAIN + ";";

    public static final String STYLE_SUBTITLE =
            "-fx-font-size: 14px;"
            + " -fx-text-fill: " + TEXT_MUTED + ";";

    public static final String STYLE_LABEL =
            "-fx-font-size: 14px;"
            + " -fx-text-fill: " + TEXT_MAIN + ";";

    public static final String STYLE_FOOTER =
            "-fx-background-color: " + NAVBAR_BG + ";"
            + " -fx-padding: 8 24 8 24;";

    public static final String STYLE_FOOTER_TEXT =
            "-fx-text-fill: " + TEXT_MUTED + ";"
            + " -fx-font-size: 11px;";

    public static final String STYLE_TEXT_FIELD =
            "-fx-font-size: 14px;"
            + " -fx-padding: 8 12 8 12;"
            + " -fx-border-color: " + BORDER_CARD + ";"
            + " -fx-border-radius: 6;"
            + " -fx-background-radius: 6;";

    public static final String STYLE_ERROR_LABEL =
            "-fx-text-fill: " + RED + ";"
            + " -fx-font-size: 13px;";

    public static final String STYLE_SUCCESS_LABEL =
            "-fx-text-fill: " + GREEN + ";"
            + " -fx-font-size: 13px;";

    public static final String STYLE_METRIC_CARD =
            "-fx-background-color: " + WHITE + ";"
            + " -fx-border-color: " + BORDER_CARD + ";"
            + " -fx-border-radius: 8;"
            + " -fx-background-radius: 8;"
            + " -fx-padding: 20;"
            + " -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 6, 0, 0, 2);"
            + " -fx-min-width: 200;";

    /* ── Badge helpers ───────────────────────────────────── */

    /**
     * Restituisce lo stile badge per un codice di stato conservazione IUCN.
     */
    public static String badgeStato(final String codice) {
        final String bg;
        switch (codice) {
            case "LC": bg = GREEN;  break;
            case "VU": bg = YELLOW; break;
            case "EN": bg = ORANGE; break;
            case "CR": bg = RED;    break;
            default:   bg = GREY;   break;  // EW, EX
        }
        final String textColor = "VU".equals(codice) ? TEXT_MAIN : WHITE;
        return "-fx-background-color: " + bg + ";"
                + " -fx-text-fill: " + textColor + ";"
                + " -fx-padding: 2 8 2 8;"
                + " -fx-background-radius: 4;"
                + " -fx-font-size: 12px;"
                + " -fx-font-weight: bold;";
    }

    /**
     * Restituisce lo stile badge generico (colore sfondo + testo bianco).
     */
    public static String badge(final String bgColor) {
        return "-fx-background-color: " + bgColor + ";"
                + " -fx-text-fill: " + WHITE + ";"
                + " -fx-padding: 2 8 2 8;"
                + " -fx-background-radius: 4;"
                + " -fx-font-size: 12px;"
                + " -fx-font-weight: bold;";
    }
}
