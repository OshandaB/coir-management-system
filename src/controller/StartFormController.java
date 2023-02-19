package controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartFormController extends Thread{

    @FXML
    private JFXProgressBar progresBar;

    @FXML
    private AnchorPane anchorPane;

    public void run() {
        try {
            for (int i = 0; i <= 10; i++) {
                double x = i * (0.1);
                progresBar.setProgress(x);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> {
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(StartFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.setScene(new Scene(root));
                stage.setTitle("Coir Product System (Version 1.0.0)");
                stage.show();
                anchorPane.getScene().getWindow().hide();
            });

        } catch (Exception ex) {
            Logger.getLogger(StartFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void initialize() {
        start();
    }
}
