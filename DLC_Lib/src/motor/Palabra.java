
package motor;

/**
 *
 * @author Nicolas
 */
public class Palabra implements Comparable{
    
    private String word;        //Identificador
    private float n;            //cantidad de documentos en que aparece el termino
    private float maxtf;        //maxima cantidad de estas en e1 documento
    private String postReference;   //direccion de la tabla de posteo

    public Palabra(String word, float n, float maxtf, String postReference) {
        this.word = word;
        this.n = n;
        this.maxtf = maxtf;
        this.postReference = postReference;
    }
    
    
    
    
    
    
    
    
    
    
    

    @Override
    public int hashCode() {
        return word.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
