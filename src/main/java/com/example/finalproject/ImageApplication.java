package com.example.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // initialize GUI for an image management tool
        FXMLLoader fxmlLoader = new FXMLLoader(ImageApplication.class.getResource("hello-view.fxml"));
        // Create a new stage that serves as the top-level container for the GUI
        // and sets the scene to the loaded view
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Image Management Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
