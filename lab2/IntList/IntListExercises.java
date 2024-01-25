package IntList;

public class IntListExercises {

    /**
     * Part A: (Buggy) mutative method that adds a constant C to each
     * element of an IntList
     *
     * @param lst IntList from Lecture
     */
    public static void addConstant(IntList lst, int c) {
        IntList head = lst;
        while (head != null) {
            head.first += c;
            head = head.rest;
        }
    }

    /**
     * Part B: Buggy method that sets node.first to zero if
     * the max value in the list starting at node has the same
     * first and last digit, for every node in L
     *
     * @param L IntList from Lecture
     */
    public static void setToZeroIfMaxFEL(IntList L) {
        IntList arr[]=new IntList[L.size()];
        int cnt=L.size()-1;
        IntList p=L;
        while (p!=null){
            arr[cnt]=p;
            p=p.rest;
            cnt--;
        }
        int  x=arr[0].first;
        for(int i=0;i<L.size();i++) {
            if(arr[i].first>=x)
                x=arr[i].first;
            if(firstDigitEqualsLastDigit(x))
                arr[i].first=0;

        }
    }

    /** Returns the max value in the IntList starting at L. */


    /** Returns true if the last digit of x is equal to
     *  the first digit of x.
     */
    public static boolean firstDigitEqualsLastDigit(int x) {
        int lastDigit = x % 10;
        while (x >= 10) {
            x = x / 10;
        }
        int firstDigit = x % 10;

        return (firstDigit == lastDigit);
    }

    /**
     * Part C: (Buggy) mutative method that squares each prime
     * element of the IntList.
     *
     * @param lst IntList from Lecture
     * @return True if there was an update to the list
     */
    public static boolean squarePrimes(IntList lst) {
        // Base Case: we have reached the end of the list
        if (lst == null) {
            return false;
        }

        boolean currElemIsPrime = Primes.isPrime(lst.first);

        if (currElemIsPrime) {
            lst.first *= lst.first;
        }

        return squarePrimes(lst.rest)  || currElemIsPrime;
    }
}
