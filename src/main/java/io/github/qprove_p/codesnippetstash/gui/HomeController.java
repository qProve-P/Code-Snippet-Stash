package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class HomeController implements Page {

    @FXML
    private AnchorPane homePage;

    @FXML
    private ScrollPane contentArea;

    @FXML
    private GridPane scrollContent;

    private AppController parentController;
    private SnippetRepository snippetRepository;

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

    }

    @FXML
    private void loadSnippets() {
        Task<List<Snippet>> task = new Task<>() {
            @Override
            protected List<Snippet> call() {
                return snippetRepository.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            List<Snippet> snippets = task.getValue();
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();

            log.error("Error loading snippets", ex);
            throw new RuntimeException(ex);
        });
    }
}
