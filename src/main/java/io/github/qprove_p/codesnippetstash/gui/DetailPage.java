package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Snippet;
import io.github.qprove_p.codesnippetstash.data.Tag;
import io.github.qprove_p.codesnippetstash.storage.DetailData;
import io.github.qprove_p.codesnippetstash.storage.SnippetRepository;
import io.github.qprove_p.codesnippetstash.storage.TagRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DetailPage implements Page {

    @FXML
    private VBox detailContent;

    @FXML
    private TextField detailName;

    @FXML
    private Button copyButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button toggleFavouriteButton;

    @FXML
    private TextArea detailCode;

    @FXML
    private ListView<Tag> detailTags;

    @FXML
    private Label detailTagsLabel;

    private AppController parentController;

    private DetailData data;
    private TagRepository tagRepository = new TagRepository();
    private SnippetRepository snippetRepository = new SnippetRepository();
    private boolean editable = false;
    private FontIcon favouriteIcon;

    @Override
    public void closeWindow() {
        if(parentController != null) {
            parentController.openHomePage();
        }else {
            log.error("Unable to switch to home page");
        }
    }

    @Override
    public void setParentController(AppController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        assert detailContent != null : "fx:id=\"detailContent\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailName != null : "fx:id=\"detailName\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert copyButton != null : "fx:id=\"copyButton\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert toggleFavouriteButton != null : "fx:id=\"toggleFavouriteButton\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailCode != null : "fx:id=\"detailCode\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailTags != null : "fx:id=\"detailTags\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailTagsLabel != null : "fx:id=\"detailTagsLabel\" was not injected: check your FXML file 'detailPage.fxml'.";

        // AnchorPane fill parent
        AnchorPane.setTopAnchor(detailContent, 0.0);
        AnchorPane.setBottomAnchor(detailContent, 0.0);
        AnchorPane.setLeftAnchor(detailContent, 0.0);
        AnchorPane.setRightAnchor(detailContent, 0.0);

        // Width 100%
        detailContent.setMaxWidth(Double.MAX_VALUE);

        // Hide elements on start
        saveButton.setManaged(false);
        saveButton.setVisible(false);
        detailTagsLabel.setVisible(false);

        // Icons
        FontIcon copyIcon = new FontIcon(FontAwesome.COPY);
        copyIcon.setIconSize(15);
        copyIcon.setIconColor(Color.web("#D9D9D9"));
        copyButton.setContentDisplay(ContentDisplay.RIGHT);
        copyButton.setGraphic(copyIcon);

        FontIcon editIcon = new FontIcon(FontAwesome.EDIT);
        editIcon.setIconSize(20);
        editIcon.setIconColor(Color.web("#D9D9D9"));
        editButton.setContentDisplay(ContentDisplay.RIGHT);
        editButton.setGraphic(editIcon);

        FontIcon deleteIcon = new FontIcon(FontAwesome.REMOVE);
        deleteIcon.setIconSize(20);
        deleteIcon.setIconColor(Color.web("#FF4A4A"));
        deleteButton.setContentDisplay(ContentDisplay.RIGHT);
        deleteButton.setGraphic(deleteIcon);

        FontIcon saveIcon = new FontIcon(FontAwesome.SAVE);
        saveIcon.setIconSize(20);
        saveIcon.setIconColor(Color.web("#D9D9D9"));
        saveButton.setContentDisplay(ContentDisplay.RIGHT);
        saveButton.setGraphic(saveIcon);

        favouriteIcon = new FontIcon(FontAwesome.HEART);
        favouriteIcon.setIconSize(20);
        toggleFavouriteButton.setContentDisplay(ContentDisplay.RIGHT);
        toggleFavouriteButton.setGraphic(favouriteIcon);

        // Actions
        copyButton.setOnMouseEntered(e -> copyButton.setCursor(Cursor.HAND));
        copyButton.setOnMouseExited(e -> copyButton.setCursor(Cursor.DEFAULT));
        copyButton.setOnAction(e -> copyCode());

        editButton.setOnMouseEntered(e -> editButton.setCursor(Cursor.HAND));
        editButton.setOnMouseExited(e -> editButton.setCursor(Cursor.DEFAULT));
        editButton.setOnAction(e -> editSnippet());

        deleteButton.setOnMouseEntered(e -> deleteButton.setCursor(Cursor.HAND));
        deleteButton.setOnMouseExited(e -> deleteButton.setCursor(Cursor.DEFAULT));
        deleteButton.setOnAction(e -> deleteSnippet());

        saveButton.setOnMouseEntered(e -> saveButton.setCursor(Cursor.HAND));
        saveButton.setOnMouseExited(e -> saveButton.setCursor(Cursor.DEFAULT));
        saveButton.setOnAction(e -> saveChanges());

        toggleFavouriteButton.setOnMouseEntered(e -> toggleFavouriteButton.setCursor(Cursor.HAND));
        toggleFavouriteButton.setOnMouseExited(e -> toggleFavouriteButton.setCursor(Cursor.DEFAULT));
        toggleFavouriteButton.setOnAction(e -> toggleFavourite(favouriteIcon));
    }

    public void setDetailData(DetailData detailData) {
        if(detailData != null) {
            data = detailData;

            detailName.setText(detailData.getDetailName());
            detailName.setEditable(false);
            detailCode.setText(detailData.getDetailCode());
            detailCode.setEditable(false);

            detailTags.setItems(FXCollections.observableArrayList(detailData.getDetailTags()));

            detailTags.setCellFactory(lv -> new ListCell<>() {
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

            favouriteBtnColor(favouriteIcon);
        }else {
            log.error("DetailData was null when trying to set detail page content");
        }
    }

    private void copyCode() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        String codeToCopy = detailCode.getText();
        content.putString(codeToCopy);

        clipboard.setContent(content);
    }

    private void editSnippet() {
        editable = !editable;

        if(editable) {
            detailName.setEditable(true);
            detailCode.setEditable(true);
            detailTags.setEditable(true);

            saveButton.setManaged(true);
            saveButton.setVisible(true);

            detailName.setStyle("-fx-background-color: #d9d9d9;");
            detailCode.setStyle("-fx-background-color: #d9d9d9;-fx-control-inner-background: #d9d9d9;");
            Node content = detailCode.lookup(".content");
            if(content != null) {
                content.setStyle("-fx-background-color: #d9d9d9;");
            }
            detailTags.setStyle("-fx-background-color: #d9d9d9;-fx-control-inner-background: #d9d9d9;");

            List<Tag> allTags = tagRepository.findAll();
            List<Tag> selectedTags = data.getDetailTags();

            detailTags.setItems(FXCollections.observableArrayList(allTags));
            detailTags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            for(Tag tag : selectedTags) {
                if(allTags.contains(tag)) {
                    detailTags.getSelectionModel().select(tag);
                }
            }
        }else {
            detailName.setEditable(false);
            detailCode.setEditable(false);
            detailTags.setEditable(false);

            saveButton.setManaged(false);
            saveButton.setVisible(false);

            detailName.setStyle("-fx-background-color: #b9b9b9;");
            detailCode.setStyle("-fx-background-color: #b9b9b9;-fx-control-inner-background: #b9b9b9;");
            Node content = detailCode.lookup(".content");
            if(content != null) {
                content.setStyle("-fx-background-color: #b9b9b9;");
            }
            detailTags.setStyle("-fx-background-color: #b9b9b9;-fx-control-inner-background: #b9b9b9;");

            List<Tag> selectedTags = data.getDetailTags();
            detailTags.setItems(FXCollections.observableArrayList(selectedTags));

            detailTags.getSelectionModel().clearSelection();
        }
    }

    private void saveChanges() {
        String newName = detailName.getText();
        String newCode = detailCode.getText();
        List<Tag> selectedTags = detailTags.getSelectionModel().getSelectedItems();

        data.setDetailName(newName);
        data.setDetailCode(newCode);
        data.setDetailTags(new ArrayList<>(selectedTags));

        try {
            snippetRepository.updateName(data.getSnippet(), data.getDetailName());
            snippetRepository.updateCode(data.getSnippet(), data.getDetailCode());
            snippetRepository.updateTags(data.getSnippet(), data.getDetailTags());
            log.info("Snippet updated successfully");
        }catch(Exception e) {
            log.error("Failed to update snippet", e);
        }

        editSnippet();
    }

    private void deleteSnippet() {
        Snippet snippet = data.getSnippet();

        snippetRepository.delete(snippet);

        parentController.openHomePage();
    }

    private void favouriteBtnColor(FontIcon icon) {
        Boolean isFavourite = data.isFavourite();

        if(!isFavourite) {
            icon.setIconColor(Color.web("#D9D9D9"));
        }else {
            icon.setIconColor(Color.web("#FF00CB"));
        }
    }

    private void toggleFavourite(FontIcon icon) {
        data.setFavourite(!data.isFavourite());
        snippetRepository.updateFavourite(data.getSnippet(), data.isFavourite());

        favouriteBtnColor(icon);

        log.info("Favourite updated successfully");
    }
}
