package open.hashmap;

public interface OpenAddressingMap {
    void put(int key, long value);

    long get(int key);

    int size();

    int capacity();
}
