package deque;

import com.puppycrawl.tools.checkstyle.checks.indentation.NewHandler;

public class LinkedListDeque <T> {

    private class node{
        public node prev;
        public T data;
        public node next;

    };
    int size;
    node sent=new node();
    LinkedListDeque(){
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
    public boolean isEmpty(){
        return (size==0);
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
        node temp=sent.next;
        int cnt=0;
        while (temp!=null){
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
    public static void main(String args[]){
        LinkedListDeque d1=new LinkedListDeque();
        d1.addFirst(1);
        d1.addFirst(2);
        d1.addFirst(3);
        LinkedListDeque d2= new LinkedListDeque();
        d2.addFirst(1);
        d2.addFirst(2);
        d2.addFirst(4);

        d1.printDeque();
        d2.printDeque();
    }




}
