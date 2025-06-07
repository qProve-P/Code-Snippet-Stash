package io.github.qprove_p.codesnippetstash.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HomeController implements Page {

    @FXML
    private AnchorPane homePage;

    @FXML
    private ScrollPane contentArea;

    @FXML
    private GridPane scrollContent;

    private AppController parentController;

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
}
