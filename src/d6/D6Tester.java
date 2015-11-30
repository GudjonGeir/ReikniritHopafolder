package d6;

import java.util.*;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.TransitiveClosure;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class D6Tester {
    public static boolean compareTransClosure(TransitiveClosure A, TransitiveClosure B, int V){
        boolean equal = true;
        for(int i = 0; i < V; ++i){
            for(int j = 0; j < V; ++j){
                if(i != j && (A.reachable(i,j) != B.reachable(i,j))){
                    equal = false;
                    StdOut.printf("%d is not reachable from %d\n", j, i);
                }
            }
        }
        if(!equal)
            StdOut.println("Transitive closure is not equal.");
        return equal;
    }

    public static boolean checkMinimality(Digraph G){
        // This is secret
        return true;
    }

    public static boolean checkNodes(Digraph A, Digraph B){
        if (A.V() < B.V()){
            StdOut.println("Graph contains more nodes than the original graph.");
            return false;
        } else if (A.V() > B.V()){
            StdOut.println("Graph contains fewer nodes than the original graph.");
            return false;
        }
        return true;
    }

    public static boolean check(Digraph A, Digraph B) {
        return  checkNodes(A, B) &&
                compareTransClosure(new TransitiveClosure(A), new TransitiveClosure(B), A.V()) &&
                checkMinimality(B);
    }

    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);
        Digraph newG = MinimumDigraph.equivalentMinimumDigraph(G);
        if (!D6Tester.check(G, newG)){
            StdOut.print(newG.toString());
        } else {
            StdOut.printf("Equivalent graph.\n");
        }
    }
}