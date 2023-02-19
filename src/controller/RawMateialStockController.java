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
import model.RawMaterialStock;
import org.controlsfx.control.Notifications;

public class RawMateialStockController {
    public int count;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ProductsaveBtn;

    @FXML
    private TextField QuantityTxt;

    @FXML
    private Label RawmaterialCount;

    @FXML
    private TableColumn<?, ?> RawmaterialDelete;

    @FXML
    private TableColumn<?, ?> RawmaterialId;

    @FXML
    private TableColumn<?, ?> RawmaterialName;

    @FXML
    private TableColumn<?, ?> RawmaterialUpdate;

    @FXML
    private Button clearBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane mainPaneAnchor;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<?, ?> quantity;

    @FXML
    private Button showBtn;

    @FXML
    private TableView<RawMaterialStock> table;

    private ObservableList<RawMaterialStock> list;

    Button updateBtn;
    Button deleteBtn;

    public void updateRawStockCount() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(material_id) FROM raw_material");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                RawmaterialCount.setText(count + " ");
            }

            // connection.close();
        } catch (Exception e) {
            RawmaterialCount.setText("Error: " + e.getMessage());
        }

    }

    @FXML
    void ProductsaveClick(ActionEvent event) {
        if(ProductsaveBtn.getText().equals("Save")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO raw_material ( material_id, material_name, quantity) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, QuantityTxt.getText());


                stmt.executeUpdate();
                //  System.out.println(rows + " row(s) inserted.");
                // con.close();
                updateRawStockCount();
                populateTableView();

                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Raw material Stock Add ")
                        .text("Raw material Stock Added Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();


                idTxt.setText("");
                nameTxt.setText("");
                QuantityTxt.setText("");


            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("not");
            }

        }else{
            Image img = new Image("assets/tick.gif", 96, 96, false, false);

            Notifications notificationBuilder = Notifications.create()
                    .title("Raw material Stock Update ")
                    .text("Raw material Stock Update Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
            notificationBuilder.darkStyle();
            notificationBuilder.show();

            update();
            updateData(idTxt.getText(),nameTxt.getText(),QuantityTxt.getText());
            populateTableView();
            ProductsaveBtn.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            QuantityTxt.setText("");
        }




    }

    public void populateTableView() {
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM raw_material";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                RawMaterialStock rawMaterialStock=new RawMaterialStock();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                rawMaterialStock.setProduct_id(resultSet.getString(1));
                rawMaterialStock.setProduct_name(resultSet.getString(2));
                rawMaterialStock.setQuantity(resultSet.getString(3));
                rawMaterialStock.setUpdateBtn(updateBtn);
                rawMaterialStock.setDeleteBtn(deleteBtn);

                list.add(rawMaterialStock);

                RawmaterialId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
                RawmaterialName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
                quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                RawmaterialUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                RawmaterialDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


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
                        .title("Raw material Stock Delete ")
                        .text("Raw material Stock Delete Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                RawMaterialStock materialId= table.getSelectionModel().getSelectedItem();
                table.getItems().remove(materialId);

                RawMateialStockController rawMateialStockController=new RawMateialStockController();
                try {
                    rawMateialStockController.removeItem(materialId.getProduct_id());
                    updateRawStockCount();
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
            stmt = connection.prepareStatement("DELETE FROM raw_material where material_id = ?");
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

        RawMaterialStock rawMaterialStock=table.getSelectionModel().getSelectedItem();
        idTxt.setText(rawMaterialStock.getProduct_id());
        nameTxt.setText(rawMaterialStock.getProduct_name());
        QuantityTxt.setText(rawMaterialStock.getQuantity());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String quantity)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update raw_material set material_id='"+id+"',material_name='"+name+"',quantity= '"+quantity+"'where material_id='"+id+"'";
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
        table.getItems().clear();

    }

    @FXML
    void showClick(ActionEvent event) {
        populateTableView();

    }

    @FXML
    void initialize() {
        populateTableView();
        updateRawStockCount();
        assert ProductsaveBtn != null : "fx:id=\"ProductsaveBtn\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert QuantityTxt != null : "fx:id=\"QuantityTxt\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert RawmaterialCount != null : "fx:id=\"RawmaterialCount\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert RawmaterialDelete != null : "fx:id=\"RawmaterialDelete\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert RawmaterialId != null : "fx:id=\"RawmaterialId\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert RawmaterialName != null : "fx:id=\"RawmaterialName\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert RawmaterialUpdate != null : "fx:id=\"RawmaterialUpdate\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert mainPaneAnchor != null : "fx:id=\"mainPaneAnchor\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert showBtn != null : "fx:id=\"showBtn\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'RawmaterialStock.fxml'.";

    }

}
