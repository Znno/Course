package deque;


import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    T[] arr = (T[]) new Object[8];
    int size = 0;

    public class node {
        int start;
        int end;
    }

    private int cap = 8;
    node sent = new node();

    public ArrayDeque() {
        sent.start = 0;
        sent.end = 0;
    }

    public void resize(int lol) {
        T[] temp = (T[]) new Object[lol];
        int cnt = 0;


        for (int i = sent.start; ; i++) {
            i = i % (cap);
            temp[cnt] = arr[i];
            if (i == sent.end) {
                sent.start = 0;
                sent.end = cnt;
                break;
            }
            cnt++;

        }
        cap = lol;
        arr = null;
        arr = temp;


    }

    public void addFirst(T item) {

        if (size == cap) {
            resize(cap * 2);
        }
        if (size == 0) {
            arr[sent.start] = item;
            sent.end = sent.start;
            size++;
            return;
        }
        sent.start--;
        sent.start += cap;
        sent.start %= cap;
        size++;
        arr[sent.start] = item;

    }

    public void addLast(T item) {
        if (size == cap) {
            resize(cap * 2);
        }
        if (size == 0) {
            arr[sent.start] = item;
            sent.end = sent.start = 0;
            size++;
            return;
        }
        sent.end++;
        sent.end %= cap;
        size++;
        arr[sent.end] = item;


    }

    public int size() {
        return size;
    }

    public void printDeque() {

        if (size == 0) {
            System.out.println();
            return;
        }
        for (int i = sent.start; ; i++) {
            i %= cap;

            System.out.print(arr[i]);
            System.out.print(" ");

            if (i == sent.end) {
                System.out.println();
                break;
            }

        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T dude = arr[sent.start];
        sent.start++;
        sent.start %= cap;
        size--;
        if (size <= cap / 4) {
            if (cap >= 16) {
                resize(cap / 2);
            }

        }
        return dude;

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T dude = arr[sent.end];
        sent.end--;
        sent.end += cap;
        sent.end %= cap;
        size--;
        if (size <= cap / 4) {
            if (cap >= 16) {
                resize(cap / 2);
            }

        }
        return dude;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return arr[((sent.start + index)) % cap];
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque dup = (Deque) o;
        int idx = 0;
        if (dup.size() != this.size) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!(get(i).equals(dup.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new it();
    }

    private class it implements Iterator<T> {
        int pos;

        public it() {
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
