package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.EmailSend;

import java.awt.Toolkit;

public class DashbordStockControler {
    int count =0;

    @FXML
    private BorderPane borderPane1;
    @FXML
    private AnchorPane mainPain1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label meterLbl;

    @FXML
    private Button productionBtn;

    @FXML
    private Button rawMaterialBtn;

    @FXML
    private TextField meterTxt;

    @FXML
    private TextField levelSet;

   

    @FXML
    private AnchorPane mainPain;



    @FXML
    void productionClick(ActionEvent event) throws IOException {
//        System.out.println("ok");
//
   //   mainPain= FXMLLoader.load(getClass().getResource("/view/DashbordExport.fxml"));
    //  boderPane.setCenter(mainPain);
        Parent load = FXMLLoader.load(getClass().getResource("/view/ProductionStock.fxml"));
        mainPain1.getChildren().clear();
        mainPain1.getChildren().add(load);

    }




    @FXML
    void rawMaterialClick(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/RawmaterialStock.fxml"));
        mainPain1.getChildren().clear();
        mainPain1.getChildren().add(load);

    }

    public void metreSet(){

        Random rand=new Random();

        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                    int number=rand.nextInt(101);
                    meterTxt.setText(number+"");

                    if(number>80){
                        count ++;
                        if(count == 1) {
                            EmailSend.sendEmail("debugentity15@gmail.com");
                            levelSet.setText("Dannger Level High");

                        }


                    }else{
                        levelSet.setText("Dannger Level Low");

                    }
                }catch(Exception e){}



            }
        }).start();

    }

    @FXML
    void initialize() {
        metreSet();

        assert meterLbl != null : "fx:id=\"meterLbl\" was not injected: check your FXML file 'DashbordStock.fxml'.";
        assert productionBtn != null : "fx:id=\"productionBtn\" was not injected: check your FXML file 'DashbordStock.fxml'.";
        assert rawMaterialBtn != null : "fx:id=\"rawMaterialBtn\" was not injected: check your FXML file 'DashbordStock.fxml'.";

    }

    public void btnFire(ActionEvent event) {
        AudioClip buzzer = new AudioClip(getClass().getResource("/assets/fire-putha.mp3").toExternalForm());
        buzzer.stop();

    }


        AudioClip buzzer = new AudioClip(getClass().getResource("/assets/fire-putha.mp3").toExternalForm());

    public void img(MouseEvent mouseEvent) throws Exception {
        buzzer.play();


        Image img = new Image("assets/fire-puthaa.gif", 96, 96, false, false);
        Notifications notificationBuilder = Notifications.create()
                .title("Fire ")
                .text("Fire Warning")
                .graphic(new ImageView(img))
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(12));
        notificationBuilder.darkStyle();
        notificationBuilder.show();

    }

}
