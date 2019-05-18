
package HashTable;

/**
 *
 * @author Nico Salvai
 */
import java.io.*;
import java.util.Hashtable;
public class TSB_OAHashtableReader {
    
      private String arch;
    
      public TSB_OAHashtableReader(){
          arch = "HashTable.dat";         
      }
      
      public TSB_OAHashtableReader(String nom)
      {
          arch = nom;
      }
      
      
      /**
       * Recupera una Hashtable desde un archivo serializado.
       * @return una referencia al objeto recuperado.
       */
      public Hashtable read()
      {
           Hashtable ht = null;
           
           try
           {
                 FileInputStream istream = new FileInputStream(arch);
                 ObjectInputStream p = new ObjectInputStream(istream);
          
                 ht = ( Hashtable ) p.readObject();
                 
                 p.close();
                 istream.close();
           }
           catch (Exception e)
           {
           }
           
           return ht;
    }
}
