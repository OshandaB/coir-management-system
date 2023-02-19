package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StartForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1,650, 500));

        stage.setResizable(false);
        stage.show();


        System.out.println("r");


    }


    public static void main(String[] args) {

        launch(args);
    }
}
