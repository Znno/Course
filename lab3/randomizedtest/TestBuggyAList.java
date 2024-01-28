package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public  void testThreeAddThreeRemove(){
        AListNoResizing corr=new AListNoResizing();
        BuggyAList wrong=new BuggyAList();
        corr.addLast(4);
        wrong.addLast(4);
        corr.addLast(5);
        wrong.addLast(5);
        corr.addLast(6);
        wrong.addLast(6);
        assertEquals(corr.removeLast(),wrong.removeLast());
        assertEquals(corr.removeLast(),wrong.removeLast());
        assertEquals(corr.removeLast(),wrong.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList <Integer>R=new BuggyAList<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                R.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(L.size(),R.size());

            }
            else if(operationNumber==2){
                if(L.size()==0)
                    continue;
                assertEquals(L.getLast(),R.getLast());

            }
            else if(operationNumber==3){
                if(L.size()==0)
                    continue;
                int x=L.removeLast();
                int y= R.removeLast();
                assertEquals(x,y);

            }
        }
    }

}
