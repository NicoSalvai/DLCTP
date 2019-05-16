
package motor;

import java.util.ArrayList;

/**
 *
 * @author dlcusr
 */
public class Buscador {
    
    private Object vocabulario;
    
    
    public Buscador(){
        
    }
    
    
    
    
    public ArrayList<Sitio> buscar(String busqueda){
        ArrayList<Sitio> resultados = new ArrayList<>();
        
        /*################################################3*/
        
        return resultados;
    }
    
    
    
    
    public boolean procesarSitio(Sitio newSite){
        this.loadSiteToDB(newSite);
        
        return true;
    }

    private void loadSiteToDB(Sitio newSite) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
