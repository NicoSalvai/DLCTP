/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import HashTable.TSB_OAHashtable;
import control.CargaBD;
import file.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import motor.Buscador;
import motor.Sitio;
import postgress.ConnectionHandler;

/**
 *
 * @author Nicolas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        /*
        ConnectionHandler ch = new ConnectionHandler();
        
        ResultSet r = ch.runCommand("Select * FROM document");
        r.next();
        System.out.println(r.getString(1) + r.getString(2));*/
        ConnectionHandler ch = new ConnectionHandler();
        
        String aux = ch.runLastSiteID();
        if(aux == null) { aux = "0";}
        
        Sitio s = new Sitio("1ws5110.txt", Integer.valueOf(aux)+1);
        System.out.println(s.content + s.id + s.title);
        
        Buscador b = new Buscador();
        b.procesarSitio2(s);
        
        System.out.println("a");
    }
    
}
