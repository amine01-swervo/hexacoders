/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import Entities.Cart;
import Entities.User;
import Services.IServiceUser;
import Utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public class ServiceUser implements IServiceUser{
    
    Connection cnx;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    
    ObservableList<User>  UserList = FXCollections.observableArrayList();


    @Override
    public ObservableList<User> AfficheUser(String username) throws SQLException {
                connection = DbConnect.getConnect();
            
            query = "SELECT * FROM `user` where firstname = '"+username+"' ";
            System.out.println(query);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                UserList.add(new  User(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("firstname"),
                        resultSet.getInt("id")));
                
                
            }
            return UserList;
    }
    
    public ObservableList<User> ReturnList(){
        return UserList;
    }
    
}
