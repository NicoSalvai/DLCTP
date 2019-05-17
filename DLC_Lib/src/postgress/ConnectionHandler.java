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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    
    
    
    
    
    
    public void runAddSite( int id, String title, String content){
            ConnectionHandler ch = new ConnectionHandler();
            
            
        try {
            String aux = ch.runLastPosteoID();
            if(aux == null) { aux = "0";}
            int auxiliarID = Integer.valueOf(aux)+1;
            
            this.runCommandInsert("INSERT INTO document (id, title, content)\n" 
                    + "values( "+ id +",'"+ title +"','"+ content +"');");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    
    
    
    public void runAddPosteo(int documentID, int tf, int wordID){
        ConnectionHandler ch = new ConnectionHandler();
            
        try {
            String aux = ch.runLastPosteoID();
            if(aux == null) { aux = "0";}
            int auxiliar = Integer.valueOf(aux)+1;
        
            this.runCommandInsert("INSERT INTO posteo (id, word_id, document_id, tf) "
                    + "values(" + auxiliar + ","+ wordID +","+ documentID +","+ tf +");");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    public void runAddWord(int id, String word, int tfmax, int n){
        ConnectionHandler ch = new ConnectionHandler();
        
        
        try {
            String aux = ch.runLastPosteoID();
            if(aux == null) { aux = "0";}
            int auxiliarID = Integer.valueOf(aux)+1;
            
            this.runCommandInsert("INSERT INTO vocabulario (id, word, n, tfmax) "
                    + "values(" + id +",'" + word + "'," + n + "," + tfmax + ");");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String runLastSiteID() throws SQLException{
            ConnectionHandler ch = new ConnectionHandler();
            String aux = "";
            
            try{
                ResultSet r =  this.runCommand("SELECT max(id) FROM document;");
                r.next();
                aux = r.getString(1);
 
            } catch (SQLException e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
            }
            return aux;
    }
    
    
    
    public String runLastWordID() throws SQLException{
            ConnectionHandler ch = new ConnectionHandler();
            String aux = "";
            
            try{
                ResultSet r =  this.runCommand("SELECT max(id) FROM vocabulario;");
                r.next();
                aux = r.getString(1);
 
            } catch (SQLException e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
            }
            return aux;
    }
    
    
    public String runLastPosteoID() throws SQLException{
            ConnectionHandler ch = new ConnectionHandler();
            String aux = "";
            
            try{
                ResultSet r =  this.runCommand("SELECT max(id) FROM posteo;");
                r.next();
                aux = r.getString(1);
 
            } catch (SQLException e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
            }
            return aux;
    }
}
