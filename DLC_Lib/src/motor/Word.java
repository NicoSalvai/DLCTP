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
public class Word {
    
    //################################################  CONSTANTES
    private final int DEFAULT_ID = 0;
    private final int DEFAULT_N = 0;
    private final int DEFAULT_TFMAX = 0;
    
    //########################################## VARIABLES
    private int id;
    private String word;
    private int n;
    private int tfmax;
    
    
    //########################################## CONSTRUCTURES
    public Word(String word){
        this.id = DEFAULT_ID;
        this.word = word;
        this.n = DEFAULT_N;
        this.tfmax = DEFAULT_TFMAX;
    }
    
    public Word(int id, String word, int n, int tfmax) {
        this.id = id;
        this.word = word;
        this.n = n;
        this.tfmax = tfmax;
    }
    
    
    
    
    //########################################## METODOS
    public void incrementarN(){
        this.n = this.n + 1;
    }
    
    public void nuevoTfMax(int tf){
        if(tfmax < tf){
            tfmax = tf;
        }
    }
    
    
    
    
    
    
    
    //########################################## GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getTfmax() {
        return tfmax;
    }

    public void setTfmax(int tfmax) {
        this.tfmax = tfmax;
    }
    
    
    
}
