package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewTagController {

    @FXML
    private TextField nameField;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label newTagLabel;

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

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
        log.info("Closing new tag window");
    }
}
