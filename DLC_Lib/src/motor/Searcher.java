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
import postgress.DBFilesConnection;

/**
 *
 * @author dlcusr
 */
public class Searcher {
    
    //#################################################33 VARIABESL
    private Hashtable<String,Word> vocabulario;
    private DBFilesConnection DataBase;
    
    
    
    //############################################CONSTRUCTOR
    public Searcher(){
        DataBase = new DBFilesConnection();
        vocabulario = DataBase.getVocabularioCompleto();
        if(vocabulario == null){
            vocabulario = new Hashtable<>();
        }
        System.out.println("voc" + vocabulario.size());
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
        
        this.posteoToVocabulario(posteo, newSite);
        this.processedSite(newSite);
        
        return false;
    }
    
    
    
    
    public void posteoToVocabulario(Hashtable<String,Integer> posteo, Site site){
        Enumeration e = posteo.keys();
        String key;
        int val;
        
        
        while(e.hasMoreElements()){
            key = (String) e.nextElement();
            val = posteo.getOrDefault(key, 0);
            
            Word actualWord = vocabulario.getOrDefault(key, null);
            if(actualWord != null){
                actualWord.incrementarN();
                actualWord.nuevoTfMax(val);
                
            } else {
                actualWord = new Word(key,1,val);
                vocabulario.put(key, actualWord);
                
            }
            
        }
        this.savePost(posteo, site);
        this.saveVocabulario();
        /*##########################*/System.out.println("posteo" + posteo.size());
        /*##########################*/System.out.println("woirds" + vocabulario.size());
    }
    
     
    

    
    
    
    
    
    //###########################################METODOS RELACIONADOS A LA BD
    private void loadSiteToDB(Site newSite) {
        newSite.setId(this.DataBase.insertSite(newSite.getTitle(), newSite.getPath()));
    }
    
    private void processedSite(Site site){
        this.DataBase.processedSite(site.getTitle());
    } 
    
    public void saveVocabulario(){
        this.DataBase.saveVocabulario(vocabulario);
    }
    
    public void savePost(Hashtable<String,Integer> posteo, Site site){
        this.DataBase.savePost(posteo, site);
    }
}
