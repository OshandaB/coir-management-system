package controller;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.ProductionStock;
import org.controlsfx.control.Notifications;

public class ProductionStockController {
    public int count;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ProductsaveBtn;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button clearBtn;

    @FXML
    private TableColumn<?, ?> employeUpdate;

    @FXML
    private Label employeeCountLbl;

    @FXML
    private TableColumn<?, ?> employeeDelete;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane mainPaneAnchor;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<?, ?> productId;

    @FXML
    private TableColumn<?, ?> productName;

    @FXML
    private TableColumn<?, ?> quantity;

    @FXML
    private Button showBtn;

    @FXML
    private TableView<ProductionStock> table;

    private ObservableList<ProductionStock> list;

    Button updateBtn;
    Button deleteBtn;


    public void updateStocksCount() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(product_id) FROM product");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                employeeCountLbl.setText(count + " ");
            }

            // connection.close();
        } catch (Exception e) {
            employeeCountLbl.setText("Error: " + e.getMessage());
        }

    }

    @FXML
    void ProductsaveClick(ActionEvent event) {

        if(ProductsaveBtn.getText().equals("Save")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO product ( product_id, product_name, available_amount) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, addressTxt.getText());


                stmt.executeUpdate();
                //  System.out.println(rows + " row(s) inserted.");
                // con.close();
                 updateStocksCount();
                populateTableView();

                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Production Stock Add ")
                        .text("Production Stock Added Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();


                idTxt.setText("");
                nameTxt.setText("");
                addressTxt.setText("");


            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("not");
            }

        }else{
            Image img = new Image("assets/tick.gif", 96, 96, false, false);

            Notifications notificationBuilder = Notifications.create()
                    .title("Production Stock Update ")
                    .text("Production Stock Update Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
            notificationBuilder.darkStyle();
            notificationBuilder.show();

            System.out.println("click");
            update();
            updateData(idTxt.getText(),nameTxt.getText(),addressTxt.getText());
            populateTableView();
            ProductsaveBtn.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            addressTxt.setText("");


        }

    }

    //insert data into table
    public void populateTableView() {
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM product";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                ProductionStock productionStock=new ProductionStock();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                productionStock.setProduct_id(resultSet.getString(1));
                productionStock.setProduct_name(resultSet.getString(2));
                productionStock.setQuantity(resultSet.getString(3));
                productionStock.setUpdateBtn(updateBtn);
                productionStock.setDeleteBtn(deleteBtn);

                list.add(productionStock);

                productId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
                productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
                quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                employeUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                employeeDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


                table.setItems(list);
                deleteBtn();
                update();
            }



            //connection.close();
        } catch (Exception e) {
            System.out.println("wrong");
        }
    }

    //end

    //Delete Action
    public void deleteBtn(){
        deleteBtn.setOnAction(event -> {
            System.out.println("clicked");
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure to delete?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Production Stock Delete ")
                        .text("Production Stock Delete Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                ProductionStock productId= table.getSelectionModel().getSelectedItem();
                table.getItems().remove(productId);

                ProductionStockController productioStockController=new ProductionStockController();
                try {
                    productioStockController.removeItem(productId.getProduct_id());
                    updateStocksCount();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

    }

    //end delete action

    //delete from database
    public void removeItem(String itemId) throws SQLException {
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            stmt = connection.prepareStatement("DELETE FROM product where product_id = ?");
            stmt.setString(1, itemId);
            stmt.execute();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(stmt != null){
                stmt.close();
            }
        }

    }

    //end

    public void update(){
        updateBtn.setOnAction(event -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure to update ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                try {
                    updateClick();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        });
    }

    //end

    //update Click
    public void updateClick() throws SQLException {
        ProductsaveBtn.setText("Update");
        //ProductsaveBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;-fx-font-weight: bold;");

        ProductionStock productionStock=table.getSelectionModel().getSelectedItem();
        idTxt.setText(productionStock.getProduct_id());
        nameTxt.setText(productionStock.getProduct_name());
        addressTxt.setText(productionStock.getQuantity());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String quantity)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update product set product_id= '"+id+"',product_name='"+name+"',available_amount= '"+quantity+"'where product_id='"+id+"' ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();


        } catch (Exception e) {
            System.out.println("ok");
        }
    }

    //end





    @FXML
    void backClick(MouseEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashbordStock.fxml"));
        mainPaneAnchor.getChildren().clear();
        mainPaneAnchor.getChildren().add(load);

    }

    @FXML
    void clearClick(ActionEvent event) {

    }

    @FXML
    void showClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        populateTableView();
        updateStocksCount();
        assert ProductsaveBtn != null : "fx:id=\"ProductsaveBtn\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert employeUpdate != null : "fx:id=\"employeUpdate\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert employeeCountLbl != null : "fx:id=\"employeeCountLbl\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert employeeDelete != null : "fx:id=\"employeeDelete\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert mainPaneAnchor != null : "fx:id=\"mainPaneAnchor\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert productId != null : "fx:id=\"productId\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert productName != null : "fx:id=\"productName\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert showBtn != null : "fx:id=\"showBtn\" was not injected: check your FXML file 'ProductionStock.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'ProductionStock.fxml'.";

    }

}
