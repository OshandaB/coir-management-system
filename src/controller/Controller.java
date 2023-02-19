package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;


public class Controller {

    String userName="kaveen";
    @FXML
    Label lblName;

    @FXML
    Label lblPwd;
    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Hyperlink signupHyperLink;



    @FXML
    void signupClickHyper(ActionEvent event) throws IOException {
        signupHyperLink.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/signup.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.setTitle("ABC");
        stage.setScene(new Scene(root1,768, 543));
        stage.setResizable(false);
        stage.show();

    }


    @FXML
    void loginClick(ActionEvent event) throws IOException {

        try {
          //  CustomAnimationMessageBox customAnimationMessageBox=new CustomAnimationMessageBox();

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            String sql = "SELECT * FROM  admin WHERE admin_name=? AND password=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usernameTxt.getText());
            stmt.setString(2, passwordTxt.getText());

            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                Image img = new Image("assets/tick.gif", 96, 96, false, false);

                Notifications notificationBuilder = Notifications.create()
                        .title("Login ")
                        .text("Login Successful")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                passwordTxt.setText("");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashbord.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("ABC");
                stage.setScene(new Scene(root1,1360, 710));
                stage.setResizable(false);
                stage.show();



            }else{
                Image img = new Image("assets/icons8-error.gif", 96, 96, false, false);
                Notifications notificationBuilder = Notifications.create()
                    .title("Login ")
                    .text("Login UnSuccessful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3));
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                //JOptionPane.showMessageDialog(null,"Username or Password incorrect.Please try again");


            }
            if(usernameTxt.getText().isEmpty()){

                usernameTxt.setStyle("-fx-border-color: red;");
                 new Shake(usernameTxt).play();
                lblName.setText("UserName is empty");


                //customAnimationMessageBox.update("Username field is empty");



            }else{
                usernameTxt.setStyle(null);
                lblName.setText(null);

            }
            if(passwordTxt.getText().isEmpty()){
                passwordTxt.setStyle("-fx-border-color: red;");
                new Shake(passwordTxt).play();
                lblPwd.setText("Password is empty");


            }else{
                passwordTxt.setStyle(null);
                lblPwd.setText(null);
            }
            if(passwordTxt.getText().isEmpty() & usernameTxt.getText().isEmpty()){

                // JOptionPane.showMessageDialog(null,"Username and Password fields can't be empty");
                usernameTxt.setStyle("-fx-border-color: red;");
                new Shake(usernameTxt).play();
                passwordTxt.setStyle("-fx-border-color: red;");
                new Shake(passwordTxt).play();



            }





           /* int rows = stmt.executeUpdate();
            System.out.println(rows + " row(s) inserted.");
            con.close();*/

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }



    private class Shake {
        public Shake(Object usernameTxt) {
        }

        public void play() {

        }
    }
    public void goToGoogle(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("http://www.mce-coirproducts.com/contact.php"
               ));
    }
    public void goToFacebook(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/profile.php?id=100080635719508"));
    }
}
