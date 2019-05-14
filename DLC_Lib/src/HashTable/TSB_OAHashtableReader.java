
package HashTable;

/**
 *
 * @author Nico Salvai
 */
import java.io.*;
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
      public TSB_OAHashtable read()
      {
           TSB_OAHashtable ht = null;
           
           try
           {
                 FileInputStream istream = new FileInputStream(arch);
                 ObjectInputStream p = new ObjectInputStream(istream);
          
                 ht = ( TSB_OAHashtable ) p.readObject();
                 
                 p.close();
                 istream.close();
           }
           catch (Exception e)
           {
           }
           
           return ht;
    }
}
