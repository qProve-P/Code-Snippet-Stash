package io.github.qprove_p.codesnippetstash.gui;


import io.github.qprove_p.codesnippetstash.storage.JPAConnector;
import jakarta.persistence.EntityManager;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

@Log4j2
public class Main extends Application {

    public static void main(String[] args) {
        log.info("Starting up");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        log.info("Loading GUI");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/appWindow.fxml"));
            Parent root = loader.load();

            log.info("GUI loaded");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/application.css")).toExternalForm());

            primaryStage.setTitle("Code Snippet Stash");

            primaryStage.getIcons().add(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/icons/icon-256.ico"))
            ));

            primaryStage.setScene(scene);
            primaryStage.show();

            preloadHibernate();

        }catch(IOException e) {
            log.error("Error loading GUI: ", e);
            throw new RuntimeException(e);
        }
    }

    private void preloadHibernate() {
        try {
            EntityManager em = JPAConnector.getEntityManager();
            em.close();

            log.info("Hibernate preloaded");
        }catch(Exception e) {
            e.printStackTrace();
            log.error("Error preloading Hibernate: ", e);
        }
    }
}