/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import motor.Site;
import motor.Word;
import org.postgresql.util.PSQLException;

/**
 *
 * @author dlcusr
 */
public class DBConnection {
    
    //######################################################3   CONFIGURACION DEFAULT **CONSTANTES
    private final String DEF_CONNECTION ="jdbc:postgresql://localhost:5432/tpdb";
    private final String DEF_USR="dlcusr", DEF_PWD="dlcpwd";
    
    //######################################################3   VARIABLES CONFIGURACION ACTUAL
    private String connectionString, usr, pwd;
    private int site_nextID, word_nextID, post_nextID;

    
     //######################################################3   CONSTRUCTORES
    public DBConnection(String connection, String usr, String pwd) {
        this.connectionString = connection;
        this.usr = usr;
        this.pwd = pwd;
        this.initializeIDs();
    }
    
    
    public DBConnection() {
        this.connectionString = DEF_CONNECTION;
        this.usr = DEF_USR;
        this.pwd = DEF_PWD;
        this.initializeIDs();
    }
    
    
    
    //######################################################3   ENVIA SENTENCIA Y DEVUELVE EL RESULT SET 
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
    
    
    
    
    public void runCommandUpdate(String consulta, String parameter){
        try (Connection connection = DriverManager.getConnection(connectionString, usr, pwd)) {
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, parameter);
            statement.executeUpdate();
            return;
            
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } 
        return;
    }
    
    
    public void runCommandUpdate(String consulta){
        try (Connection connection = DriverManager.getConnection(connectionString, usr, pwd)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(consulta);
            return;
            
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } 
        return;
    }
    
    
    
    
    
    
    
    
    
    
    //######################################################3   COMUNES Site
    public int insertSite(String title, String filepath){
        
        this.runCommandUpdate("INSERT INTO site (id, title, filepath, processed)\n" +
                        "values(" + this.site_nextID + ",\'" + title + "\',\'" + filepath + "\', false);");
        
        this.site_nextID++;
        return this.site_nextID-1;
    }
    
    public void processedSite(String title){
        this.runCommandUpdate("UPDATE site\n" +
                        "SET processed = true\n" +
                        "WHERE title = '" + title + "';");
    }
    
    public Site getSite(String title){
        Site sitio = null;
        
        try {
            ResultSet rs = this.runCommand("SELECT * FROM site WHERE title = '" + title +"';");
            if(rs.next()){
                sitio = new Site(rs.getString(1),Integer.valueOf(rs.getString(1)),Boolean.valueOf(rs.getString(4)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sitio;
    }
    
    
    
    
    //######################################################3   COMUNES Post
    public int insertPost(int site_id, int word_id, int tf){
        this.runCommandUpdate("INSERT INTO post (id, site_id, word_id, tf)\n" +
                        "values(" + this.post_nextID + "," + site_id + "," + word_id + "," + tf + ");");
        
        this.post_nextID++;
        return this.post_nextID-1;
    }
    
    
    
    
    //########################################################  Comunes Word
    public int insertWord(String word, int n, int tfmax){
        this.runCommandUpdate("INSERT INTO word (id, word, n, tfmax)\n" +
                    "values(" + this.word_nextID + ", ? ," + n + "," + tfmax + ");", word);
        
        this.word_nextID++;
        return this.word_nextID-1;
    }
    
    public void updateWord(int id, int n, int tfmax){
        this.runCommandUpdate("UPDATE word\n" +
                    "SET n = " + n + ", tfmax = " + tfmax + "\n" +
                    "WHERE id = " + id + ";");
    }
    
    public Hashtable<String,Word> getVocabularioCompleto(){
        Word aux;
        Hashtable<String,Word> vocabulario = new Hashtable<>();
        
        try {
            ResultSet rs = this.runCommand("SELECT * FROM word;");
            while(rs.next()){
                aux = new Word(Integer.valueOf(rs.getString(1)),rs.getString(2),Integer.valueOf(rs.getString(3)),Integer.valueOf(rs.getString(4)));    //int id, String word, int n, int tfmax
                vocabulario.put(rs.getString(2), aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vocabulario;
    }
    
    
    
    
    
    
    
    
    
    
    //######################################################3   INICIALIZACION DE OBJETO
    public void initializeIDs(){
        site_nextID = getTableNextID("site");
        post_nextID = getTableNextID("post");
        word_nextID = getTableNextID("word");
    }
    
    
    public int getTableNextID(String table){
        String aux="";
        ResultSet rs;
        
        try {
            rs = this.runCommand("SELECT max(id) FROM " + table + ";");
            if(rs.next()){
                aux = rs.getString(1);
            }
            
            if(aux == null || aux == ""){ aux = "0";}
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Integer.valueOf(aux)+1;
    }
    
}
