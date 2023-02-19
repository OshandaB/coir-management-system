


package controller;


import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

public class DashbordEmployeeController {
    public int count;



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField contatcNumberTxt;

    @FXML
    private Label employeeCountLbl;

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
    private Button clearBtn;


    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn< ?, ?> employeeDelete;

    @FXML
    private TableColumn<Employee, String> employeeId;

    @FXML
    private TableColumn<Employee, String> employeeName;

    @FXML
    private TableColumn<Employee, String> employeeSalary;

    @FXML
    private TableColumn<?, ?> employeUpdate;

    @FXML
    private TableColumn<Employee, String> employeeAddress;

    @FXML
    private TableColumn<Employee, String> employeeContact;

    ///////////////////
    private ObservableList<Employee> list;



    Button updateBtn;
    Button deleteBtn;




    public void updateEmployeeCount(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(employee_id) FROM employee");

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


    //insert into database
    @FXML
    void saveClick(ActionEvent event) {
        if(saveBtn.getText().equals("Save")){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO employee ( employee_id, employee_name, address,contact_number,salary) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, idTxt.getText());
                stmt.setString(2, nameTxt.getText());
                stmt.setString(3, addressTxt.getText());
                stmt.setString(4, contatcNumberTxt.getText());
                stmt.setString(5, salaryTxt.getText());

                int rows = stmt.executeUpdate();
                System.out.println(rows + " row(s) inserted.");
                con.close();
                updateEmployeeCount();
                populateTableView();
                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Employee Add ")
                        .text("Employee Added Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                idTxt.setText("");
                nameTxt.setText("");
                addressTxt.setText("");
                contatcNumberTxt.setText("");
                salaryTxt.setText("");



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
            updateData(idTxt.getText(),nameTxt.getText(),addressTxt.getText(),contatcNumberTxt.getText(),salaryTxt.getText());
            populateTableView();
            saveBtn.setText("Save");
            idTxt.setText("");
            nameTxt.setText("");
            addressTxt.setText("");
            contatcNumberTxt.setText("");
            salaryTxt.setText("");
        }


    }

    //end

    //insert data into table
    public void populateTableView(){
        list= FXCollections.observableArrayList();
        String query="SELECT * FROM employee";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Employee employee=new Employee();
                updateBtn=new Button("Update");
                deleteBtn=new Button("Delete");
                updateBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #2D033B;-fx-text-fill:WHITE;-fx-font-weight: bold;");

                employee.setEmployee_id(resultSet.getString(2));
                employee.setEmployee_name(resultSet.getString(3));
                employee.setSalary(resultSet.getString(6));
                employee.setAddress(resultSet.getString(4));
                employee.setContact_number(resultSet.getString(5));
                employee.setUpdateBtn(updateBtn);
                employee.setDeleteBtn(deleteBtn);

                list.add(employee);

                employeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeName.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                employeeContact.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
                employeUpdate.setCellValueFactory(new PropertyValueFactory<>("updateBtn"));
                employeeDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

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

    //Delete action
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
                        .title("DELETE  ")
                        .text("Employee Delete Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();

                Employee empid = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(empid);

                DashbordEmployeeController dashbordEmployeeController=new DashbordEmployeeController();
                try {
                    dashbordEmployeeController.removeItem(empid.getEmployee_id());
                    updateEmployeeCount();
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
            stmt = connection.prepareStatement("DELETE FROM employee where employee_id = ?");
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

        Employee employee=table.getSelectionModel().getSelectedItem();
        idTxt.setText(employee.getEmployee_id());
        nameTxt.setText(employee.getEmployee_name());
        addressTxt.setText(employee.getAddress());
        contatcNumberTxt.setText(employee.getContact_number());
        salaryTxt.setText(employee.getSalary());


    }

    //end

    //Updata database

    public void updateData(String id ,String name,String address,String contact,String salary)  {
        System.out.println(name);
        System.out.println(id);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "update employee set employee_id= '"+id+"',employee_name='"+name+"',address= '"+address+"'" +
                    ",contact_number= '"+contact+"',salary='"+salary+"'where employee_id='"+id+"' ";
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

    //clear table
    @FXML
    void clearClick(ActionEvent event) {
        table.getItems().clear();

    }
    //end



    @FXML
    void initialize() {
        populateTableView();
        updateEmployeeCount();

        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert contatcNumberTxt != null : "fx:id=\"contatcNumberTxt\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert employeeCountLbl != null : "fx:id=\"employeeCountLbl\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert salaryTxt != null : "fx:id=\"salaryTxt\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'dashbordEmployee.fxml'.";

    }


}

