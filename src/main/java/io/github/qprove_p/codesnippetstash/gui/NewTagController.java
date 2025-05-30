package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewTagController implements Page {

    @FXML
    private TextField nameField;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label newTagLabel;

    private Runnable onCloseCallback;

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String color = colorPicker.getValue().toString();

        Tag tag = Tag.builder().name(name).color(color).build();

        new TagRepository().save(tag);

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
        log.info("Closing new-tag page");
    }

    @FXML
    void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert colorPicker != null : "fx:id=\"colorPicker\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert newTagLabel != null : "fx:id=\"newTagLabel\" was not injected: check your FXML file 'newTagPage.fxml'.";
    }
}
