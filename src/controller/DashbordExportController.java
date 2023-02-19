package controller;


import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Buyer;
//import model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

public class DashbordExportController {
    public int count;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> BuyerAddrss;

    @FXML
    private TableColumn<?, ?> BuyerContact;

    @FXML
    private TableColumn<?, ?> BuyerDelete;

    @FXML
    private TableColumn<?, ?> BuyerEmail;

    @FXML
    private TableColumn<?, ?> BuyerId;

    @FXML
    private TableColumn<?, ?> BuyerName;

    @FXML
    private Button BuyerSave;

    @FXML
    private TableColumn<?, ?> BuyerUpdate;

    @FXML
    private Label CountryCountLbl;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button clearBtn;

    @FXML
    private TextField contatcNumberTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private Button showBtn;

    @FXML
    private TableView<Buyer> table;

    private ObservableList<Buyer> list;

    Button updateBtn;
    Button deleteBtn;

    public void updatesBuyerCount() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(buyer_id) FROM buyer;");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                CountryCountLbl.setText(count + " ");
            }

            //  connection.close();
        } catch (Exception e) {
            CountryCountLbl.setText("Error: " + e.getMessage());
        }

    }
    ///insert data into table
    public void populateTableView() {
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM buyer";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Buyer buyer=new Buyer();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                buyer.setBuyer_id(resultSet.getString(2));
                buyer.setBuyer_name(resultSet.getString(3));
                buyer.setAddress(resultSet.getString(4));
                buyer.setEmail(resultSet.getString(5));
                buyer.setContact_number(resultSet.getString(6));
                buyer.setUpdateBtn(updateBtn);
                buyer.setDeleteBtn(deleteBtn);

                list.add(buyer);

                BuyerId.setCellValueFactory(new PropertyValueFactory<>("buyer_id"));
                BuyerName.setCellValueFactory(new PropertyValueFactory<>("buyer_name"));
                BuyerAddrss.setCellValueFactory(new PropertyValueFactory<>("address"));
                BuyerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                BuyerContact.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
                BuyerUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                BuyerDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


                table.setItems(list);
                deleteBtn();
                update();

            }



            connection.close();
        } catch (Exception e) {
            System.out.println("not");
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
                        .title("Buyer Add ")
                        .text("Buyer Delete Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                Buyer buyerId = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(buyerId);

                DashbordExportController dashbordExportController=new DashbordExportController();
                try {
                    dashbordExportController.removeItem(buyerId.getBuyer_id());
                    updatesBuyerCount();
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
            stmt = connection.prepareStatement("DELETE FROM buyer where buyer_id = ?");
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
        BuyerSave.setText("Update");
        //ProductsaveBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;-fx-font-weight: bold;");

        Buyer buyer=table.getSelectionModel().getSelectedItem();
        idTxt.setText(buyer.getBuyer_id());
        nameTxt.setText(buyer.getBuyer_name());
        addressTxt.setText(buyer.getAddress());
        contatcNumberTxt.setText(buyer.getContact_number());
        emailTxt.setText(buyer.getEmail());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String address,String contact,String email)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update buyer set buyer_id= '"+id+"',buyer_name='"+name+"',address= '"+address+"'" +
                    ",email= '"+email+"',contact_number='"+contact+"'where buyer_id='"+id+"' ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();


        } catch (Exception e) {
            System.out.println("ok");
        }
    }

    //end





    //end


    @FXML
    void BuyersaveClick(ActionEvent event) {
        if(BuyerSave.getText().equals("Save")){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO buyer ( buyer_id, buyer_name, address,email,contact_number) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, addressTxt.getText());
                stmt.setString(4, emailTxt.getText());
                stmt.setString(5, contatcNumberTxt.getText());

                stmt.executeUpdate();
                //  con.close();
                updatesBuyerCount();
                populateTableView();
                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Buyer Add ")
                        .text("Buyer Added Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                idTxt.setText("");
                nameTxt.setText("");
                addressTxt.setText("");
                contatcNumberTxt.setText("");
                emailTxt.setText("");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else{
            Image img = new Image("assets/tick.gif", 96, 96, false, false);

            Notifications notificationBuilder = Notifications.create()
                    .title("Delete")
                    .text("Buyer Update Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            update();
            updateData(idTxt.getText(),nameTxt.getText(),addressTxt.getText(),contatcNumberTxt.getText(),emailTxt.getText());
            populateTableView();
            BuyerSave.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            addressTxt.setText("");
            contatcNumberTxt.setText("");
            emailTxt.setText("");

        }


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
        updatesBuyerCount();
        assert BuyerAddrss != null : "fx:id=\"BuyerAddrss\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerContact != null : "fx:id=\"BuyerContact\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerDelete != null : "fx:id=\"BuyerDelete\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerEmail != null : "fx:id=\"BuyerEmail\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerId != null : "fx:id=\"BuyerId\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerName != null : "fx:id=\"BuyerName\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerSave != null : "fx:id=\"BuyerSave\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert BuyerUpdate != null : "fx:id=\"BuyerUpdate\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert CountryCountLbl != null : "fx:id=\"CountryCountLbl\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert contatcNumberTxt != null : "fx:id=\"contatcNumberTxt\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert emailTxt != null : "fx:id=\"emailTxt\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert showBtn != null : "fx:id=\"showBtn\" was not injected: check your FXML file 'DashbordExport.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'DashbordExport.fxml'.";

    }

}
