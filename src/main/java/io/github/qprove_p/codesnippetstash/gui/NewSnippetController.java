package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class NewSnippetController implements Page {

    @FXML
    private TextField nameFieldS;

    @FXML
    private TextArea codeFieldS;

    @FXML
    private Label newSnippetLabel;

    @FXML
    private HBox topSnippetLabelBar;

    @FXML
    private ListView<Tag> tagSelectS;

    @FXML
    private Button createBtnS;

    @FXML
    private Button cancelBtnS;

    private AppController parentController;
    private TagRepository tagRepository = new TagRepository();

    @FXML
    private void handleSave() {
        String name = nameFieldS.getText();
        String code = codeFieldS.getText();

        Snippet snippet = Snippet.builder().name(name).code(code).tags(new ArrayList<>()).build();

        List<Tag> selectedTags = new ArrayList<>(tagSelectS.getSelectionModel().getSelectedItems());
        if(selectedTags.size() <= 4) {
            for(Tag tag : tagSelectS.getSelectionModel().getSelectedItems()) {
                snippet.addTag(tag);
            }
        }else {
            throw new RuntimeException("Max 4 tags");
        }

        new SnippetRepository().save(snippet);

        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    @Override
    public void setParentController(AppController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void closeWindow() {
        if(parentController != null) {
            parentController.openHomePage();
        }else {
            log.error("Unable to switch to home page");
        }
    }

    @FXML
    private void initialize() {
        assert nameFieldS != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert codeFieldS != null : "fx:id=\"codeField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert tagSelectS != null : "fx:id=\"tagSelect\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert newSnippetLabel != null : "fx:id=\"newSnippetLabel\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert topSnippetLabelBar != null : "fx:id=\"topSnippetLabelBar\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert createBtnS != null : "fx:id=\"createBtnS\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert cancelBtnS != null : "fx:id=\"cancelBtnS\" was not injected: check your FXML file 'newSnippetPage.fxml'.";

        createBtnS.setOnMouseEntered(e -> createBtnS.setCursor(Cursor.HAND));
        createBtnS.setOnMouseExited(e -> createBtnS.setCursor(Cursor.DEFAULT ));

        cancelBtnS.setOnMouseEntered(e -> cancelBtnS.setCursor(Cursor.HAND));
        cancelBtnS.setOnMouseExited(e -> cancelBtnS.setCursor(Cursor.DEFAULT ));

        loadTags();
    }

    private void loadTags() {
        Task<List<Tag>> task = new Task<>() {
            @Override
            protected List<Tag> call() {
                return tagRepository.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            List<Tag> tags = task.getValue();

            tagSelectS.getItems().clear();
            tagSelectS.setItems(FXCollections.observableArrayList(tags));

            tagSelectS.setCellFactory(listView -> new javafx.scene.control.ListCell<Tag>() {
                @Override
                protected void updateItem(Tag tag, boolean empty) {
                    super.updateItem(tag, empty);
                    if(empty || tag == null) {
                        setText(null);
                    }else {
                        setText(tag.getName());
                    }
                }
            });

            tagSelectS.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();

            log.error("Error loading tags", ex);
            throw new RuntimeException(ex);
        });

        new Thread(task).start();
    }
}
