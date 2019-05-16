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
public class Sitio {
    public static final String folder = "/home/dlcusr/NetBeansProjects/TPGit/DLCTP/DLC_Lib/DocumentosTP1/";
    
    public String title;
    public String content;
    public int id;

    public Sitio(String title, String content, int id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }
    
    public Sitio(String title, int id){
        this.title = title;
        this.content = folder + title;
        this.id = id;
    }
    
}
