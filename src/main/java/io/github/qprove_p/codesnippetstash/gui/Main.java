package io.github.qprove_p.codesnippetstash.gui;


import lombok.extern.log4j.Log4j2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

@Log4j2
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/appWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Code Snippet Stash");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}