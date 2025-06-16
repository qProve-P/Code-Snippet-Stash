package io.github.qprove_p.codesnippetstash.gui;

import io.github.qprove_p.codesnippetstash.data.Tag;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DetailPage implements Page {

    @FXML
    private VBox detailContent;

    @FXML
    private TextField detailName;

    @FXML
    private TextArea detailCode;

    @FXML
    private ListView<Tag> detailTags;

    private AppController parentController;

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
        assert detailName != null : "fx:id=\"detailName\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailCode != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'detailPage.fxml'.";
        assert detailTags != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'detailPage.fxml'.";

        // AnchorPane fill parent
        AnchorPane.setTopAnchor(detailContent, 0.0);
        AnchorPane.setBottomAnchor(detailContent, 0.0);
        AnchorPane.setLeftAnchor(detailContent, 0.0);
        AnchorPane.setRightAnchor(detailContent, 0.0);

        // Width 100%
        detailContent.setMaxWidth(Double.MAX_VALUE);
    }

    public void setDetailData(DetailData detailData) {
        if(detailData != null) {
            detailName.setText(detailData.getDetailName());
            detailCode.setText(detailData.getDetailCode());

            detailTags.setItems(FXCollections.observableArrayList(detailData.getDetailTags()));
        }else {
            log.error("DetailData was null when trying to set detail page content");
        }
    }
}
