package open.hashmap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap implements OpenAddressingMap {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.8;

    private int capacity;
    private int size;
    private double loadFactor;
    private Entry[] table;

    public OpenAddressingHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        table = new Entry[capacity];
    }

    public OpenAddressingHashMap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity should be greater then 1");
        }
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = capacity;
        table = new Entry[this.capacity];
    }

    public OpenAddressingHashMap(int capacity, double loadFactor) {
        if (loadFactor < 0.25) {
            throw new IllegalArgumentException("Load Factor should be greater then 0.25");
        }
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity should be greater then 1");
        }
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        table = new Entry[this.capacity];
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public long get(int key) {
        return this.get(key, 0);
    }

    @Override
    public void put(int key, long value) {

        this.put(key, value, 0);
    }

    private long get(int key, int i) {
        int hashcode = this.hash(key, i);

        if ((i > 0) && (hashcode == this.hash(key, 0))) {
            throw new NoSuchElementException("Key " + key + " not found in hash table.");
        }

        if ((this.table[hashcode] != null) && (this.table[hashcode].key == key)) {
            return table[hashcode].value;
        }

        return this.get(key, i + 1);
    }

    private void put(int key, long value, int position) {

        int hashcode = this.hash(key, position);

        if (this.table[hashcode] != null) {
            if (this.table[hashcode].key == key) {
                this.table[hashcode] = new Entry(key, value);
            } else {
                this.put(key, value, position + 1);
            }
        } else {
            if ((((double) size + 1) / capacity) > loadFactor) {
                resizeMap();
                put(key, value);
                return;
            }
            this.table[hashcode] = new Entry(key, value);
            size++;
        }
    }

    private int hash(int key, int i) {

        return this.doubleHashing(key, i);
    }

    private int doubleHashing(int key, int i) {

        return (this.keyHash(key) + i) % this.capacity;
    }

    private int keyHash(int key) {

        return (1 + key) % (this.capacity - 1);
    }

    private void resizeMap() {
        Entry[] tableCopy = Arrays.copyOf(this.table, this.table.length);
        this.capacity = this.capacity * 2;
        this.size = 0;
        this.table = new Entry[capacity];
        for (Entry entry : tableCopy) {
            if (entry != null) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static class Entry {
        int key;
        long value;

        public Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }
    }
}
