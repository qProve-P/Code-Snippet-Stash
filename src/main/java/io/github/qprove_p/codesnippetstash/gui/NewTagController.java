package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NewTagController implements Page {

    @FXML
    private TextField nameFieldT;

    @FXML
    private ColorPicker colorPickerT;

    @FXML
    private HBox topTagLabelBar;

    @FXML
    private Label newTagLabel;

    @FXML
    private Button createBtnT;

    @FXML
    private Button cancelBtnT;

    private AppController parentController;

    @FXML
    private void handleSave() {
        String name = nameFieldT.getText();
        String color = colorPickerT.getValue().toString();

        Tag tag = Tag.builder().name(name).color(color).build();

        new TagRepository().save(tag);

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
        try {
            parentController.openHomePage();
        }catch(Exception e) {
            log.error("Unable to switch to home page: ", e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert nameFieldT != null : "fx:id=\"nameField\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert colorPickerT != null : "fx:id=\"colorPicker\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert topTagLabelBar != null : "fx:id=\"topTagLabelBar\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert newTagLabel != null : "fx:id=\"newTagLabel\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert createBtnT != null : "fx:id=\"createBtnT\" was not injected: check your FXML file 'newTagPage.fxml'.";
        assert cancelBtnT != null : "fx:id=\"cancelBtnT\" was not injected: check your FXML file 'newTagPage.fxml'.";

        colorPickerT.setOnMouseEntered(e -> colorPickerT.setCursor(Cursor.HAND));
        colorPickerT.setOnMouseExited(e -> colorPickerT.setCursor(Cursor.DEFAULT ));

        newTagLabel.setOnMouseEntered(e -> newTagLabel.setCursor(Cursor.HAND));
        newTagLabel.setOnMouseExited(e -> newTagLabel.setCursor(Cursor.DEFAULT ));

        createBtnT.setOnMouseEntered(e -> createBtnT.setCursor(Cursor.HAND));
        createBtnT.setOnMouseExited(e -> createBtnT.setCursor(Cursor.DEFAULT ));

        cancelBtnT.setOnMouseEntered(e -> cancelBtnT.setCursor(Cursor.HAND));
        cancelBtnT.setOnMouseExited(e -> cancelBtnT.setCursor(Cursor.DEFAULT ));
    }
}
