/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import HashTable.TSB_OAHashtable;
import HashTable.TSB_OAHashtableReader;
import HashTable.TSB_OAHashtableWriter;
import file.FileReader;
import motor.Palabra;

/**
 *
 * @author Nicolas
 */
public class Controlador {
    
    private TSB_OAHashtable<String,Integer> vocabulario;    // <Word,WordStats>
    private TSB_OAHashtableWriter writer;
    
    //#####################################3    AUXILIARES
    private TSB_OAHashtable<String,Integer> aux;         // <word,tf> para este documento
    
    
    
    /**
     * Contructor del Controller, decide si crear una tabla nueva o no (
     * generalmente porque se cargara una)
     * @param newTable - indicador para crear una tabla nueva
     */
    public Controlador(boolean newTable){
        if(newTable){
            vocabulario = new TSB_OAHashtable<>();
        }
    }
    
    public Controlador(){
        
    }
    
    
    
    
    
    /**
     * Procesa el archivo cuya direccion es pasada por parametro
     * agregando cada palabra por separado a la tabla.
     * @param fileDirectory - directorio del archivo a procesar
     * @return
     */
    public boolean processDocument(String fileDirectory){
        if(aux == null) { return false;}
        FileReader file = new FileReader();
        String key;
        int val;
        
        file.openFile(fileDirectory);
        
        while(file.hasNextToRead()){
            //System.out.print(i);
            key = file.readNextWord();
            val = aux.getOrDefault(key, 0) + 1;
            
            
            aux.put(key, val);
            
        }
        return true;
    }

    
}
