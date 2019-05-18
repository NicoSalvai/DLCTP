
package HashTable;

/**
 *
 * @author Nico Salvai
 */
import java.io.*;
import java.util.Hashtable;

public class TSB_OAHashtableWriter {
    // nombre del archivo serializado...
      private String arch;
      
    
      public TSB_OAHashtableWriter(){
          arch = "HashTable.dat";
      }
      
      public TSB_OAHashtableWriter(String nom){
            arch = nom;
      }
      
      /**
       * Graba la tabla tomada como parametro.
       * @param ht la lista a serializar.
     * @return 
       */
      public boolean write (Hashtable ht)
      {
           try
           {
             FileOutputStream ostream = new FileOutputStream(arch);
             ObjectOutputStream p = new ObjectOutputStream(ostream);
             
             p.writeObject(ht);
      
             p.flush(); 
             ostream.close();
             return true;
           }
           catch ( Exception e )
           {
               return false;
           }
      }
}
