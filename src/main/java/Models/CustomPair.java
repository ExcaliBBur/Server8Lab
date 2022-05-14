package Models;

/**
 * Custom Pair class to contain various data.
 *
 * @param <K> first parameter
 * @param <V> second parameter
 */
public class CustomPair<K, V> {
    private final K key;
    private final V value;

    /**
     * Constructor, gets all necessary things.
     *
     * @param key   first parameter to contain
     * @param value second parameter to contain
     */
    public CustomPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
