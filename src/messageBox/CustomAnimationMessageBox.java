package messageBox;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomAnimationMessageBox {
    public  void update(String message) {
        // create label
        Label label = new Label(message);
        label.setWrapText(true);
        label.setPadding(new Insets(10));

        // create vbox
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.BASELINE_RIGHT);

        // create scene
        Scene scene = new Scene(vbox);

        // create stage
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        // create animation
        FadeTransition ft = new FadeTransition(Duration.seconds(3), vbox);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished(event -> stage.close());
        ft.play();
    }
}

