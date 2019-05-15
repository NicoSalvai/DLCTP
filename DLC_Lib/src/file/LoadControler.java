/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import HashTable.TSB_OAHashtable;
import HashTable.TSB_OAHashtableReader;
import HashTable.TSB_OAHashtableWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nicolas
 */
public class LoadControler {
    //Tabla a usar
    private TSB_OAHashtable<String,Integer> table;
    private LinkedList<String> files;
    private TSB_OAHashtableWriter writer;
    
    
    public boolean write(){
        
        if(writer == null){
            writer = new TSB_OAHashtableWriter();
        }
        return writer.write(table);
        
    }
    
    public TSB_OAHashtable<String, Integer> getTable(){
        return table;
        
    }
    
    
    /**
     * Guarda una tabla en el archivo cuyo nombre es pasado por parametro
     * @param file
     * @return true si se guardo con exito y false si no se pudo guardar
     */
    public boolean saveTable(String file){
        TSB_OAHashtableWriter rd = new TSB_OAHashtableWriter(file);
        boolean flag = rd.write(table);
        return flag;
    }    
    
          
    public boolean processFiles(List<File> files){
        
        if(files != null){
            
            for(File f: files){
                String path = f.getAbsolutePath();                
                if(!this.processFile(path)) return false;               
            }
            this.write();
            return true;
        }
        return false;
    }
    
    
    /**
     * hace toString a la tabla y lo devuelve
     * @return el toString de la tabla
     */
    public String watchTable(){
        return table.toString();
    }

    private boolean processFile(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
