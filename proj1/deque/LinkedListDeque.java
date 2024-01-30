package deque;

import com.puppycrawl.tools.checkstyle.checks.indentation.NewHandler;

import java.util.Iterator;

public class LinkedListDeque <T> implements Deque<T>, Iterable<T> {

    private class node{
        public node prev;
        public T data;
        public node next;

    };
    int size;
    node sent=new node();
    public LinkedListDeque(){
        sent.prev=sent;
        sent.next=sent;
        size=0;
    }

    public void addFirst(T item){
        size++;
        node temp=new node();
        temp.data=item;
        temp.prev=sent;
        temp.next=sent.next;
        sent.next.prev=temp;
        sent.next=temp;

    }
    public void addLast(T item){
        size++;
        node temp=new node();
        temp.data=item;
        temp.next=sent;
        temp.prev=sent.prev;
        sent.prev.next=temp;
        sent.prev=temp;
    }
    public int size(){
        return this.size;
    }
    public void printDeque(){
        node p=sent.next;
        while (p!=sent){
            System.out.print(p.data);
            System.out.print(" ");
            p=p.next;
        }
        System.out.println();

    }
    public T removeFirst(){
        if (size==0)
            return null;
        node h=sent.next;
        sent.next=sent.next.next;
        sent.next.prev=sent;
        T dude=h.data;
        h=null;
        size--;

            return dude;
    }
    public T removeLast(){
        if(size==0)
            return null;
        node h=sent.prev;
        sent.prev=sent.prev.prev;
        sent.prev.next=sent;
        T dude=h.data;
        h=null;
        size--;
        return dude;
    }
    public T get(int index){
        if(index>=size)
            return null;
        node temp=sent.next;
        int cnt=0;
        while (temp!=sent){
            if(cnt++==index)
                return temp.data;
        temp=temp.next;
        }
        return null;
    }
    public boolean equals(Object o){
        if(!(o instanceof LinkedListDeque))
            return false;
        LinkedListDeque temp =(LinkedListDeque) o;
        if(temp.size!=this.size)
            return false;
        node p1=sent.next;
        node p2=temp.sent.next;
        while (p1!=sent){
            if(p1.data!=p2.data)
                return false;
            p1=p1.next;
            p2=p2.next;
        }
        return true;

    }
    private T go(node x, int idx){
        if(idx==0)
            return x.data;
        return go(x.next,idx-1);
    }

    public T getRecursive(int index){
        node p=sent;
        return go(p,index);
    }
    public Iterator<T> iterator(){
        return new it();
    }
    private class it implements Iterator<T>{
        int pos;
        public it(){
            pos=0;
        }
        public boolean hasNext(){
            return (pos<size);
        }
        public T next(){
            T x=get(pos);
            pos++;
            return x;

        }
    }
    public static void main(String args[]){
        LinkedListDeque<Integer> d1=new LinkedListDeque<Integer>();
        d1.addFirst(1);
        d1.addFirst(2);
        d1.addFirst(3);
        LinkedListDeque d2= new LinkedListDeque();
        for(int x:d1)
            System.out.println(x);
    }




}
