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
    
    private String connectionString ="jdbc:postgresql://localhost:5432/tpdb";
    private String usr="dlcusr", pwd="dlcpwd";
    
    
    public ConnectionHandler(){
        
    }
    
    
    
    
    
    
    public ResultSet runCommand(String consulta){
        try (Connection connection = DriverManager.getConnection(connectionString, usr, pwd)) {
             Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            return resultSet;
 
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    
    
    public void runCommandInsert(String consulta){
        try (Connection connection = DriverManager.getConnection(connectionString, usr, pwd)) {
             Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);
            
 
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
    
    public void runAddSite(int id, String title, String content){
            ConnectionHandler ch = new ConnectionHandler();
            this.runCommandInsert("INSERT INTO document (id, title, content)\n" + "values( "+ id +",'"+ title +"','"+ content +"');");
            
    }
}
