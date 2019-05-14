
package HashTable;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class TSB_OAHashtable<K,V> implements Map<K,V>, Cloneable, Serializable{
    //
    
    /**
     * Enumerador que contiene los posibles estado que puede tener
     * una entrada de la TSB_OAHashtable
     */
    private enum EntryState{
        OPEN,
        CLOSED,
        TOMB;
        
    }
    
    //***************************************************************CONSTANTES
    // el valor maximo que podra tener el arreglo de soporte
    private transient final static int MAX_SIZE = Integer.MAX_VALUE;
    // el valor default que tenedra el arreglo de soporte
    private transient final static int DEFAULT_SIZE = 11;
    // el valor default que tomara el factor de carga
    private transient final static float DEFAULT_LOAD_FACTOR = 0.5f;
    
    // el DEFAULT_LOAD_FACTOR = 0.5 sería conveniente para evitar usar una
    // exploracion lineal.
    //private final static float DEFAULT_LOAD_FACTOR = 0.5f;
    
    
    //********************************************************ATRIBUTOS PRIVADOS
    //El arreglo de soporte que contendra la Tabla de Hash
    private Object tabla[]; //Almacenara objetos que implementen Map.Entry<K, V>
    
    //La cantidad de objetos de objetos que hay en la tabla.
    private int count;
    
    //El tamaño inicial de la tabla de hash
    private int initialSize;
    
    //El factor de carga para saber si es necesario hacer ReHashing
    private float loadFactor;
    
    //Atributos para gestionar las 3 vistas stateless
    private transient Set<K> keySet;
    private transient Set<Map.Entry<K,V>> entrySet;
    private transient Collection<V> values;
    
    //Atributo para fail fast iterators..
    protected transient int modCount;
    
    
    //*******************************************************CONSTRUCTORES
    /**
     * Constructor por defecto sin parametros, crea una tabla vacia con tamaño
     * igual a DEFAULT_SIZE y factor de carga igual a DEFAULT_LOAD_FACTOR
     */
    public TSB_OAHashtable() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
        
    }
    
    
    /**
     * Constructor con tamaño inicial, crea una tabla vacia con el tamaño
     * indicado  y factor de carga igual a DEFAULT_LOAD_FACTOR
     * @param initialSize el tamaño inicial de la tabla
     * @throws IllegalArgumentException - si initialsize es menor o igual que 0
     */
    public TSB_OAHashtable(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }
    
    
    /**
     * Constructor con tamaño inicial y factor de carga inicial, crea una tabla
     * vaia con el tamaño y el factor de carga indicados.
     * si el tamaño indicado es mayor que MAX_SIZE se usara MAX_SIZE como
     * tamaño inicial
     * @param initialSize - el tamaño inicial de la tabla
     * @param loadFactor - el factor de carga de la tabla
     * @throws IllegalArgumentException - si initialsize es menor o igual que 0
     * o si loadFactor es menor que 0 o mayor que 1.
     */
    public TSB_OAHashtable(int initialSize, float loadFactor) {
        
        if(loadFactor <= 0 || loadFactor >= 1) { throw new IllegalArgumentException(); }
        if(initialSize <= 0) { throw new IllegalArgumentException(); }
        else if(initialSize > TSB_OAHashtable.MAX_SIZE) {
            initialSize = TSB_OAHashtable.MAX_SIZE;
        }
        if(!this.esPrimo(initialSize)) initialSize = this.siguientePrimo(initialSize);
        
        this.tabla = new Object[initialSize];
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.count = 0;
        this.modCount = 0;
        
        for(int i = 0; i< this.tabla.length; i++){
            this.tabla[i] = EntryState.OPEN;
        }
        
    }
    
    
    /**
     * crea una la tabla apartir del contenido especificado por el Map "t"
     * pasado por parametro
     * @param t el Map que contiene los valores a incluir en la tabla
     * @throws NullPointerException - si "t" es igual a null
     */
    public TSB_OAHashtable(Map<? extends K,? extends V> t) {
        this();
        this.putAll(t);
    }
    
    
    /**
     * Retorna la cantidad de elementos que posee la tabla
     * @return La cantidad de elementos que posee la tabla
     */
    @Override
    public int size() {
        return this.count;
    }
    
    
    /**
     * comprueba si la tabla esta vacia o no
     * @return true si la tabla esta vacia, false si la tabla no esta vacia
     */
    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }
    
    
    /**
     * determina si  alguna clave que se encuentre en la tabla se encuentra
     * asociada al value (o) pasado por parametro
     * @param o - el value a buscar en la tabla
     * @return true si alguna clave esta asociada a ese value
     */
    public boolean contains(Object o) {
        return this.containsValue(o);
    }
    
    
    /**
     * Comprueba si la tabla contiene la clave que es pasada como parametro
     * @param key - la clave a buscar
     * @return  true si la clave se encuentra en la tabla, false sino
     */
    @Override
    public boolean containsKey(Object key){
        return (this.get((K)key) != null);
    }
    
    
    /**
     * determina si  alguna clave que se encuentre en la tabla se encuentra
     * asociada al value (o) pasado por parametro
     * @param o - el value a buscar en la tabla
     * @return true si alguna clave esta asociada a ese value
     */
    @Override
    public boolean containsValue(Object o) { // o es un value
        
        if(o == null) return false;
        Set<Map.Entry<K,V>> entryS = this.entrySet();
        
        Iterator<Map.Entry<K,V>> it = entryS.iterator();
        
        while(it.hasNext()){
            Map.Entry<K,V> aux = it.next();
            if(aux.getValue().equals(o)) return true;
        }
        
        return false;
    }
    
    
    /**
     * Retorna el value de la entrada correspondiente al key pasado por parametro
     * @param key la clave a buscar dentro de la tabla
     * @return el value correspondiente a la clave pasada por parametro, o null si no existe
     */
    @Override
    public V get(Object key) {
        
        if(key == null) return null;
        
        try{
            
            int itCount = 0;
            int idx = this.h((K)key, itCount);
            
            while(!tabla[idx].equals(EntryState.OPEN)){
                
                if(!tabla[idx].equals(EntryState.TOMB)){
                    Map.Entry<K,V> current_entry =(Map.Entry<K, V>) tabla[idx];
                    if(current_entry.getKey().equals(key)) return current_entry.getValue();
                    
                }
                itCount++;
                idx = this.h((K)key, itCount);
            }
            return null;
            
            
        } catch(ClassCastException ex){
            return null;
        }
    }
    
    
    /**
     * retorna el valor al que esta mappeado la clave especificada, o retorna
     * defaultValue si la clave no se encuentra en la tabla
     * @param key - la clave a buscar en la tabla
     * @param defaultValue - el valor a retornar si la clave no esta en la tabla
     * @return el valor asociado a la clave pasada por parametro en la tabla,
     * o el valor defaultValue si la clave no se encuentra en la tabla.
     */
    @Override
    public V getOrDefault(Object key, V defaultValue){
        V auxVal;
        auxVal = this.get(key);
        if(auxVal == null) return defaultValue;
        return auxVal;
    }
    
    
    /**
     *
     * @return
     */
    public boolean necesitaRehash(){
        float res = (float)(count + 1)/(float)tabla.length;
        return res >= this.loadFactor;
    }
    
    
    /**
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        
        if(key == null || value == null) throw new NullPointerException("put(): parámetro null");
        // obtiene la posicion que debería tomar la nueva entrada con la funcion hash
        if(this.necesitaRehash()) {
            this.rehash();
        }
        //int idx = this.h(key);
        Map.Entry<K,V> entry = new TSBOAEntry(key, value);
        int itCount = 0;
        while(true){
            
            int idx = this.h(key, itCount);
            // si el elemento en la posición del indice está abierta o es una tumba
            // lo añade
            if(tabla[idx].equals(EntryState.OPEN) || tabla[idx].equals(EntryState.TOMB)){
                
                tabla[idx] = entry;
                modCount++;
                count++;
                return null;
            } else{
                // si no está abierto ni es una tumba entonces es una entrada
                // compara la clave de la entrada que está en el idx con la clave
                // a insertar
                Map.Entry<K,V> temp = (Map.Entry<K, V>) tabla[idx];
                if(temp.getKey().equals(key)){
                    V old = temp.getValue();
                    temp.setValue(value);
                    return old;
                }
            }
            itCount++;
            
            
        }
        
    }
    
    
    /**
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public V putIfAbsent(K key, V value){
        return this.put(key, value);
    }
    
    
    /**
     * Reemplaza el value de la entry de la clave especificada si y solo si esta asociada
     * al valor especificado
     * @param key - clave especificada
     * @param oldValue - valor especificado
     * @param newValue - nuevo valor
     * @return true si el valor fue reemplazado
     */
    @Override
    public boolean replace(K key, V oldValue, V newValue){
        if(key == null || oldValue == null || newValue == null) throw new NullPointerException();
        V auxVal;
        auxVal = this.get(key);
        if(auxVal == null) return false; //la clave no esta en la tabla retorna false
        if(auxVal != oldValue) return false; // el value de la tabla no es igual al oldValue
        auxVal = this.put(key, newValue);
        return auxVal != null;
    }
    
    
    /**
     * Reemplaza la entry de la clave especificada si y solo si esta mapeada a algun valor
     * @param key - clave a buscar para reemplazar la entry
     * @param value - nuevo value para la entry
     * @return el valor asociado previamente con la clave o null si la key no estaba en la tabla
     */
    @Override
    public V replace(K key, V value){
        if(key == null || value == null) throw new NullPointerException(); //parametro null retorna null
        V auxVal;
        auxVal = this.get(key);
        if(auxVal == null) return null; //la clave no esta en la tabla retorna false
        return this.put(key, value);
    }
    
    
    /**
     * Elimina de la tabla la Entry que corresponda con la key enviada por parametro
     * @param key Clave de la Entry a ser eliminada
     * @return El value correspondiente a la entry eliminada o null si la entry no existe
     * @throws NullPointerException si key es null
     */
    @Override
    public V remove(Object key) {
        
        try{
            if(key == null) throw new NullPointerException();
            
            K temp_key = (K) key;
            
            int itCount = 0;
            int idx = this.h(temp_key,itCount);
            
            while(!tabla[idx].equals(EntryState.OPEN)){
                
                if(! tabla[idx].equals(EntryState.TOMB)){
                    Map.Entry<K,V> current_entry = (Map.Entry<K, V>) tabla[idx];
                    if(current_entry.getKey().equals(temp_key)){
                        tabla[idx] = EntryState.TOMB;
                        count--;
                        modCount++;
                        return current_entry.getValue();
                    }
                }
                itCount++;
                idx = this.h(temp_key,itCount);
            }
            return null;
        }catch(ClassCastException e){
            return null;
        }
        
    }
    
    /**
     * 
     * @param key La clave correspondiente a la Entry a eliminar
     * @param value El valor que debe tener la Entry para la clave especificada
     * @return true si existe una Entry con los key y value pasados por parametro,
     * o false si no existe o no puede eliminarlo
     * @throws NullPointerException si alguno de los parametros es null
     */
    
    @Override
    public boolean remove(Object key, Object value){
        
        if(key == null || value == null) throw new NullPointerException();
        
        try{
            K temp_key = (K)key;
            int itCount = 0;
            int index = this.h(temp_key, itCount);
            
            while(!tabla[index].equals(EntryState.OPEN)){
                
                if(!tabla[index].equals(EntryState.TOMB)){
                    Map.Entry<K,V> aux_entry = (Map.Entry<K, V>) tabla[index];
                    if(aux_entry.getKey().equals(temp_key) && aux_entry.getValue().equals(value)){
                        tabla[index] = EntryState.TOMB;
                        count--;
                        modCount++;
                        return true;
                    }
                }
                itCount++;
                index = this.h(temp_key, itCount);
            }
            
            return false;
        }catch(ClassCastException e){
            return false;
        }
        
    }
    
    
    /**
     * Copia en esta tabla, todos los objetos contenidos en el map especificado.
     * Los nuevos objetos reemplazarán a los que ya existan en la tabla
     * asociados a las mismas claves (si se repitiese alguna).
     * @param map el map cuyos objetos serán copiados en esta tabla.
     * @throws NullPointerException si m es null.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if(map == null) throw new NullPointerException();
        
        this.rehash(map.size() * 2);
        for(Map.Entry<? extends K, ? extends V> e : map.entrySet()){
            put(e.getKey(), e.getValue());
        }
    }
    
    
    /**
     * Reinicia el contenido de la tabla, volviendo a crear la tabla como una
     * tabla vacia con capacidad igual a initialSize
     */
    @Override
    public void clear() {
        this.tabla = new Object[this.initialSize];
        this.count = 0;
        this.modCount++;
    }
    
    
    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        
        TSB_OAHashtable<K,V> auxMap = new TSB_OAHashtable<>(this.tabla.length);
        Set<Map.Entry<K,V>> entryS = this.entrySet();
        Iterator<Map.Entry<K,V>> it = entryS.iterator();
        while(it.hasNext()){
            Map.Entry<K,V> auxEntry = (Map.Entry<K, V>) it.next();
            auxMap.put(auxEntry.getKey(), auxEntry.getValue());
        }     
        return auxMap;
    }
    
    
    /**
     * Devuelve el contenido de la tabla en forma de String.
     * @return una cadena con el contenido completo de la tabla.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        for (Object tabla1 : this.tabla) {
            if (tabla1 != EntryState.OPEN && tabla1 != EntryState.TOMB) {
                sb.append(" ").append(tabla1.toString());
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
    
    public String toStringFull()
    {
        StringBuilder sb = new StringBuilder("[");
        for (Object tabla1 : this.tabla) {
            
            sb.append(" ").append(tabla1.toString());
            
        }
        sb.append(" ]");
        return sb.toString();
    }
    
    
    /**
     *
     * @return
     */
    @Override
    public Set<K> keySet() {
        return new keySet();
    }
    
    
    /**
     *
     * @return
     */
    @Override
    public Collection<V> values() {
        return new ValueCollection();
    }
    
    
    /**
     *
     * @return
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }
    
    
    /**
     *
     * @param k
     * @return
     */
    /*private int h(int k)
    {
    return h(k, this.tabla.length);
    }*/
    
    /**
     * Aplica una funcion de dispersion a una Key para poder obtener la posicion
     * que ocupara en la tabla
     * @param key Clave a la cual se le va a aplicar la funcion de dispersion
     * @param i Desfasaje del indice en el cual deberá insertarse la nueva Entry
     * en caso de que la posicíón correspondiente ya se encuentre ocupada
     * @return La posicion que ocupara la nueva Entry a insertar.
     */
    
    private int h(K key, int i) {
        return h(key, tabla.length, i);
    }
    
    private int h(K key, int t, int i){
        int k = key.hashCode();
        if(i < 0) i *= -1;
        if(k<0) k *= -1;
        return (k + (i*i)) % t;
        
        
    }
    
   
    
    @Override
    public int hashCode() {
        if(this.isEmpty()) {return 0;}
        
        int hc = 0;
        for(Map.Entry<K, V> entry : this.entrySet()){
            hc += entry.hashCode();
        }
        
        return hc;
    }
    
    @Override
    public boolean equals(Object obj){
        
        if(! (obj instanceof Map)) return false;
        
        Map<K,V> aux_map = (Map<K, V>) obj;
        if(this.size() != aux_map.size()) return false;
        
        try{
            Iterator<Map.Entry<K,V>> i = this.entrySet().iterator();
            while(i.hasNext()) {
                
                Map.Entry<K, V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if(aux_map.get(key) == null) { return false; }
                else{
                    if(!value.equals(aux_map.get(key))) { return false; }
                }
            }
        }
        
        catch (ClassCastException | NullPointerException e){
            return false;
        }
        
        return true;
        
        
        
        
        
    }
  
    protected void rehash(int newLength){
        if(newLength <= 0) throw new IllegalArgumentException();
        if(newLength <= tabla.length) return;
        if(!esPrimo(newLength)) newLength = this.siguientePrimo(newLength);
        
        Object[] tabla_temp = new Object[newLength];
        
        for(int i = 0; i < tabla_temp.length; i++){
            tabla_temp[i] = EntryState.OPEN;
        }
        modCount++;
        Set<Map.Entry<K,V>> entryS = this.entrySet();
        Iterator<Map.Entry<K,V>> it = entryS.iterator();
        
        while(it.hasNext()){
            int itCount = 0;
            Map.Entry<K,V> temp_entry = (Map.Entry<K, V>) it.next();
            int new_idx = this.h(temp_entry.getKey(), newLength, itCount);            
            while(!tabla_temp[new_idx].equals(EntryState.OPEN)){
                itCount++;
                new_idx = this.h(temp_entry.getKey(), newLength,itCount);
            }            
            tabla_temp[new_idx] = temp_entry;            
        }
        tabla = tabla_temp;
    }
    
    protected void rehash(){
        int new_length = tabla.length * 2 + 1;
        this.rehash(new_length);
    }
    
    
    
    /**
     * Busca el primo siguiente del numero que se le pasa por parametro
     * @param num - el numero del cual se buscara el siguiente primo
     * @return el primo siguiente.
     */
    private int siguientePrimo(int num){
        boolean finish = false;
        
        if(num % 2 == 0){
            num++;
        }
        
        while(!finish){
            num+=2;
            if(esPrimo(num)) finish = true;
        }
        
        return num;
    }
    
    
    /**
     * comprubea si un numero es primo o no
     * @param x - el numero a comprobar
     * @return true si el numero ingresado era primo y false si no lo era.
     */
    private boolean esPrimo(int x){
        int contador = 2, max = (int) x/2;
        
        boolean primo=true;
        if(x % contador == 0){
            primo = false;
        }
        contador++;
        
        while ((primo) && (contador <= max)){
            if (x % contador == 0)
                primo = false;
            contador+=2;
        }
        
        return primo;
    }
    
    
    
    private class TSBOAEntry<K,V> implements Map.Entry<K, V>, Serializable{
        
        private K key;
        private V value;
        
        public TSBOAEntry(K key, V value) {
            
            if( key == null || value == null) throw new IllegalArgumentException();
            
            this.key = key;
            this.value = value;
        }
        @Override
        public K getKey() {
            return key;
        }
        
        @Override
        public V getValue() {
            return value;
            
        }
        
        @Override
        public V setValue(V arg0) {
            
            if(arg0 == null) throw new IllegalArgumentException();
            V old = value;
            value = arg0;
            return old;
        }
        
        @Override
        public int hashCode(){
            int hash = 7;
            hash = 61 * hash + Objects.hashCode(this.key);
            hash = 61 * hash + Objects.hashCode(this.value);
            return hash;
        }
        
        @Override
        public boolean equals(Object obj){
            
            if(this == obj) return true;
            if(!this.getClass().equals(obj.getClass())) return false;
            
            Map.Entry aux = (Map.Entry)obj;
            
            if(!this.key.equals(aux.getKey())) return false;
            return this.value.equals(aux.getValue());
            
        }
        
        @Override
        public String toString(){
            return "{Key: " + key + ", Value: " + value + "}";
        }
        
    }
    
    private class EntrySet extends AbstractSet<Map.Entry<K,V>>{
        
        /*
        @Override
        public boolean add(Map.Entry<K,V> entry){
        
        if(entry == null) throw new NullPointerException();
        
        TSB_OAHashtable.this.put(entry.getKey(), entry.getValue());
        return true;
        
        }
        
        @Override
        public boolean addAll(Collection<? extends Map.Entry<K,V>> c){
        
        for(Map.Entry<K,V> entry: c){
        TSB_OAHashtable.this.put(entry.getKey(), entry.getValue());
        }
        return true;
        }*/
        
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntrySetIterator();
        }
        
        @Override
        public int size() {
            return TSB_OAHashtable.this.size();
        }
        @Override
        
        public void clear()
        {
            TSB_OAHashtable.this.clear();
        }
        
        
        /**
         * elimina tanto de la vista como de la tabla al par que entra com
         * parametro
         * @param o - El par a eliminar de la tabla
         * @return
         */
        @Override
        public boolean remove(Object o) {
            if(o == null) throw new NullPointerException();
            if(!(o instanceof Entry)) return false;
            Map.Entry<K,V> entry = (Map.Entry<K, V>) o;
            return TSB_OAHashtable.this.remove(entry.getKey(), entry.getValue());
        }
        
        
        /**
         * verifica si tanto la tabla como esta vista contienen el par que entra
         * como parametro
         * @param o - Par que se debe comprobar si existe en la tabla
         * @return
         */
        @Override
        public boolean contains(Object o) {
            if(o == null) { return false; }
            if(!(o instanceof Map.Entry)) { return false; }
            
            Map.Entry<K,V> aux = (Map.Entry<K, V>) o;
            
            V aux_value = TSB_OAHashtable.this.get(aux.getKey());
            
            if(aux_value == null) return false;
            return aux_value.equals(aux.getValue());
            
        }
        
        private class EntrySetIterator implements Iterator<Map.Entry<K,V>>{
            
            private int current_idx;
            private int next_idx;
            private boolean next_ok;
            private int expected_modCount;
            private Map.Entry<K,V> current;
            private Map.Entry<K,V> next;
            
            public EntrySetIterator(){
                current_idx = -1;
                next_ok = false;
                expected_modCount = TSB_OAHashtable.this.modCount;
                current = null;
                next = null;
                next_idx = -1;
                
            }
            
            
            @Override
            public boolean hasNext() {
                next = null;
                next_idx = -1;
                for(int i = current_idx + 1; i < TSB_OAHashtable.this.tabla.length; i++){
                    if(TSB_OAHashtable.this.tabla[i] instanceof Map.Entry){
                        next_idx = i;
                        next = (Map.Entry<K, V>) TSB_OAHashtable.this.tabla[i];
                        break;
                    }
                    
                }
                return next != null;
            }
            
            @Override
            public Map.Entry<K, V> next() {
                if(!hasNext()) throw new NoSuchElementException();
                if(expected_modCount != modCount) throw new ConcurrentModificationException();
                current_idx = next_idx;
                current = next;
                next_ok = true;
                return current;
            }
            
            @Override
            public void remove() {
                if(next_ok){
                    if(expected_modCount != TSB_OAHashtable.this.modCount) throw new ConcurrentModificationException();
                    TSB_OAHashtable.this.remove(current.getKey(),current.getValue());
                    
                    next_ok = false;
                    expected_modCount++;
                } else{
                    throw new IllegalStateException();
                }
            }
            
        }
        
        
    }
    
    private class ValueCollection extends AbstractCollection<V>{
        
        
        @Override
        public int size(){
            return TSB_OAHashtable.this.size();
        }
        @Override
        public void clear(){
            TSB_OAHashtable.this.clear();
        }
        
        @Override
        public Iterator<V> iterator() {
            return new VallueIterator();
        }
        
        @Override
        public boolean contains(Object o){
            return TSB_OAHashtable.this.containsValue(o);
        }
        
        private class VallueIterator implements Iterator<V>{
            private int current_idx;
            private Map.Entry<K,V> current_Entry;
            private Map.Entry<K,V> next_Entry;
            private int next_idx;
            private int expected_modCount;
            private boolean next_ok;
            
            public VallueIterator(){
                current_idx = 0;
                current_Entry = null;
                next_Entry = null;
                next_idx = 0;
                next_ok = false;
                expected_modCount = modCount;
            }
            
            @Override
            public boolean hasNext() {
                next_Entry = null;
                next_idx = -1;
                for(int i = current_idx + 1; i < TSB_OAHashtable.this.tabla.length; i++){
                    if(TSB_OAHashtable.this.tabla[i] instanceof Map.Entry){
                        next_idx = i;
                        next_Entry = (Map.Entry<K, V>) TSB_OAHashtable.this.tabla[i];
                        break;
                    }
                    
                }
                return next_Entry != null;
            }
            
            @Override
            public V next() {
                if(!hasNext()) throw new NoSuchElementException();
                if(expected_modCount != modCount) throw new ConcurrentModificationException();
                current_idx = next_idx;
                current_Entry = next_Entry;
                next_ok = true;
                return current_Entry.getValue();
            }
            
            @Override
            public void remove() {
                if(next_ok){
                    if(expected_modCount != TSB_OAHashtable.this.modCount) throw new ConcurrentModificationException();
                    TSB_OAHashtable.this.remove(current_Entry.getKey(),current_Entry.getValue());
                    
                    next_ok = false;
                    expected_modCount++;
                } else{
                    throw new IllegalStateException();
                }
                
            }
            
        }
        
        
    }
    
    
    
    private class keySet extends AbstractSet<K> {
        
        @Override
        public Iterator<K> iterator() {
            return new KeySetIterator();
        }
        
        @Override
        public int size() {
            return TSB_OAHashtable.this.size();
        }
        
        @Override
        public boolean contains(Object o) {
            return TSB_OAHashtable.this.contains(o);
        }
        
        @Override
        public boolean remove(Object o)
        {
            return (TSB_OAHashtable.this.remove(o) != null);
        }
        
        @Override
        public void clear()
        {
            TSB_OAHashtable.this.clear();
        }
        
        private class KeySetIterator implements Iterator<K>{
            private int current_idx;
            private Map.Entry<K,V> current_Entry;
            private Map.Entry<K,V> next_Entry;
            private int next_idx;
            private int expected_modCount;
            private boolean next_ok;
            
            public KeySetIterator(){
                current_idx = 0;
                current_Entry = null;
                next_Entry = null;
                next_idx = 0;
                next_ok = false;
                expected_modCount = modCount;
            }
            
            @Override
            public boolean hasNext() {
                next_Entry = null;
                next_idx = -1;
                for(int i = current_idx + 1; i < TSB_OAHashtable.this.tabla.length; i++){
                    if(TSB_OAHashtable.this.tabla[i] instanceof Map.Entry){
                        next_idx = i;
                        next_Entry = (Map.Entry<K, V>) TSB_OAHashtable.this.tabla[i];
                        break;
                    }
                    
                }
                return next_Entry != null;
            }
            
            @Override
            public K next() {
                if(!hasNext()) throw new NoSuchElementException();
                if(expected_modCount != modCount) throw new ConcurrentModificationException();
                current_idx = next_idx;
                current_Entry = next_Entry;
                next_ok = true;
                return current_Entry.getKey();
            }
            
            @Override
            public void remove() {
                if(next_ok){
                    if(expected_modCount != TSB_OAHashtable.this.modCount) throw new ConcurrentModificationException();
                    TSB_OAHashtable.this.remove(current_Entry.getKey(),current_Entry.getValue());
                    
                    next_ok = false;
                    expected_modCount++;
                } else{
                    throw new IllegalStateException();
                }
                
            }
            
        }
        
    }
}
