/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dlc.tp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author dlcusr
 */
public abstract class Generador {
    
    private static Hashtable resultados;
    private static int sequence = 0;
    
    
    public static int nextID() {
        return ++sequence;
    }
    
    
    public static List getResultadosList() {
        Collection aux = getResultados().values();
        return new ArrayList(aux);
    }
    
    public static Hashtable getResultados() {
        if (resultados == null) resultados = new Hashtable();
        return resultados;
    }
    
    
    
    public static Site getResultado(int id){
        Site resultado = new Site("ylwlp10");
        return resultado;
    }
    
    
    
    public static int updateResultado(Site resultado) throws Exception {
        if (resultado == null) {
            throw new Exception("");
        }
        
        if (resultado.getTitle()== null || resultado.getTitle().equals("")) {
            throw new Exception("");
        }
        
        if (resultados == null) {
            resultados = new Hashtable();
        }
        
        int id = resultado.getId();
        if (id < 1) {
            id = nextID();
            resultado.setId(id);
        }

        resultados.put(id, resultado);
        
        return id;
    }
    
    
    
    
    
    /**
     * Genero resultados para test
     * se generan 3 resultados nada mas para visualizar
     * @throws Exception 
     */
    public static void populateResultados() throws Exception {
        if (resultados == null) resultados = new Hashtable();
        if (resultados.isEmpty()) {
            updateResultado(new Site("google"));
            updateResultado(new Site("facebook"));
            updateResultado(new Site("linkedin"));
        }
    }
}
