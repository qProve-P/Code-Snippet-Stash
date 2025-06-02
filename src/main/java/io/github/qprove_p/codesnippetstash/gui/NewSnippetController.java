package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewSnippetController implements Page {

    @FXML
    private TextField nameFieldS;

    @FXML
    private TextArea codeFieldS;

    @FXML
    private ListView<Tag> tagSelectS;

    @FXML
    private Button createBtnS;

    @FXML
    private Button cancelBtnS;

    private Runnable onCloseCallback;

    @FXML
    private void handleSave() {
        String name = nameFieldS.getText();
        String code = codeFieldS.getText();

        Snippet snippet = Snippet.builder().name(name).code(code).build();
        for(Tag tag : tagSelectS.getSelectionModel().getSelectedItems()) {
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
        assert nameFieldS != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert codeFieldS != null : "fx:id=\"codeField\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert tagSelectS != null : "fx:id=\"tagSelect\" was not injected: check your FXML file 'newSnippetPage.fxml'.";
        assert createBtnS != null : "fx:id=\"createBtnS\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert cancelBtnS != null : "fx:id=\"cancelBtnS\" was not injected: check your FXML file 'newTagPage.fxml'.";

        createBtnS.setOnMouseEntered(e -> createBtnS.setCursor(Cursor.HAND));
        createBtnS.setOnMouseExited(e -> createBtnS.setCursor(Cursor.DEFAULT ));

        cancelBtnS.setOnMouseEntered(e -> cancelBtnS.setCursor(Cursor.HAND));
        cancelBtnS.setOnMouseExited(e -> cancelBtnS.setCursor(Cursor.DEFAULT ));
    }
}
