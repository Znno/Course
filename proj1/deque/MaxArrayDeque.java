package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    Comparator<T> def;

    public MaxArrayDeque(Comparator<T> com) {
        def = com;
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        T mx = arr[sent.start];
        for (int i = sent.start; ; i++) {
            i %= arr.length;
            if (c.compare(arr[i], mx) > 0) {
                mx = arr[i];
            }
            if (i == sent.end) {
                break;
            }
        }
        return mx;
    }

    public T max() {
        return max(def);
    }
}
