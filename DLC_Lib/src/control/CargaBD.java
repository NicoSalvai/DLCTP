/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import file.FileReader;
import java.io.BufferedReader;
import postgress.ConnectionHandler;

/**
 *
 * @author dlcusr
 */



public class CargaBD {
    private BufferedReader reader;
    private static String documentsPath = "/home/dlcusr/NetBeansProjects/TPGit/DLCTP/DLC_Lib/DocumentosTP1/";
    
    public CargaBD(){
        
    }
       
    
    
    public static boolean documentsToDB(int max, int from){
        FileReader file = new FileReader();
        String fileName;
        int i= 0;
        
        file.openFile(documentsPath + "Documentos.txt");
        
        while(file.hasNextToRead() && i<max+from){
            //System.out.print(i);
            
            i++;
            fileName = file.readNextWord();
            if(i>from){
                
                System.out.println(fileName);
                cargarDocumento(fileName,documentsPath + fileName,i);
            }
        }
        
        
        return true;
        
    }
    
    
    
    
    
    
    
    public static void cargarDocumento(String nombre, String texto, int id){
        ConnectionHandler ch = new ConnectionHandler();
        ch.runCommandInsert("INSERT INTO document (id, title, content)\n" + "values( "+ id +",'"+ nombre +"','"+ texto +"');");
    }
}
