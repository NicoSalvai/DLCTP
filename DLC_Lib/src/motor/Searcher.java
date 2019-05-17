/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import java.util.ArrayList;
import java.util.Hashtable;
import postgress.DBConnection;

/**
 *
 * @author dlcusr
 */
public class Searcher {
    
    //#################################################33 VARIABESL
    private Hashtable<String,Word> vocabulario;
    private DBConnection DataBase;
    
    
    
    //############################################CONSTRUCTOR
    public Searcher(){
        vocabulario = new Hashtable<>();
        DataBase = new DBConnection();
    }
    
    
    
    public ArrayList<Site> buscar(String busqueda){
        return null;
    }
    
    
    
    
    
    
    
    
    public boolean procesarSitio(Site newSite){
        return false;
    }
    
    
    
     
    

    
    //###########################################METODOS RELACIONADOS A LA BD
    private void loadSiteToDB(Site newSite) {
        newSite.setId(this.DataBase.insertSite(newSite.getTitle(), newSite.getPath()));
    }
    
    private void processedSite(Site site){
        this.DataBase.processedSite(site.getTitle());
    }
    
    private void loadPosteoToDB(int siteID, int wordID, int tf) {
        this.DataBase.insertPost(siteID, wordID, tf);
    }
        
    private void loadVocabularioToDB(Word p) { 
        p.setId(this.DataBase.insertWord(p.getWord(), p.getN(), p.getTfmax()));
    }
    
     


}
