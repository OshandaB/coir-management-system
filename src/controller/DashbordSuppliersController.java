package controller;



import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Supplier;
import org.controlsfx.control.Notifications;

public class DashbordSuppliersController {
    public int count;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> Email;

    @FXML
    private TableColumn<?, ?> SupplieAddress;

    @FXML
    private TableColumn<?, ?> SupplieContact;

    @FXML
    private Label SupplieCountLbl;

    @FXML
    private TableColumn<?, ?> SupplieDelete;

    @FXML
    private TableColumn<?, ?> SupplieName;

    @FXML
    private TableColumn<?, ?> SupplieUpdate;

    @FXML
    private TableColumn<?, ?> Supplierd;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button clearBtn;

    @FXML
    private TextField contactumberTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField salaryTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button showBtn;

    @FXML
    private TableView<Supplier> table;

    private ObservableList<Supplier> list;

    Button updateBtn;
    Button deleteBtn;


    public void updatesuppliersCount() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(supplier_id) FROM supplier");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                SupplieCountLbl.setText(count + " ");
            }

            // connection.close();
        } catch (Exception e) {
            SupplieCountLbl.setText("Error: " + e.getMessage());
        }

    }


    @FXML
    void clearClick(ActionEvent event) {
        table.getItems().clear();

    }

    @FXML
    void saveClick(ActionEvent event) {
        if(saveBtn.getText().equals("Save")){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO supplier ( supplier_id, supplier_name, address,contact_number,email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, addressTxt.getText());
                stmt.setString(4, contactumberTxt.getText());
                stmt.setString(5, salaryTxt.getText());

                stmt.executeUpdate();
                //  System.out.println(rows + " row(s) inserted.");
                // con.close();
               updatesuppliersCount();
              populateTableView();

                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Suppplier Add ")
                        .text("Supplier Added Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();


                idTxt.setText("");
                nameTxt.setText("");
                addressTxt.setText("");
                contactumberTxt.setText("");
                salaryTxt.setText("");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            Image img = new Image("assets/tick.gif", 96, 96, false, false);

            Notifications notificationBuilder = Notifications.create()
                    .title("Supplier Update ")
                    .text("Supplier Update Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
            notificationBuilder.darkStyle();
            notificationBuilder.show();

            update();
            updateData(idTxt.getText(),nameTxt.getText(),addressTxt.getText(),contactumberTxt.getText(),salaryTxt.getText());
            populateTableView();
            saveBtn.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            addressTxt.setText("");
            contactumberTxt.setText("");
            salaryTxt.setText("");
        }



    }

    public void populateTableView() {
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM supplier";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Supplier supplier=new Supplier();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                supplier.setSupplier_id(resultSet.getString(1));
                supplier.setSupplier_name(resultSet.getString(2));
                supplier.setAddress(resultSet.getString(3));
                supplier.setContact_number(resultSet.getString(4));
                supplier.setEmail(resultSet.getString(5));
                supplier.setUpdateBtn(updateBtn);
                supplier.setDeleteBtn(deleteBtn);

                list.add(supplier);

                Supplierd.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
                SupplieName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
                SupplieAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                SupplieContact.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
                Email.setCellValueFactory(new PropertyValueFactory<>("email"));
                SupplieUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                SupplieDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


                table.setItems(list);
                deleteBtn();
                update();
            }



            //connection.close();
        } catch (Exception e) {
            System.out.println("not");
        }
    }

    //end

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
                        .title("Supllier Delete ")
                        .text("Supplier Delete Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                Supplier suplierId = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(suplierId);

                DashbordSuppliersController dashbordSuppliersController=new DashbordSuppliersController();
                try {
                    dashbordSuppliersController.removeItem(suplierId.getSupplier_id());
                    updatesuppliersCount();
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
            stmt = connection.prepareStatement("DELETE FROM supplier where supplier_id = ?");
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
        saveBtn.setText("Update");
        //ProductsaveBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;-fx-font-weight: bold;");

        Supplier supplier=table.getSelectionModel().getSelectedItem();
        idTxt.setText(supplier.getSupplier_id());
        nameTxt.setText(supplier.getSupplier_name());
        addressTxt.setText(supplier.getAddress());
        contactumberTxt.setText(supplier.getContact_number());
        salaryTxt.setText(supplier.getEmail());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String address,String contact,String email)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update supplier set supplier_id= '"+id+"',supplier_name='"+name+"',address= '"+address+"'" +
                    ",contact_number= '"+contact+"',email='"+email+"'where supplier_id='"+id+"' ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();


        } catch (Exception e) {
            System.out.println("ok");
        }
    }

    //end


    @FXML
    void showClick(ActionEvent event) {
        populateTableView();

    }

    @FXML
    void initialize() {
        populateTableView();
        updatesuppliersCount();
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieAddress != null : "fx:id=\"SupplieAddress\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieContact != null : "fx:id=\"SupplieContact\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieCountLbl != null : "fx:id=\"SupplieCountLbl\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieDelete != null : "fx:id=\"SupplieDelete\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieName != null : "fx:id=\"SupplieName\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert SupplieUpdate != null : "fx:id=\"SupplieUpdate\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert Supplierd != null : "fx:id=\"Supplierd\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert contactumberTxt != null : "fx:id=\"contactumberTxt\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert salaryTxt != null : "fx:id=\"salaryTxt\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert showBtn != null : "fx:id=\"showBtn\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'DashbordSuppliers.fxml'.";

    }

}
