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
    
    
    
    public static int updateResultado(Resultado resultado) throws Exception {
        if (resultado == null) {
            throw new Exception("CURSO ERROR: curso vacío.");
        }
        if (resultado.getNombre() == null || resultado.getNombre().equals("")) {
            throw new Exception("CURSO ERROR: curso incorrecto.");
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
            updateResultado(new Resultado("google", "Descripcion1"));
            updateResultado(new Resultado("facebook", "Descripcion3"));
            updateResultado(new Resultado("linkedin", "Descripcion2"));
        }
    }
}
