/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postgress;

import HashTable.TSB_OAHashtableReader;
import HashTable.TSB_OAHashtableWriter;
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
public class DBFilesConnection {
    
    //######################################################3   CONFIGURACION DEFAULT **CONSTANTES
    private final String DEF_CONNECTION ="jdbc:postgresql://localhost:5432/tpdb";
    private final String DEF_USR="dlcusr", DEF_PWD="dlcpwd";
    
    //######################################################3   VARIABLES CONFIGURACION ACTUAL
    private String connectionString, usr, pwd;
    private int site_nextID, word_nextID, post_nextID;

    
     //######################################################3   CONSTRUCTORES
    public DBFilesConnection(String connection, String usr, String pwd) {
        this.connectionString = connection;
        this.usr = usr;
        this.pwd = pwd;
        this.initializeIDs();
    }
    
    
    public DBFilesConnection() {
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
            Logger.getLogger(DBFilesConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sitio;
    }
    
    
    
    
    //######################################################3   COMUNES Post
    public void savePost(Hashtable<String,Integer> posteo, Site site){
        TSB_OAHashtableWriter writer = new TSB_OAHashtableWriter(site.getPost_path());
        writer.write(posteo);
    }
    
    
    
    
    //########################################################  Comunes Word
    public Hashtable<String,Word> getVocabularioCompleto(){
        Hashtable<String,Word> vocabulario = new Hashtable<>();
        
        TSB_OAHashtableReader reader = new TSB_OAHashtableReader("vocabulario.dat");
        vocabulario = reader.read();
        
        return vocabulario;
    }
    
    
    public void saveVocabulario(Hashtable<String,Word> vocabulario){
        TSB_OAHashtableWriter writer = new TSB_OAHashtableWriter("vocabulario.dat");
        writer.write(vocabulario);
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
            Logger.getLogger(DBFilesConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Integer.valueOf(aux)+1;
    }
    
}
