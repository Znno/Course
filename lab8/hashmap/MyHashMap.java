package hashmap;

import javax.print.DocFlavor;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Znno
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {
        for(int i=0;i<len;i++) {
            buckets[i] = null;
        }
        size=0;
        keys.clear();

    }

    @Override
    public boolean containsKey(K key) {

        return (keys.contains(key));
    }

    @Override
    public V get(K key) {
        int hash= key.hashCode();
        hash=(((hash%len)+len))%len;
        if(!keys.contains(key)) {
            return null;
        }
        for(Node x:buckets[hash])
        {
            if(x.key.equals(key)) {
                return x.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }
    private void resize(){
        len=len*2;
        Collection<Node>[] newbuckets=new Collection[len];
        for(int i=0;i<len/2;i++){
            if(buckets[i]!=null) {
                for (Node x : buckets[i]) {
                    int hash = x.key.hashCode();
                    hash = (((hash % len) + len)) % len;
                    if(newbuckets[hash]==null)
                        newbuckets[hash]=createBucket();
                    newbuckets[hash].add(x);


                }
            }
        }
        buckets=newbuckets;
        newbuckets=null;


    }

    @Override
    public void put(K key, V value) {
        int hash= key.hashCode();
        hash=(((hash%len)+len))%len;
        if(!keys.contains(key)) {
            if(buckets[hash]==null)
            buckets[hash]=createBucket();
            buckets[hash].add(new Node(key,value));
            keys.add(key);
            size++;
        }
        else {
            for (Node x : buckets[hash]) {
                if (x.key.equals(key)) {
                    x.value=value;
                }
            }
        }
        if((double)keys.size()/(double)len>loadfactor)
            resize();
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets=new Collection[16];
    // You should probably define some more!
    private int len=16;
    private int size=0;
    private double loadfactor=0.75;
    private HashSet<K> keys=new HashSet<>();

    /** Constructors */
    public MyHashMap() {
    }

    public MyHashMap(int initialSize) {
        len=initialSize;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        len=initialSize;
        loadfactor=maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
