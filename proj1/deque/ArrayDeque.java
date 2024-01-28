package deque;

import org.checkerframework.checker.units.qual.A;

public class ArrayDeque <T>{

    T[] arr = (T[]) new Object[8];
    int size=0;
    private class node{
        int start;
        int end;
    };
    int cap=8;
    node sent=new node();
    public  ArrayDeque(){
        sent.start=0;
        sent.end=0;
    }
    public void resize(int lol){
        T[] temp=(T[]) new Object [lol];
        int cnt=0;


        for(int i=sent.start;;i++){
            i=i%(cap);
            temp[cnt]=arr[i];
            if(i==sent.end) {
                sent.start=0;
                sent.end = cnt;
                break;
            }
            cnt++;

        }
        cap=lol;
        arr=temp;
        temp=null;

    }
    public  void addFirst(T item){

        if(size==cap){
            resize(cap*2);
        }
        if(size==0){
            arr[sent.start]=item;
            size++;
            return;
        }
        sent.start--;
        sent.start+=cap;
        sent.start%=cap;
        size++;
        arr[sent.start]=item;

    }
    public void addLast(T item){
        if(size==cap){
            resize(cap*2);
        }
        if(size==0){
            arr[sent.start]=item;
            size++;
            return;
        }
        sent.end++;
        sent.end%=cap;
        size++;
        arr[sent.end]=item;


    }
    public boolean isEmpty(){
        return (size==0);
    }
    public int size()
    {
        return size;
    }
    public void printDeque(){

        if(size==0){
            System.out.println();
            return;
        }
        for(int i=sent.start;;i++){
            i%=cap;

            System.out.print(arr[i]);
            System.out.print(" ");

            if(i==sent.end){
                System.out.println();
                break;
            }

        }
    }
    public T removeFirst(){
        if (size==0)
            return null;
        T dude=arr[sent.start];
        sent.start++;
        sent.start%=cap;
        size--;
        if(size<=cap/2) {
            if(size>=8)
            resize(cap/2);

        }
        return dude;

    }
    public T removeLast(){
        if (size==0)
            return null;
        T dude=arr[sent.end];
        sent.end--;
        sent.end+=cap;
        sent.end%=cap;
        size--;
        if(size<=cap/2) {
            if(size>=8)
                resize(cap/2);

        }
        return dude;
    }
    public T get(int index){
        if (index>=size)
            return null;
        int cnt=0;
        for(int i=sent.start;;i++){
            i%=cap;



            if(cnt==index){
                return arr[i];

            }
            cnt++;

        }
    }
    public boolean equals(Object o){
        if(!(o instanceof ArrayDeque))
            return false;
        ArrayDeque dup=(ArrayDeque) o;
        if(dup.size()!=this.size)
            return false;
        System.out.println(cap);
        System.out.println(dup.cap);
        for(int i=sent.start, j=dup.sent.start;;i++,j++){
            i%=cap;
            j%=cap;
            if (arr[i]!=dup.arr[j])
                return false;
            if(i==sent.end)
                return true;
        }
    }
    public static void main(String args[]){
        ArrayDeque a1=new ArrayDeque();
        a1.addLast(1);
        a1.addLast(2);
        a1.addLast(3);
        a1.addLast(4);
        a1.addLast(5);
        a1.addLast(6);
        a1.addLast(7);
        a1.addFirst(0);
        a1.addLast(8);
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.addLast(6);
        a1.addLast(7);
        a1.addFirst(0);
        ArrayDeque a2=new ArrayDeque();
        a2.addFirst(7);
        a2.addFirst(6);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.addFirst(0);
        a2.removeFirst();
        a2.removeFirst();
        a2.removeFirst();
        a2.removeFirst();
        a2.removeFirst();
        a2.removeFirst();
        System.out.println(a1.equals(a2));










        a2.printDeque();
    }

}
