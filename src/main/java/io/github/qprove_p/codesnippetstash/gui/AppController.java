package io.github.qprove_p.codesnippetstash.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.DetailData;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ScrollPane sideMenuScrollPane;

    @FXML
    private VBox sideMenu;

    @FXML
    private HBox topBar;

    @FXML
    private AnchorPane content;

    @FXML
    private ProgressIndicator progressIndicator;

    private TagRepository tagRepository = new TagRepository();
    private HomeController homeController;

    @FXML
    void initialize() {
        assert allBtn != null : "fx:id=\"allBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert favouritesBtn != null : "fx:id=\"favouritesBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nSnippetBtn != null : "fx:id=\"nSnippetBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert nTagBtn != null : "fx:id=\"nTagBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert sideMenu != null : "fx:id=\"sideMenu\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert topBar != null : "fx:id=\"topBar\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert content != null : "fx:id=\"content\" was not injected: check your FXML file 'appWindow.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'appWindow.fxml'.";

        // Bind VBox width to ScrollPane viewport width
        sideMenu.prefWidthProperty().bind(sideMenuScrollPane.widthProperty());
        // sideMenuScrollPane = 100%
        sideMenu.minHeightProperty().bind(sideMenuScrollPane.viewportBoundsProperty().map(bounds -> bounds.getHeight()));

        // Hide progressIndicator
        progressIndicator.setVisible(false);
        // Turn off searchBtn
        searchBtn.setDisable(true);

        // Icons
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

        // Actions
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

        nTagBtn.setOnMouseEntered(e -> nTagBtn.setCursor(Cursor.HAND));
        nTagBtn.setOnMouseExited(e -> nTagBtn.setCursor(Cursor.DEFAULT));
        nTagBtn.setOnAction(e -> openNewTagPage());

        nSnippetBtn.setOnMouseEntered(e -> nSnippetBtn.setCursor(Cursor.HAND));
        nSnippetBtn.setOnMouseExited(e -> nSnippetBtn.setCursor(Cursor.DEFAULT));
        nSnippetBtn.setOnAction(e -> openNewSnippetPage());

        // Load gui
        loadSidebar();
        openHomePage();
    }

    @FXML
    private void searchAction() {
        String searchText = searchBar.getText();
        if(homeController != null) {
            homeController.loadSnippets(searchText.isBlank() ? null : searchText, null);
        }else {
            log.error("Search error: No HomeController");
        }
    }

    @FXML
    private void showAll() {
        homeController.loadSnippets(null, null);
    }

    @FXML
    private void showFavourites() {
        homeController.loadSnippets("aZ7pQw4LmX", null);
    }

    private void openNewTagPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/newTagPage.fxml"));
            Region newTagContent = fxmlLoader.load();

            NewTagController newTagController = fxmlLoader.getController();
            newTagController.setParentController(this);

            Scene scene = content.getScene();
            if(scene != null) {
                scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
            }

            content.getChildren().clear();

            AnchorPane.setTopAnchor(newTagContent, 0.0);
            AnchorPane.setBottomAnchor(newTagContent, 0.0);
            AnchorPane.setLeftAnchor(newTagContent, 0.0);
            AnchorPane.setRightAnchor(newTagContent, 0.0);

            content.getChildren().add(newTagContent);

            log.info("Switch to new-tag page");
        }catch(Exception e) {
            log.error("Unable to switch to new-tag page: ", e);
            throw new RuntimeException(e);
        }
    }

    private void openNewSnippetPage() {
        progressIndicator.setVisible(true);

        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/newSnippetPage.fxml"));
                Parent newSnippetContent = fxmlLoader.load();

                NewSnippetController newSnippetController = fxmlLoader.getController();
                newSnippetController.setParentController(AppController.this);

                Scene scene = content.getScene();
                if(scene != null) {
                    newSnippetContent.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
                }

                return newSnippetContent;
            }
        };

        loadTask.setOnSucceeded(e -> {
            Parent newSnippetContent = loadTask.getValue();

            content.getChildren().clear();

            AnchorPane.setTopAnchor(newSnippetContent, 0.0);
            AnchorPane.setBottomAnchor(newSnippetContent, 0.0);
            AnchorPane.setLeftAnchor(newSnippetContent, 0.0);
            AnchorPane.setRightAnchor(newSnippetContent, 0.0);

            content.getChildren().add(newSnippetContent);

            progressIndicator.setVisible(false);

            log.info("Switch to new-snippet page");
        });

        loadTask.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            Throwable ex = loadTask.getException();

            log.error("Unable to switch to new-snippet page: ", ex);
            throw new RuntimeException(ex);
        });

        new Thread(loadTask).start();
    }

    public void openHomePage() {
        progressIndicator.setVisible(true);

        Task<Parent> loadTask = new Task<>() {
            @Override
            protected Parent call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/homePage.fxml"));
                Parent homeContent = fxmlLoader.load();

                homeController = fxmlLoader.getController();
                homeController.setParentController(AppController.this);

                Scene scene = content.getScene();
                if(scene != null) {
                    homeContent.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
                }

                return homeContent;
            }
        };

        loadTask.setOnSucceeded(e -> {
            Parent homeContent = loadTask.getValue();

            content.getChildren().clear();

            AnchorPane.setTopAnchor(homeContent, 0.0);
            AnchorPane.setBottomAnchor(homeContent, 0.0);
            AnchorPane.setLeftAnchor(homeContent, 0.0);
            AnchorPane.setRightAnchor(homeContent, 0.0);

            content.getChildren().add(homeContent);

            searchBtn.setDisable(false);
            progressIndicator.setVisible(false);

            log.info("Switch to home page");
        });

        loadTask.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            Throwable ex = loadTask.getException();

            log.error("Unable to switch to home page: ", ex);
            throw new RuntimeException(ex);
        });

        new Thread(loadTask).start();
    }

    public void openDetailPage(DetailData detailData) {
        progressIndicator.setVisible(true);

        Task<DetailPageWrapper> loadTask = new Task<>() {
            @Override
            protected DetailPageWrapper call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/detailPage.fxml"));
                Parent detailContent = fxmlLoader.load();

                DetailPage controller = fxmlLoader.getController();
                controller.setParentController(AppController.this);

                return new DetailPageWrapper(detailContent, controller);
            }
        };

        loadTask.setOnSucceeded(e -> {
            DetailPageWrapper result = (DetailPageWrapper) loadTask.getValue();
            Parent detailContent = result.parent;
            DetailPage controller = result.controller;

            controller.setDetailData(detailData);

            content.getChildren().clear();

            AnchorPane.setTopAnchor(detailContent, 0.0);
            AnchorPane.setBottomAnchor(detailContent, 0.0);
            AnchorPane.setLeftAnchor(detailContent, 0.0);
            AnchorPane.setRightAnchor(detailContent, 0.0);

            content.getChildren().add(detailContent);

            progressIndicator.setVisible(false);
            log.info("Switched to detail page");
        });

        loadTask.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            Throwable ex = loadTask.getException();

            log.error("Unable to switch to detail page: ", ex);
            throw new RuntimeException(ex);
        });

        new Thread(loadTask).start();
    }

    public void loadSidebar() {
        Task<List<Tag>> task = new Task<>() {
            @Override
            protected List<Tag> call() {
                return tagRepository.getAllDistinct();
            }
        };

        task.setOnSucceeded(e -> {
            List<Tag> tags = task.getValue();

            ObservableList<Node> children = sideMenu.getChildren();
            if(children.size() > 2) {
                children.remove(2, children.size());
            }

            for(int i = 0; i < tags.size(); i++) {
                Tag tag = tags.get(i);

                Label nameLabel = new Label(tag.getName());
                HBox.setHgrow(nameLabel, Priority.ALWAYS);
                nameLabel.setMaxWidth(Double.MAX_VALUE);
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.getStyleClass().add("sideMenuBtnText");

                Button closeButton = new Button("×");
                closeButton.setFocusTraversable(false);
                closeButton.setMinWidth(20);
                closeButton.setPrefWidth(20);
                closeButton.setMaxHeight(20);
                closeButton.getStyleClass().add("closeBtn");
                closeButton.setOnAction(ev -> {
                    ev.consume();
                    tagRepository.delete(tag);
                    loadSidebar();
                    openHomePage();
                });

                HBox content = new HBox(nameLabel, closeButton);
                content.setSpacing(5);
                content.setAlignment(Pos.CENTER_LEFT);
                content.setPrefWidth(Double.MAX_VALUE);

                Button tagButton = new Button();
                tagButton.setGraphic(content);
                tagButton.setPrefWidth(Double.MAX_VALUE);
                tagButton.getStyleClass().add("sideMenuBtn");

                if(i % 2 == 0) {
                    tagButton.getStyleClass().add("even");
                }else {
                    tagButton.getStyleClass().add("odd");
                }

                tagButton.setOnAction(event -> {
                    homeController.loadSnippets(null, tag.getName());
                });

                sideMenu.getChildren().add(tagButton);
            }
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();

            log.error("Error loading sidebar: ", ex);
            throw new RuntimeException(ex);
        });

        new Thread(task).start();
    }
}