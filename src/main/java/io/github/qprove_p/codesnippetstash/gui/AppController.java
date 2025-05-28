package io.github.qprove_p.codesnippetstash.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome.FontAwesome;


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
    private Button nSnippetBtn;

    @FXML
    private Button nTagBtn;

    @FXML
    private BorderPane root;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private VBox sideMenu;

    @FXML
    private HBox topBar;

    @FXML
    void initialize() {
        assert allBtn != null : "fx:id=\"allBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert contentArea != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert favouritesBtn != null : "fx:id=\"favouritesBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nSnippetBtn != null : "fx:id=\"nSnippetBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nTagBtn != null : "fx:id=\"nTagBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert sideMenu != null : "fx:id=\"sideMenu\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert topBar != null : "fx:id=\"topBar\" was not injected: check your FXML file 'appWindow.fxml'.";

        FontIcon searchIcon = new FontIcon(FontAwesome.SEARCH);
        searchIcon.setIconSize(20);
        searchIcon.setIconColor(Color.web("#D9D9D9"));
        searchBtn.setGraphic(searchIcon);

        FontIcon homeIcon = new FontIcon(FontAwesome.HOME);
        homeIcon.setIconSize(20);
        homeIcon.setIconColor(Color.web("#D9D9D9"));
        homeBtn.setGraphic(homeIcon);

        FontIcon snippetIcon = new FontIcon(FontAwesome.FILE_TEXT);
        snippetIcon.setIconSize(20);
        snippetIcon.setIconColor(Color.web("#D9D9D9"));
        nSnippetBtn.setContentDisplay(ContentDisplay.RIGHT);
        nSnippetBtn.setText("+");
        nSnippetBtn.setGraphic(snippetIcon);

        FontIcon tagIcon = new FontIcon(FontAwesome.TAGS);
        tagIcon.setIconSize(20);
        tagIcon.setIconColor(Color.web("#D9D9D9"));
        nTagBtn.setContentDisplay(ContentDisplay.RIGHT);
        nTagBtn.setText("+");
        nTagBtn.setGraphic(tagIcon);

        FontIcon settingsIcon = new FontIcon(FontAwesome.COG);
        settingsIcon.setIconSize(20);
        settingsIcon.setIconColor(Color.web("#D9D9D9"));
        settingsBtn.setGraphic(settingsIcon);


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

        searchBtn.setOnMouseEntered(e -> searchBtn.setCursor(Cursor.HAND));
        searchBtn.setOnMouseExited(e -> searchBtn.setCursor(Cursor.DEFAULT));

        homeBtn.setOnMouseEntered(e -> homeBtn.setCursor(Cursor.HAND));
        homeBtn.setOnMouseExited(e -> homeBtn.setCursor(Cursor.DEFAULT));
        homeBtn.setOnAction(e -> openHomePage());

        settingsBtn.setOnMouseEntered(e -> settingsBtn.setCursor(Cursor.HAND));
        settingsBtn.setOnMouseExited(e -> settingsBtn.setCursor(Cursor.DEFAULT));

        nTagBtn.setOnMouseEntered(e -> nTagBtn.setCursor(Cursor.HAND));
        nTagBtn.setOnMouseExited(e -> nTagBtn.setCursor(Cursor.DEFAULT));
        nTagBtn.setOnAction(e -> openNewTagPage());

        nSnippetBtn.setOnMouseEntered(e -> nSnippetBtn.setCursor(Cursor.HAND));
        nSnippetBtn.setOnMouseExited(e -> nSnippetBtn.setCursor(Cursor.DEFAULT));
    }

    private void openNewTagPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/newTagPage.fxml"));
            Parent newTagContent = fxmlLoader.load();

            scrollContent.getChildren().clear();
            scrollContent.getColumnConstraints().clear();
            scrollContent.getRowConstraints().clear();

            scrollContent.prefWidthProperty().bind(contentArea.widthProperty());
            scrollContent.prefHeightProperty().bind(contentArea.heightProperty());

            scrollContent.setAlignment(Pos.CENTER);
            scrollContent.add(newTagContent, 0, 0);

            log.info("Switch to new-tag page");
        }catch(Exception e) {
            log.error("Unable to switch to new-tag page: ", e);
            throw new RuntimeException(e);
        }
    }

    private void openHomePage() {
        scrollContent.getChildren().clear();

        log.info("Switch to home view");
    }
}