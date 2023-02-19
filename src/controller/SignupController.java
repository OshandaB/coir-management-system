package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController {


    @FXML
    private Button sigunupPopup;

    @FXML
    private Button signUp;

    @FXML
    private TextField idtxt;

    @FXML
    private TextField nametxt;

    @FXML
    private PasswordField passwordtxt;

    @FXML
    void btnPopup(MouseEvent event) {

    }

    @FXML
    private Hyperlink loginHyperLink;

    @FXML
    void loginClickHyper(ActionEvent event) throws IOException {
        loginHyperLink.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/sample.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.setTitle("ABC");
        stage.setScene(new Scene(root1,768, 543));
        stage.setResizable(false);
        stage.show();

    }


    @FXML
    void signUpClick(ActionEvent event) {
        System.out.println("ok");
        //connect database
        String id=idtxt.getText();
        String name=nametxt.getText();
        String password=passwordtxt.getText();
        if(id.isEmpty()) {
            JOptionPane.showMessageDialog(null,"id field can't be empty");

        }else if(name.isEmpty()){
            JOptionPane.showMessageDialog(null,"name field can't be empty");
        }else if(password.isEmpty()){
            JOptionPane.showMessageDialog(null,"password field can't be empty");
        }else {
            try {
                JOptionPane.showMessageDialog(null,"Signup successfull");
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
                String sql = "INSERT INTO admin (admin_id, admin_name, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, password);

                int rows = stmt.executeUpdate();
                System.out.println(rows + " row(s) inserted.");
                con.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            idtxt.setText("");
            passwordtxt.setText("");
            nametxt.setText("");
        }
    }
    //end
}


