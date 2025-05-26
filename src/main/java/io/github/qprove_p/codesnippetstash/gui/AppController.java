package io.github.qprove_p.codesnippetstash.gui;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController {

    private static Logger log = LogManager.getLogger(AppController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
}
