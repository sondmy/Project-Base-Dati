package it.unibo.zoo.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Record di visualizzazione per la tabella animali (dati denormalizzati).
 */
public class AnimaliView {

    /** DTO piatto per le righe della TableView. */
    public static class AnimaleRow {
        private final String nome;
        private final String specie;
        private final String sesso;
        private final String habitat;
        private final String statoConservazione;
        private final String recinto;
        private final String area;

        public AnimaleRow(final String nome, final String specie, final String sesso,
                          final String habitat, final String statoConservazione, final String recinto, final String area) {
            this.nome = nome;
            this.specie = specie;
            this.sesso = sesso;
            this.habitat = habitat;
            this.statoConservazione = statoConservazione;
            this.recinto = recinto;
            this.area = area;
        }

        public String getNome() { return nome; }
        public String getSpecie() { return specie; }
        public String getSesso() { return sesso; }
        public String getHabitat() { return habitat; }
        public String getStatoConservazione() { return statoConservazione; }
        public String getRecinto() { return recinto; }
        public String getArea() { return area; }
    }

    private final VBox root;
    private final TableView<AnimaleRow> table;
    private final TextField txtCerca;
    private final Button btnCerca;

    @SuppressWarnings("unchecked")
    public AnimaliView() {
        root = new VBox(16);
        root.setPadding(new Insets(32, 32, 32, 32));
        root.setStyle(StyleHelper.STYLE_APP_BG);

        /* ── Titolo ──────────────────────────────────────── */
        final Label title = new Label("I nostri animali");
        title.setStyle(StyleHelper.STYLE_TITLE);

        /* ── Barra ricerca ───────────────────────────────── */
        txtCerca = new TextField();
        txtCerca.setPromptText("Cerca per nome o specie...");
        txtCerca.setStyle(StyleHelper.STYLE_TEXT_FIELD);
        txtCerca.setPrefWidth(320);

        btnCerca = new Button("Cerca");
        btnCerca.setStyle(StyleHelper.STYLE_BTN_PRIMARY);
        btnCerca.setOnMouseEntered(e -> btnCerca.setStyle(StyleHelper.STYLE_BTN_PRIMARY_HOVER));
        btnCerca.setOnMouseExited(e -> btnCerca.setStyle(StyleHelper.STYLE_BTN_PRIMARY));

        final HBox searchBox = new HBox(12, txtCerca, btnCerca);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        /* ── Tabella ─────────────────────────────────────── */
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        final TableColumn<AnimaleRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nome"));

        final TableColumn<AnimaleRow, String> colSpecie = new TableColumn<>("Specie");
        colSpecie.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("specie"));

        final TableColumn<AnimaleRow, String> colSesso = new TableColumn<>("Sesso");
        colSesso.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("sesso"));
        colSesso.setMaxWidth(70);

        final TableColumn<AnimaleRow, String> colHabitat = new TableColumn<>("Habitat");
        colHabitat.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("habitat"));

        final TableColumn<AnimaleRow, String> colStato = new TableColumn<>("Stato conservazione");
        colStato.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("statoConservazione"));
        colStato.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(final String item, final boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Label badge = new Label(item);
                    badge.setStyle(StyleHelper.badgeStato(item));
                    setGraphic(badge);
                    setText(null);
                }
            }
        });

        final TableColumn<AnimaleRow, String> colRecinto = new TableColumn<>("Recinto");
        colRecinto.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("recinto"));
        colRecinto.setMaxWidth(90);

        final TableColumn<AnimaleRow, String> colArea = new TableColumn<>("Area");
        colArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
        colArea.setMaxWidth(90);

        table.getColumns().addAll(colNome, colSpecie, colSesso, colHabitat, colStato, colRecinto, colArea);

        root.getChildren().addAll(title, searchBox, table);
    }

    /* ── Metodi pubblici ─────────────────────────────────── */

    public VBox getRoot() { return root; }

    public TextField getTxtCerca() { return txtCerca; }

    public Button getBtnCerca() { return btnCerca; }

    /**
     * Aggiorna la lista degli animali mostrati in tabella.
     */
    public void setAnimali(final List<AnimaleRow> rows) {
        table.setItems(FXCollections.observableArrayList(rows));
    }
}
