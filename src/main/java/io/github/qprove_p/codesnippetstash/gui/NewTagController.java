package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewTagController {

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

    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    private void closeWindow() {
        if(onCloseCallback != null) {
            onCloseCallback.run();
        }
        log.info("Closing new-tag page");
    }
}
