/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import motor.Searcher;
import motor.Site;

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
        Site s = new Site("wrnpc10");
        
        b.procesarSitio(s);
        
    }
    
}
