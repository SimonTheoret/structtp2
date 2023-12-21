import java.util.ArrayList;
import java.util.Map;

public abstract class Maps<K,V> implements Map<K,V> {
    public static class MapEntry<K, V> implements Entry<K, V> {
        private K k; // for the key
        private V v; // for the value

        public MapEntry(K key, V value) {
            this.k = key;
            this.v = value;
        }

        // getters
        public K getKey() {
            return this.k;
        }

        public V getValue() {
            return this.v;
        }

        // developer's utilities
        protected void setKey(K key) {
            this.k = key;
        }

        public V setValue(V value) {
            V old = v;
            this.v = value;
            return old;
        }
        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">";
        }
    }
    // table containing the entries
    protected ArrayList<MapEntry<K,V>> table;
    protected MapEntry<K,V> DEFUNCT = new MapEntry<>( null, null ); // value left for removed entries
    int capacity; // capacity of table
    public int size() {
        int count = 0;
        for (MapEntry<K,V> entry : table){
            if (entry != DEFUNCT){
                count = count+1;
            }
        }
        return count;
    }
    public float factor(){
       float s = (float) size();
       float c = (float) capacity;
       return s/c;
    }
    public int getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size() > 0;
    }
}
