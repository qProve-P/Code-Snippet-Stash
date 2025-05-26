package io.github.qprove_p.codesnippetstash.gui;


import lombok.extern.log4j.Log4j2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

@Log4j2
public class Main extends Application {

    public static void main(String[] args) {
        log.info("Starting up");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        log.info("Loading GUI");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/appWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            log.info("GUI loaded");
        }catch(IOException e) {
            log.error("Error loading GUI: ", e);
            throw new RuntimeException(e);
        }

        primaryStage.setTitle("Code Snippet Stash");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}