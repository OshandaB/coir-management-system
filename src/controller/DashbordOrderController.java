package controller;


import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

//import model.Buyer;
//import model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Employee;
import model.Order;
import org.controlsfx.control.Notifications;

public class DashbordOrderController {
    public int count;
    public TextField idTxt;
    public TextField addressTxt;
    public TextField contatcNumberTxt;
    public TextField salaryTxt;
    public TextField nameTxt;
    public Button saveBtn;
    public TableColumn<Order,String> OrderId;
    public TableColumn<Order,String> BuyerId;
    public TableColumn<Order,String> OrderDate;
    public TableColumn<Order,String> OrderTime;
    public TableColumn<Order,String> Payment;
    public TableColumn< ?, ?> orderUpdate;
    public TableColumn< ?, ?> orderDelete;
    public Button showBtn;
    public Button clearBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableView<Order> table;

    private ObservableList<Order> list;

    Button updateBtn;
    Button deleteBtn;
/*
    public void updatesBuyerCount() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(order_id) FROM order_payment;");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count);
                //CountryCountLbl.setText(count + " ");
            }

            //  connection.close();
        } catch (Exception e) {
            CountryCountLbl.setText("Error: " + e.getMessage());
        }

    }*/
    ///insert data into table
    public void populateTableView() {
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM order_payment";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Order order=new Order();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                order.setOrder_id(resultSet.getString(1));
                order.setBuyer_id(resultSet.getString(2));
                order.setOrder_date(resultSet.getString(3));
                order.setOrder_time(resultSet.getString(4));
                order.setPayment(resultSet.getString(5));
                order.setUpdateBtn(updateBtn);
                order.setDeleteBtn(deleteBtn);

                list.add(order);

                OrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
                BuyerId.setCellValueFactory(new PropertyValueFactory<>("buyer_id"));
                OrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
                OrderTime.setCellValueFactory(new PropertyValueFactory<>("order_time"));
                Payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
                orderUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                orderDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


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
                Order orderId = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(orderId);

               DashbordOrderController dashbordOrderController = new DashbordOrderController();
                try {
                    dashbordOrderController.removeItem(orderId.getOrder_id());
                    //updatesBuyerCount();
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
            stmt = connection.prepareStatement("DELETE FROM order_payment where order_id = ?");
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


    @FXML
    void saveClick(ActionEvent event) {
        if(saveBtn.getText().equals("Save")) {
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO order_payment ( order_id, buyer_id, order_date,order_time,payment) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, addressTxt.getText());
                stmt.setString(4, salaryTxt.getText());
                stmt.setString(5, contatcNumberTxt.getText());

                stmt.executeUpdate();
                //  con.close();
                //updatesBuyerCount();
                populateTableView();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Image img = new Image("assets/tick.gif", 96, 96, false, false);

            Notifications notificationBuilder = Notifications.create()
                    .title("Update")
                    .text("Employee Update Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            update();
            updateData(idTxt.getText(),nameTxt.getText(),addressTxt.getText(),salaryTxt.getText(),contatcNumberTxt.getText());
            populateTableView();
            saveBtn.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            addressTxt.setText("");
            contatcNumberTxt.setText("");
            salaryTxt.setText("");
        }


    }

    @FXML
    void clearClick(ActionEvent event) {
        table.getItems().clear();


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


    public void updateClick() throws SQLException {
        saveBtn.setText("Update");
        //ProductsaveBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;-fx-font-weight: bold;");

        Order order=table.getSelectionModel().getSelectedItem();
        idTxt.setText(order.getOrder_id());
        nameTxt.setText(order.getBuyer_id());
        addressTxt.setText(order.getOrder_date());
       salaryTxt.setText(order.getOrder_time());
        contatcNumberTxt.setText(order.getPayment());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String address,String contact,String salary)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update order_payment set order_id= '"+id+"',buyer_id='"+name+"',order_date= '"+address+"'" +
                    ",order_time= '"+contact+"',payment='"+salary+"'where order_id='"+id+"' ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.execute();


        } catch (Exception e) {
            System.out.println("ok");
        }
    }

    @FXML
    void showClick(ActionEvent event) {
        populateTableView();

    }

    @FXML
    void initialize() {
        populateTableView();
        //updatesBuyerCount();
        assert OrderDate != null : "fx:id=\"BuyerAddrss\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert Payment != null : "fx:id=\"BuyerContact\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert orderDelete != null : "fx:id=\"BuyerDelete\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert OrderTime != null : "fx:id=\"BuyerEmail\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert BuyerId != null : "fx:id=\"BuyerId\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert OrderId!= null : "fx:id=\"BuyerName\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert saveBtn != null : "fx:id=\"BuyerSave\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert orderUpdate != null : "fx:id=\"BuyerUpdate\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
       // assert CountryCountLbl != null : "fx:id=\"CountryCountLbl\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert clearBtn != null : "fx:id=\"clearBtn\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert contatcNumberTxt != null : "fx:id=\"contatcNumberTxt\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert salaryTxt != null : "fx:id=\"emailTxt\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert showBtn != null : "fx:id=\"showBtn\" was not injected: check your FXML file 'DashbordOrder.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'DashbordOrder.fxml'.";

    }

}
