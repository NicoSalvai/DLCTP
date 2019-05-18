/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

/**
 *
 * @author dlcusr
 */
public class Site {
    //##################################################    CONSTANTES
    private final String DOCUMENTS_PATH = "/home/dlcusr/NetBeansProjects/TPGit/DLCTP/DLC_Lib/DocumentosTP1/";
    
        //##################################################    Variables
    private String title;               //Titulo del documento
    private String path;                //Path al archivo que contiene el documento
    private int id;                     //id del documento en la BD
    private boolean processed;          //Si es que ya se proceso y se crearon sus posteos

  
     //##################################################    CONSTRUCTORES
    
    public Site(String nombre) {
        this(nombre,0,false);
    }
    
    
    public Site(String title, int id, boolean processed ) {
        this.title = title;
        this.path = this.DOCUMENTS_PATH + title + "";
        this.id = id;
        this.processed = processed;
    }
    
    

    
    
    
    
     //##################################################    GETTERS Y SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    
}
