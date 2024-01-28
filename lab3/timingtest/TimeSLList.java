package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList N=new AList();
        N.addLast(1000);
        N.addLast(2000);
        N.addLast(4000);
        N.addLast(8000);
        N.addLast(16000);
        N.addLast(32000);
        N.addLast(64000);
        N.addLast(128000);
        AList times=new AList();
        AList ops=new AList();
        for(int i=0;i<N.size();i++){

            SLList temp=new SLList();
            for(int j=0;j<(int)N.get(i);j++)
                temp.addLast(j);
            Stopwatch dude=new Stopwatch();
            int cnt=0;
            for(int j=0;j<10000;j++) {
                temp.getLast();
                cnt++;
            }

            times.addLast(dude.elapsedTime());
            ops.addLast(cnt);
        }
        printTimingTable(N,times,ops);
    }

}
