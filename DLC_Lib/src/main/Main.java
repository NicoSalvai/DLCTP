/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import HashTable.TSB_OAHashtable;
import file.FileReader;

/**
 *
 * @author Nicolas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TSB_OAHashtable<String, Integer> aux = new TSB_OAHashtable<>();
        
        
        FileReader file = new FileReader();
        String key;
        int val, i =0;
        
        file.openFile("C:\\Users\\Nicolas\\Downloads\\DLC\\DLC_Lib\\DocumentosTP1\\00ws110.txt");
        
        while(file.hasNextToRead()){
            //System.out.print(i);
            key = file.readNextWord();
            val = aux.getOrDefault(key, 0) + 1;
            
            
            aux.put(key, val);
            
        }
        System.out.println(aux);
    }
    
}
