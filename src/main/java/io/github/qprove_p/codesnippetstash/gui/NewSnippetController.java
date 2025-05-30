package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewSnippetController implements Page {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea codeField;

    @FXML
    private ListView<Tag> tagSelect;

    private Runnable onCloseCallback;

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String code = codeField.getText();

        Snippet snippet = Snippet.builder().name(name).code(code).build();
        for(Tag tag : tagSelect.getSelectionModel().getSelectedItems()) {
            snippet.addTag(tag);
        }

        new SnippetRepository().save(snippet);

        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    @Override
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    @Override
    public void closeWindow() {
        if(onCloseCallback != null) {
            onCloseCallback.run();
        }
        log.info("Closing new-snippet page");
    }

    @FXML
    private void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert codeField != null : "fx:id=\"codeField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert tagSelect != null : "fx:id=\"tagSelect\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
    }
}
