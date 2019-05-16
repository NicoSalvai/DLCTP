
package motor;

import HashTable.TSB_OAHashtable;
import file.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import postgress.ConnectionHandler;

/**
 *
 * @author dlcusr
 */
public class Buscador {
    
    private Hashtable<String,Palabra> vocabulario;
    
    int nextWordID;
    
    public Buscador(){
        this.vocabulario = new Hashtable<>();
        nextWordID = this.nextWordID() + 1;
    }
    
    
    
    
    public ArrayList<Sitio> buscar(String busqueda){
        ArrayList<Sitio> resultados = new ArrayList<>();
        
        /*################################################3*/
        
        return resultados;
    }
    
    
    
    
    
    
    
    
     public boolean procesarSitio2(Sitio newSite){
        Hashtable<String,Integer> auxPosteo = new Hashtable<>();
        FileReader file = new FileReader();
        file.openFile(newSite.content);
        
        String key;
        int auxTf;
                
        while(file.hasNextToRead()){
            /*------------------Aux------------3*/
            key = file.readNextWord();
            auxTf = auxPosteo.getOrDefault(key, 0) + 1;
            auxPosteo.put(key, auxTf);
        }
        /*-----------------Vocabulario--------------------3*/
        this.loadSiteToDB(newSite);
        this.actualizarBDs(auxPosteo, newSite.id);
        
        /*################################################3*/
        
        return true;
    }
    
    
    
     
     
    private void actualizarBDs(Hashtable tfs, int siteID) {
        String key;
        int val;
        Enumeration e;
        e = tfs.keys();
        while(e.hasMoreElements()){
            key = e.nextElement().toString();
            val = (int) tfs.getOrDefault(key, 0);
            
            Palabra auxPalabra;
            if(!vocabulario.containsKey(key)){
                auxPalabra = new Palabra(nextWordID,key,0,0);
                nextWordID++;
                auxPalabra.incrementarN();
                auxPalabra.nuevoTfMax(val);
                vocabulario.put(key, auxPalabra);
                this.loadVocabularioToDB(auxPalabra);
            } else{
                auxPalabra = vocabulario.getOrDefault(key, null);
                auxPalabra.incrementarN();
                auxPalabra.nuevoTfMax(val);
                vocabulario.put(key, auxPalabra);
            }
            
            
            
            this.loadPosteoToDB(siteID, val, auxPalabra.getId());
        }
        
        
    }
    
    

    
    
    private void loadSiteToDB(Sitio newSite) {
        ConnectionHandler ch = new ConnectionHandler();
        ch.runAddSite(newSite.id, newSite.title, newSite.content);
    }
   
    private void loadPosteoToDB(int siteID, int tf, int wordID) {
        ConnectionHandler ch = new ConnectionHandler();
        ch.runAddPosteo(siteID, tf, wordID);
    }
    
    private void loadVocabularioToDB(Palabra p) { //int id, String word, int tfmax, int n
        ConnectionHandler ch = new ConnectionHandler();
        ch.runAddWord(p.getId(), p.getWord(), p.getTfmax(), p.getN());
    }
    
     
     
     
     
     private int nextWordID(){
        ConnectionHandler ch = new ConnectionHandler();
        String aux = "";
        
        
        try {
            aux = ch.runLastWordID();
        } catch (SQLException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( aux == null || "".equals(aux)){ return 0;}
        
        return Integer.valueOf(aux);
     }



}
