package bstmap;

import javax.lang.model.util.Elements;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    private int size;

    private class node {
        private V data;
        public K keyo;
        private node right;
        private node left;

        public node(V dat, K key) {
            data = dat;
            keyo = key;
        }
    }

    private node sent;

    public BSTMap() {
        sent = null;
        size = 0;
    }

    @Override
    public void clear() {
        size = 0;
        sent = null;
    }

    private boolean find(K sk, node n) {
        if (n == null)
            return false;
        if (sk.equals(n.keyo))
            return true;
        else if ((sk.compareTo(n.keyo) < 0))
            return find(sk, n.left);
        else
            return find(sk, n.right);


    }

    @Override
    public boolean containsKey(K key) {

        return find(key, sent);
    }

    private V getval(K sk, node n) {
        if (n == null)
            return null;
        if (sk.compareTo(n.keyo) == 0)
            return n.data;
        else if ((sk.compareTo(n.keyo) < 0))
            return getval(sk, n.left);
        else
            return getval(sk, n.right);

    }

    @Override
    public V get(K key) {

        return getval(key, sent);
    }

    @Override
    public int size() {

        return size;
    }

    private node add(node curr, node gded) {
        if (curr == null)
            return gded;
        else if (gded.keyo.compareTo(curr.keyo) < 0)
            curr.left = add(curr.left, gded);
        else
            curr.right = add(curr.right, gded);
        return curr;
    }

    public K getsent() {
        return sent.keyo;
    }

    @Override
    public void put(K key, V value) {
        if (find(key, sent))
            return;
        node tempnode = new node(value, key);
        sent = add(sent, tempnode);
        size++;
        return;
    }

    public void go(node n) {
        if (n == null)
            return;
        go(n.left);
        System.out.println(n.keyo + ":" + n.data);
        go(n.right);
    }

    public void printInOrder() {
        go(sent);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
