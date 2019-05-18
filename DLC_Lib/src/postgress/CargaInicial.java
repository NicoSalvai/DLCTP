package postgress;

import file.FileReader;
import motor.Searcher;
import motor.Site;

/**
 *
 * @author dlcusr
 */
public class CargaInicial {
    
    String DEFAULT_TITLESFILE = "/home/dlcusr/NetBeansProjects/TPGit/DLCTP/DLC_Lib/DocumentosTP1/Documentos.txt";
    
    String titlesFile;
    
    public CargaInicial(){
        this.titlesFile = DEFAULT_TITLESFILE;
        
    }
    
    
    public void cargarX(int cantidad, Searcher b){
        FileReader file = new FileReader();
        String title = "";
        Site site;
        file.openFile(titlesFile);
        
        for (int i = 0; i < cantidad; i++) {
            
            if(file.hasNextToRead()){
                
                title = file.readLine();
                
                if(b.isSiteProcessed(title) == 0){
                    site = new Site(title);
                    b.procesarSitio(site);
                } else{
                    i--;
                }
            }
            
        }
        
    }
    
}
