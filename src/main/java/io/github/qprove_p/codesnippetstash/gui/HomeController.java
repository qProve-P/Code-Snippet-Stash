package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class HomeController implements Page {

    @FXML
    private AnchorPane homePage;

    @FXML
    private ScrollPane contentArea;

    @FXML
    private VBox scrollContent;

    private AppController parentController;
    private SnippetRepository snippetRepository = new SnippetRepository();

    @Override
    public void closeWindow() {
        return;
    }

    @Override
    public void setParentController(AppController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void initialize() {
        assert contentArea != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'homePage.fxml'.";
        assert scrollContent != null : "fx:id=\"scrollContent\" was not injected: check your FXML file 'homePage.fxml'.";
        assert homePage != null : "fx:id=\"homePage\" was not injected: check your FXML file 'homePage.fxml'.";

        scrollContent.prefWidthProperty().bind(contentArea.widthProperty());

        loadSnippets(null, null);
    }

    private boolean isColorCloserToWhite(Color color) {
        double luminance = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
        return luminance > 0.5;
    }

    @FXML
    public void loadSnippets(String search, String searchTag) {
        Task<List<Snippet>> task = new Task<>() {
            @Override
            protected List<Snippet> call() {
                if(searchTag != null) {
                    return snippetRepository.findByTag(searchTag);
                }
                if(search == null) {
                    return snippetRepository.findAll();
                }
                if(search.equals("aZ7pQw4LmX")) {
                    return snippetRepository.findByFavourite();
                }

                return snippetRepository.findByName(search);
            }
        };

        task.setOnSucceeded(e -> {
            List<Snippet> snippets = task.getValue();

            scrollContent.getChildren().clear();

            for(Snippet snippet : snippets) {
                HBox row = new HBox();
                row.setId("snippetContainer");
                row.setSpacing(10);
                row.prefWidthProperty().bind(scrollContent.widthProperty());

                Label snippetName = new Label(snippet.getName());
                snippetName.setId("snippetName");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox tagsBox = new HBox();
                tagsBox.setSpacing(5);

                for(Tag tag : snippet.getTags()) {
                    Label tagLabel = new Label(tag.getName());
                    Color tagColor = Color.web("#" + tag.getColor().substring(2, 8));

                    tagLabel.setStyle("-fx-background-color: #"+tag.getColor().substring(2, 8)+";-fx-padding: 5px 5px;-fx-background-radius: 6px;");

                    if(isColorCloserToWhite(tagColor)) {
                        tagLabel.setTextFill(Color.BLACK);
                    }else {
                        tagLabel.setTextFill(Color.WHITE);
                    }

                    tagsBox.getChildren().add(tagLabel);
                }

                row.getChildren().addAll(snippetName, spacer, tagsBox);

                row.setOnMouseClicked(event -> {
                    DetailData data = new DetailData(
                            snippet.getName(),
                            snippet.getCode(),
                            snippet.getTags()
                    );
                    parentController.openDetailPage(data);
                });

                scrollContent.getChildren().add(row);
            }
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();

            log.error("Error loading snippets", ex);
            throw new RuntimeException(ex);
        });

        new Thread(task).start();
    }
}
