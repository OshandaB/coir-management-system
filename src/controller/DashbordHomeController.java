package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class DashbordHomeController {
    public int count;
    public Label employeeCountLbl;
    @FXML
    private LineChart lineChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timeLbl;

    @FXML
    private Label date;



    @FXML
    void initialize() {
       Timenow();
       updateEmployeeCount();
        assert timeLbl != null : "fx:id=\"timeLbl\" was not injected: check your FXML file 'DashbordHome.fxml'.";
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 50),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
       // final pieChart chart = new pieChart(pieChartData);
        pieChart.setData(pieChartData);
        pieChart.setTitle("Imported Fruits");
        pieChart.setStartAngle(180);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");



        lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        series.getData().add(new XYChart.Data("Jan", 23));
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));

        lineChart.getData().add(series);

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
                timeLbl.setText(timenow);
               date.setText(timenow1);
             });
          }
       });
       thread.start();
    }


    public void tracking(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Tracking.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Vehicle Tracking");
        stage.setScene(new Scene(root1,1360, 710));
        stage.setResizable(false);
        stage.show();
    }
    public void updateEmployeeCount(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(order_id) FROM order_payment");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                employeeCountLbl.setText(count+" ");
            }

            connection.close();
        } catch (Exception e) {
            employeeCountLbl.setText("Error: " + e.getMessage());
        }

    }
 }
