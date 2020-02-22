package open.hashmap;

public interface OpenAddressingMap {
    /**
     * Used to put element in map
     *
     * @param key   int value of key
     * @param value long value that stored by represented key
     */
    void put(int key, long value);

    /**
     * Used to get element from map
     *
     * @param key int value of key
     * @return long value that stored by represented key
     */
    long get(int key);

    /**
     * @return current size of map(count of element that map contains)
     */
    int size();

    /**
     * @return current map capacity
     */
    int capacity();
}
