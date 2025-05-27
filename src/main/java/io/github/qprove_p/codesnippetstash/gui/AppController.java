package io.github.qprove_p.codesnippetstash.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button allBtn;

    @FXML
    private ScrollPane contentArea;

    @FXML
    private GridPane scrollContent;

    @FXML
    private Button favouritesBtn;

    @FXML
    private Button hamburgerBtn;

    @FXML
    private Button nSnippetBtn;

    @FXML
    private Button nTagBtn;

    @FXML
    private BorderPane root;

    @FXML
    private TextField searchBar;

    @FXML
    private Button settingsBtn;

    @FXML
    private VBox sideMenu;

    @FXML
    private HBox topBar;

    @FXML
    void toggleSideMenu(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert allBtn != null : "fx:id=\"allBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert contentArea != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert favouritesBtn != null : "fx:id=\"favouritesBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert hamburgerBtn != null : "fx:id=\"hamburgerBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nSnippetBtn != null : "fx:id=\"nSnippetBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nTagBtn != null : "fx:id=\"nTagBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert sideMenu != null : "fx:id=\"sideMenu\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert topBar != null : "fx:id=\"topBar\" was not injected: check your FXML file 'appWindow.fxml'.";

        contentArea.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            scrollContent.setPrefWidth(newBounds.getWidth());
        });

        allBtn.setMaxWidth(Double.MAX_VALUE);
        favouritesBtn.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(allBtn, Priority.ALWAYS);
        VBox.setVgrow(favouritesBtn, Priority.ALWAYS);

        allBtn.setOnMouseEntered(e -> allBtn.setCursor(Cursor.HAND));
        allBtn.setOnMouseExited(e -> allBtn.setCursor(Cursor.DEFAULT));

        favouritesBtn.setOnMouseEntered(e -> favouritesBtn.setCursor(Cursor.HAND));
        favouritesBtn.setOnMouseExited(e -> favouritesBtn.setCursor(Cursor.DEFAULT));

        settingsBtn.setOnMouseEntered(e -> settingsBtn.setCursor(Cursor.HAND));
        settingsBtn.setOnMouseExited(e -> settingsBtn.setCursor(Cursor.DEFAULT));

        nTagBtn.setOnMouseEntered(e -> nTagBtn.setCursor(Cursor.HAND));
        nTagBtn.setOnMouseExited(e -> nTagBtn.setCursor(Cursor.DEFAULT));

        nSnippetBtn.setOnMouseEntered(e -> nSnippetBtn.setCursor(Cursor.HAND));
        nSnippetBtn.setOnMouseExited(e -> nSnippetBtn.setCursor(Cursor.DEFAULT));
    }
}
