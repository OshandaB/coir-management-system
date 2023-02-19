

//////////////////////////////////////////////////////////////
package controller;

        import java.io.IOException;
        import java.net.URL;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.ResourceBundle;

        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.BorderPane;
        import javafx.stage.Stage;

public class DashbordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label AdminTxt;

    @FXML
    private Button EmployeeBtn;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button exportBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView logout;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button stockBtn;

    @FXML
    private Button supppliersBtn;

    @FXML
    private Label time;

    @FXML
    private Label date;

    @FXML
    void employeeClick(ActionEvent event) throws IOException {
        System.out.println("ok");
        AnchorPane view= FXMLLoader.load(getClass().getResource("/view/dashbordEmployee.fxml"));
        borderpane.setCenter(view);

    }

    @FXML
    void exportClick(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("/view/DashbordExport.fxml"));
        borderpane.setCenter(view);

    }

    @FXML
    void homeBtnCLick(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("/view/DashbordHome.fxml"));
        borderpane.setCenter(view);

    }

    @FXML
    void logoutClick(MouseEvent event) throws IOException {

        logout.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/sample.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.setTitle("ABC");
        stage.setScene(new Scene(root1,768, 543));
        stage.show();


    }

    @FXML
    void reportsClick(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("/view/DashbordOrder.fxml"));
        borderpane.setCenter(view);


    }

    @FXML
    void stockClick(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("/view/DashbordStock.fxml"));
        borderpane.setCenter(view);

    }

    @FXML
    void suppliersClick(ActionEvent event) throws IOException {
        AnchorPane view=FXMLLoader.load(getClass().getResource("/view/DashbordSuppliers.fxml"));
        borderpane.setCenter(view);

    }
    private void Timenow(){
        Thread thread =new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                 String timenow1 = sdf1.format(new Date());

                Platform.runLater(() ->{
                    time.setText(timenow);
                    date.setText(timenow1);
                });
            }
        });
            thread.start();
    }

    @FXML
    void initialize() {
        assert AdminTxt != null : "fx:id=\"AdminTxt\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert EmployeeBtn != null : "fx:id=\"EmployeeBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert borderpane != null : "fx:id=\"borderpane\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert exportBtn != null : "fx:id=\"exportBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert reportsBtn != null : "fx:id=\"reportsBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert stockBtn != null : "fx:id=\"stockBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
        assert supppliersBtn != null : "fx:id=\"supppliersBtn\" was not injected: check your FXML file 'Dashbord.fxml'.";
   Timenow();
    }


}

