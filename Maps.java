import java.util.ArrayList;
import java.util.Map;

public abstract class Maps<K,V> implements Map<K,V> {
    protected static class MapEntry<K, V> implements Entry<K, V> {
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
    }
    // table containing the entries
    protected MapEntry<K,V>[] table;
    protected MapEntry<K,V> DEFUNCT = new MapEntry<>( null, null );
    int size; // number of elements in table
    int capacity; // capacity of table
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() > 0;
    }
}
