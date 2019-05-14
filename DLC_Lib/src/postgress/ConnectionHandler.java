/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgress;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nicolas
 */
public class ConnectionHandler {
    
    
    public ResultSet runCommand(String consulta){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "postgres")) {
             Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            return resultSet;
 
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return null;
    }
    
    
    public void loadDocument(){
        
    }
    
    public void run(String consulta){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "postgres")) {
             Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("id"), resultSet.getString("title"));
            }
 
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
