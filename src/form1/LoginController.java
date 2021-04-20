/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form1;

import Entities.Product;
import Entities.User;
import Service.ServiceProduct;
import Utils.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author khamm
 */
public class LoginController implements Initializable {
    
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Product student = null ;
    
    

    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    private User getUserInDB(String userName, String password) {
		Connection connection = null;
		try {
			// create a database connection
			connection = DbConnect.getConnect();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			String getAllPersonString = "select * from user where firstname = '" + userName + "' and password = '"
					+ password + "'";

			ResultSet rs = statement.executeQuery(getAllPersonString);
			while (rs.next()) {
				User existedUser = new User();
				existedUser.username = rs.getString("firstName");
				
				
				existedUser.email = rs.getString("email");
				existedUser.password = rs.getString("password");

				return existedUser;
				// read the result set
				// System.out.println("name = " + rs.getString("firstName"));
				// System.out.println("personId = " + rs.getInt("personId"));
			}

		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return null;
	}

    
    

    @FXML
    private void Login(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        
        	// String password = Security.hashPassword(passField.getText());
		User existedUser = getUserInDB(tfUserName.getText(), tfPassword.getText());
		if (existedUser != null) {
                    
                    System.out.println();

			Alert alert = new Alert(Alert.AlertType.NONE, "Login Succesful! Welcome to TuniPharma", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
                            FXMLLoader Home = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                            Parent HomeParent = (Parent) Home.load();
                            Scene HomeScene = new Scene(HomeParent);
                            Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                            
                            FXMLDocumentController username = Home.getController();
                            username.sendData(tfUserName.getText());
                            
                            window.setScene(HomeScene);
                            window.show();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Login Failed!", ButtonType.YES);
			alert.show();
		}
    }
    
}
