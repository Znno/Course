package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node {
        private Node prev;
        private T data;
        private Node next;

    }
    private int size;
    private Node sent = new Node();
    public LinkedListDeque() {
        sent.prev = sent;
        sent.next = sent;
        size = 0;
    }

    public void addFirst(T item) {
        size++;
        Node temp = new Node();
        temp.data = item;
        temp.prev = sent;
        temp.next = sent.next;
        sent.next.prev = temp;
        sent.next = temp;

    }

    public void addLast(T item) {
        size++;
        Node temp = new Node();
        temp.data = item;
        temp.next = sent;
        temp.prev = sent.prev;
        sent.prev.next = temp;
        sent.prev = temp;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node p = sent.next;
        while (p != sent) {
            System.out.print(p.data);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println();

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node h = sent.next;
        sent.next = sent.next.next;
        sent.next.prev = sent;
        T dude = h.data;
        h = null;
        size--;

        return dude;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node h = sent.prev;
        sent.prev = sent.prev.prev;
        sent.prev.next = sent;
        T dude = h.data;
        h = null;
        size--;
        return dude;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node temp = sent.next;
        int cnt = 0;
        while (temp != sent) {
            if (cnt++ == index) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque temp = (Deque) o;
        if (temp.size() != this.size) {
            return false;
        }
        int idx = 0;

        while (idx < size()) {
            if (!(get(idx).equals(temp.get(idx)))) {
                return false;
            }
            idx++;
        }
        return true;

    }

    private T go(Node x, int idx) {
        if (idx == 0) {
            return x.data;
        }
        return go(x.next, idx - 1);
    }

    public T getRecursive(int index) {
        Node p = sent.next;
        return go(p, index);
    }

    public Iterator<T> iterator() {
        return new It();
    }

    private class It implements Iterator<T> {
        int pos;
        private It() {
            pos = 0;
        }
        public boolean hasNext() {
            return (pos < size);
        }
        public T next() {
            T x = get(pos);
            pos++;
            return x;

        }
    }


}
