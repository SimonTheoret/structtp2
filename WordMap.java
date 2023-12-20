public class WordMap<K,V> extends Maps<K,V> {
    public boolean containsKey(Object key){
        for(MapEntry<K,V> entry: table){
            if (key == entry.getKey()){
                return true;
            }
        }
        return false;
    }
    public boolean containsValue(Object value){
        for(MapEntry<K,V> entry: table){
            if (value == entry.getValue()){
                return true;
            }
        }
        return false;
    }
    public V get(Object key){
        for(MapEntry<K,V> entry: table){
            if (key == entry.getKey()){
                return entry.getValue();
            }
        }
        return null;
    }
    public V put(K key, V value){
        MapEntry<K,V> entry = new MapEntry<K, V>(key, value);
        for (int i = key.hashCode(); i < size(); i++){
            if (table[i] == null || table[i] == DEFUNCT){
                table[i] = new MapEntry<K,V>(key, value);
            }
            if (table[i] == entry){
                
            }
        }
    }
}
