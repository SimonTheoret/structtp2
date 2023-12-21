import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
public class MyMap<K,V> extends Maps<K,V> {

    // Constructor for specifying the initial capacity of the map with an empty table
    public MyMap(int capacity) {
        this.table = new ArrayList<>(Collections.nCopies(capacity, DEFUNCT)); // Make sure
        this.capacity = capacity;
    }

    //Returns true if MyMap contains the key
    public boolean containsKey(Object key) {
        return table.contains(key);
    }

    // This method returns the index in the table of the key. It first looks at the right part of the array
    // and then the left part of the array, where the middle is the hashfunc result of the key.
    protected int getIndex(Object key){
        for (int i = this.hashfunc(key); i < capacity; i++) { // rightmost part of array
            MapEntry<K,V> element = table.get(i);
            if (element == DEFUNCT) { //change value of this entry if found
                return i;
                }
            if (element == DEFUNCT){ //If the next position is free and was never taken, returns -1
                return -1;
            }
        }
        // ends with the left index
        for (int i = 0; i < this.hashfunc(key); i++) {
            MapEntry<K,V> element = table.get(i);
            if (element.getKey() == key) { //change value of this entry if found
                return i;
                }
            if (element == null){ // If the next position is free and was never taken, returns -1
                return -1;
            }
        }
        return -1; // returns -1 if the key is not found
    }

    //Returns true if MyMap contains the value
    public boolean containsValue(Object value) {
        for (MapEntry<K, V> entry : table) {
            if (value == entry.getValue()) {
                return true;
            }
        }
        return false;
    }

    // Returns the value of the object
    public V get(Object key) {
        int i = getIndex(key);
        if (i < 0){
            return null;
        }
        return table.get(i).getValue();
    }

    // Returns the position (compressed hash code) of a key.
    protected int hashfunc(Object key) {
        return key.hashCode() % capacity; //Compression + hashcode
    }

    // Insert the value at the key position;
    public V put(K key, V value) {
        // start from the right of the index
        // getIndex is not appropriate here
        for (int i = this.hashfunc(key); i < capacity; i++) { // rightmost part of array
            MapEntry<K,V> element = table.get(i);
            if (element == DEFUNCT ) { // change element if defunct
                V old = table.set(i, new MapEntry<>(key, value)).getValue();
                if (factor() > 0.75){ // resize condition
                    resize();
                }
                return old;
            }
            if (element.getKey() == key) { //if is the same key, replace the value
                return element.setValue(value);
            }
        }

        // end with the left index
        for (int i = 0; i < this.hashfunc(key); i++) {
            System.out.println("GOING THE OTHER SIDE");
            MapEntry<K,V> element = table.get(i);
            if (element == DEFUNCT ) { // change element if defunct

                V old = table.set(i, new MapEntry<>(key, value)).getValue();
                if (factor() > 0.75 ){ // resize condition
                    System.out.println(size());
                    resize();
                }
                return old;
            }
            if (element.getKey() == key) { //if is the same key, replace the value
                return element.setValue(value);
            }
        }
        System.out.println("ERROR: Array is full!");
        return null; // cannot be reached, (normally)
    }
    // Remove the mapping key. It lefts a DEFUNCT entry in its place.
    public V remove(Object key){
        int i = getIndex(key);
        if (i<0){
            return null;
        }
        V old = table.get(i).getValue();
        table.set(i, DEFUNCT);
        return old;
    }
    // Increase the capacity of the array
    // It also reashes the newly created table
    protected void resize(){
        table.ensureCapacity(capacity * 2 +1);
        this.capacity = capacity * 2 + 1;
        HashSet<Maps.Entry<K,V>> set = entrySet();
        ArrayList<MapEntry<K,V>> newTable = new ArrayList<MapEntry<K,V>>(Collections.nCopies(capacity, DEFUNCT));
        this.table = newTable;
        for (Entry<K,V> entry : set){
            put(entry.getKey(), entry.getValue());
        }
    }
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key: m.keySet()){
            V value = m.get(key);
            put(key, value);
        }
    }
    public void clear(){
        table = new ArrayList<MapEntry<K,V>>();
        table.add(DEFUNCT);
        capacity = 1;
    }
    // This method returns a HashSet containing all keys of the MyMap. It is not a view!
    public HashSet<K> keySet(){
        HashSet<K> set = new HashSet<K>();
        for (MapEntry<K,V> entry: table){
            if (entry != DEFUNCT){
                set.add(entry.getKey());
            }
        }
        return set;
    }
    public ArrayList<V> values(){
        ArrayList<V> list = new ArrayList<>();
        for (MapEntry<K,V> entry: table){
            if (entry != DEFUNCT){
                list.add(entry.getValue());
            }
        }
        return list;
    }

    public HashSet<Entry<K,V>> entrySet(){
        HashSet<Entry<K,V>> set = new HashSet<Entry<K,V>>();
        for (Entry<K,V> entry: table){
            if (entry != DEFUNCT){
                set.add(entry);
            }
        }
        return set;
    }
}