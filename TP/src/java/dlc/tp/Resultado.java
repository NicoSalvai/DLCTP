/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tp;

/**
 *
 * @author dlcusr
 */
public class Resultado {
    
    private String title;
    private String content;
    private int id;

    public Resultado() {
    }

    public Resultado(String nombre, String descripcion) {
        this.title = nombre;
        this.content = descripcion;
        this.id = 0;
    }

    public String getNombre() {
        return title;
    }

    public void setNombre(String nombre) {
        this.title = nombre;
    }

    public String getDescripcion() {
        return content;
    }

    public void setDescripcion(String descripcion) {
        this.content = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
