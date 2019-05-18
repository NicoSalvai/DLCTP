/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;
import motor.Searcher;
import postgress.CargaInicial;

/**
 *
 * @author Nicolas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        Searcher b = new Searcher();
        CargaInicial charger = new CargaInicial();
        int op = 0;
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        
        
        while(op >= -1){
            System.out.println("cuanto cargo ");
            op = myObj.nextInt();  // Read user input
            
            if(op>0){
                charger.cargarX(op, b);
                
            }
        }
        
    }
    
}
