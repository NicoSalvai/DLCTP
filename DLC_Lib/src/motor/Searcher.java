/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import file.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
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
        vocabulario = DataBase.getVocabularioCompleto();
        
    }
    
    
    
    public ArrayList<Site> buscar(String busqueda){
        return null;
    }
    
    
    
    
    
    public int isSiteProcessed(String title){
        Site site;
        site = this.DataBase.getSite(title);
        if(site != null){
            return site.getId();
        }
        return 0;
    }
    
    
    
    
    //###################################       CARGA DE NUEVOS SITIOS
    public boolean procesarSitio(Site newSite){
        Hashtable<String,Integer> posteo = new Hashtable<>();
        FileReader file = new FileReader();
        String key;
        int val;
        
        this.loadSiteToDB(newSite);
        System.out.println(newSite.getTitle());
        
        file.openFile(newSite.getPath());
        
        while(file.hasNextToRead()){

            key = file.readNextWord();
            val = posteo.getOrDefault(key, 0) + 1;
            
            
            posteo.put(key, val);
            
        }
        
        this.posteoToVocabulario(posteo, newSite.getId());
        this.processedSite(newSite);
        
        return false;
    }
    
    
    
    
    public void posteoToVocabulario(Hashtable<String,Integer> posteo, int siteID){
        Enumeration e = posteo.keys();
        String key;
        int val;
        
        /*##########################*/System.out.println("posteo" + posteo);
        /*##########################*/System.out.println(posteo.size());
        
        while(e.hasMoreElements()){
            key = (String) e.nextElement();
            val = posteo.getOrDefault(key, 0);
            
            Word actualWord = vocabulario.getOrDefault(key, null);
            if(actualWord != null){
                actualWord.incrementarN();
                actualWord.nuevoTfMax(val);
                //this.loadVocabularioToDB(actualWord);
                
            } else {
                actualWord = new Word(key,1,val);
                this.loadVocabularioToDB(actualWord);
                vocabulario.put(key, actualWord);
                
            }
            
            this.loadPosteoToDB(siteID, actualWord.getId(), val);
        }
        /*##########################*/System.out.println("woirds" + vocabulario);
        /*##########################*/System.out.println(vocabulario.size());
    }
    
     
    

    
    
    
    
    
    //###########################################METODOS RELACIONADOS A LA BD
    private void loadSiteToDB(Site newSite) {
        newSite.setId(this.DataBase.insertSite(newSite.getTitle(), newSite.getPath()));
    }
        
    private void loadPosteoToDB(int siteID, int wordID, int tf) {
        this.DataBase.insertPost(siteID, wordID, tf);
    }
        
    private void loadVocabularioToDB(Word p) { 
        if(p.getId() == 0){
            p.setId(this.DataBase.insertWord(p.getWord(), p.getN(), p.getTfmax()));
        } else{
            this.DataBase.updateWord(p.getId(), p.getN(), p.getTfmax());
        }
        
    }
    
    
    private void processedSite(Site site){
        this.DataBase.processedSite(site.getTitle());
    } 
    
    public void actualizarVocabularioBD(){
        Enumeration v = vocabulario.elements();
        
        while(v.hasMoreElements()){
            Word w = (Word) v.nextElement();
            this.loadVocabularioToDB(w);
        }
    }


}
